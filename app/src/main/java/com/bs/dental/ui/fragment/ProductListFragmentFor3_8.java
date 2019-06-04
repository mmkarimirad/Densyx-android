package com.bs.dental.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.constants.ProductSort;
import com.bs.dental.model.AvailableSortOption;
import com.bs.dental.model.Category;
import com.bs.dental.model.CategoryFeaturedProductAndSubcategoryResponse;
import com.bs.dental.model.ProductModel;
import com.bs.dental.model.ProductService;
import com.bs.dental.model.ViewType;
import com.bs.dental.networking.Api;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.response.ProductsResponse;
import com.bs.dental.ui.activity.GalleryActivity;
import com.bs.dental.ui.adapter.ProductListAdapter;
import com.bs.dental.ui.adapter.SubCategoryAdapter;
import com.bs.dental.ui.views.DrawerManipulationFromFragment;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.inject.InjectView;

/**
 * Created by bs156 on 28-Feb-17.
 * This filter is for nopCommerce version 3.8
 */

public class ProductListFragmentFor3_8 extends BaseFragment {

    private static final String CATEGORY_NAME = "categoryName";
    private static final String CATEGORY_ID = "categoryId";
    @InjectView(R.id.list_product)
    RecyclerView listProduct;
    @InjectView(R.id.rl_rootLayout)
    RelativeLayout rootViewRelativeLayout;
    @InjectView(R.id.tv_category_name)
    TextView categoryNameTextView;
    @InjectView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @InjectView(R.id.bottomsheet)
    BottomSheetLayout bottomSheetLayout;

    private View rootView;
    List<ProductModel> productList;

    public ImageView iv_toolbar;

    String categoryName;
    private GridLayoutManager layoutManager;
    private int newSpanCount = 2;
    private int itemViewType = ViewType.GRID;
    public Map<String, String> queryMapping = new HashMap<>();
    protected int pageNumber = 1;
    protected ProductListAdapter productAdapter;
    protected int categoryId = 0;
    FilterFragmentFor3_8 filterFragment;
    private List<AvailableSortOption> availableSortOptions = new ArrayList<>();
    private int selectedPosition = -1;
    private ArrayAdapter<String> sortAdapter;
    protected boolean resetList = true;

    public static ProductListFragmentFor3_8 newInstance(String categoryName, int categoryId) {
        ProductListFragmentFor3_8 fragment = new ProductListFragmentFor3_8();
        Bundle args = new Bundle();
        args.putString(CATEGORY_NAME, categoryName);
        args.putInt(CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // ====================================== hide HG image =========================================

        iv_toolbar = (ImageView) getActivity().findViewById(R.id.iv_toolbar);
        iv_toolbar.setVisibility(View.GONE);

        Log.e("product", "onCreateView: " + "  open");
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_product_list_custom, container, false);
            productList = null;
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_product_list_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_filter) {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawers();
            } else {
                drawerLayout.openDrawer(GravityCompat.END);
            }

            return true;
        } else if (item.getItemId() == R.id.menu_item_sort) {
            showSortByView();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void drawerSetup() {
        DrawerManipulationFromFragment drawerManipulationFromFragment =
                new DrawerManipulationFromFragment(drawerLayout);
        drawerManipulationFromFragment.DrawerSetup(this);
        filterFragment = new FilterFragmentFor3_8();
        getChildFragmentManager().beginTransaction().replace(R.id.drawer_filter, filterFragment).commit();
    }

    protected void showSortByView() {
        if (availableSortOptions != null) {
            LinearLayout sortLinearLayout = (LinearLayout) getLayoutInflater().
                    inflate(R.layout.list_sort_by, bottomSheetLayout, false);
            ListView sortListView = (ListView) sortLinearLayout.findViewById(R.id.lv_sortby);
            bottomSheetLayout.showWithSheetView(sortLinearLayout);
            sortListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            sortAdapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.simple_list_item_single_choice, ProductSort.getSortOptionTextList(availableSortOptions));

            sortListView.setAdapter(sortAdapter);
            if (selectedPosition >= 0) {
                sortListView.setItemChecked(selectedPosition, true);
            }
            sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String value = availableSortOptions.get(position).getValue();
                    selectedPosition = position;
                    queryMapping.put("orderBy", value);
                    callWebService();
                    bottomSheetLayout.dismissSheet();
                }
            });
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkEventBusRegistration();
        calculateAutomaticGridColumn();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            categoryId = bundle.getInt(CATEGORY_ID, categoryId);
            categoryName = bundle.getString(CATEGORY_NAME, "");
            categoryNameTextView.setText(categoryName);
        }

        if (productAdapter == null) {
            callWebService();
            drawerSetup();
        }


        callSubCategoryList();
    }

    private void calculateAutomaticGridColumn() {
        layoutManager = new GridLayoutManager(getActivity(), 2);
        listProduct.setLayoutManager(layoutManager);
        listProduct.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT < 16) {
                            listProduct.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            listProduct.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                        int viewWidth = listProduct.getMeasuredWidth();
                        float cardViewWidth = getActivity()
                                .getResources()
                                .getDimension(R.dimen.cardviewWidth);

                        newSpanCount = (int) Math.floor(viewWidth / cardViewWidth);
                        if (itemViewType == ViewType.GRID) {
                            updateColumnPerRow(newSpanCount);
                        }
                    }
                });
    }

    private void updateColumnPerRow(int spanCount) {
        layoutManager.setSpanCount(spanCount);
        layoutManager.requestLayout();
    }

    public void callWebService() {
        Log.e("ProductListFragment1", "onViewCreated: " + categoryId + categoryName);
        if (productAdapter != null) {
            productAdapter.resetList();
        }
        resetList = true;
        pageNumber = 1;
        getQueryMap();
        RetroClient.getApi().getProductList(categoryId, queryMapping)
                .enqueue(new CustomCB<ProductsResponse>(rootViewRelativeLayout));
    }

    private void callWebServiceMoreLoad(int pageNumber) {
        this.pageNumber = pageNumber;
        resetList = false;
        getQueryMap();
        RetroClient.getApi().getProductList(categoryId, queryMapping)
                .enqueue(new CustomCB<ProductsResponse>());
    }

    private void callSubCategoryList() {
        RetroClient.getApi().getCategoryFeaturedProductAndSubcategory(categoryId)
                .enqueue(new CustomCB<CategoryFeaturedProductAndSubcategoryResponse>());
    }

    public void getQueryMap() {
        queryMapping.put(Api.qs_page_number, "" + pageNumber);
    }

    public void onEvent(ProductsResponse response) {
        if (response != null && response.getStatusCode() == 200) {

            categoryNameTextView.setText(response.getName());
            Log.e("product", "onEvent: " + response.getName());

            if (response.getAvailableSortOptions() != null && productAdapter == null) {
                availableSortOptions.clear();
                availableSortOptions.addAll(response.getAvailableSortOptions());
            }

            List<ProductModel> responseProducts = response.getProducts();
            if (responseProducts != null && responseProducts.size() > 0) {
                if (productAdapter == null) {
                    if (response.getPriceRange() != null) {
                        filterFragment.setPriceFilter(response.getPriceRange());
                    }

                    listProduct.setLayoutManager(layoutManager);
                    productAdapter = new ProductListAdapter(getContext(), responseProducts, listProduct, response.getTotalPages());
                    listProduct.setAdapter(productAdapter);

                    productAdapter.setOnLoadMoreListener(new ProductListAdapter.OnLoadMoreListener() {
                        @Override
                        public void onLoadMore(int currentPage) {
                            callWebServiceMoreLoad(currentPage);
                            productAdapter.showLoader();
                            listProduct.post(new Runnable() {
                                @Override
                                public void run() {
                                    productAdapter.notifyItemInserted(productAdapter.getItemCount() - 1);
                                }
                            });
                        }
                    });

                    productAdapter.setOnProductClickListener(new ProductListAdapter.OnProductClickListener() {
                        @Override
                        public void onProductClick(View view, ProductModel product) {

                            ProductDetailFragment.productModel = product;
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.container, new ProductDetailFragment())
                                    .addToBackStack(null)
                                    .commit();
                        }
                    });
                } else {
                    productAdapter.setLoaded();
                    if (resetList) {
                        resetList = false;
                        productAdapter.setTotalPage(response.getTotalPages());
                        productAdapter.addMoreProducts(responseProducts);
                        productAdapter.notifyDataSetChanged();
                    } else {
                        productAdapter.hideLoader();

                        int start = productAdapter.getItemCount();
                        int size = responseProducts.size();
                        productAdapter.addMoreProducts(responseProducts);
                        productAdapter.notifyItemRangeChanged(start, size);
                    }
                }
            } else if (responseProducts != null && responseProducts.size() == 0 && productAdapter != null && productAdapter.getItemCount() > 0) {
                productAdapter.setLoaded();
                productAdapter.hideLoader();
            }

            filterFragment.removeSpecificationList();
            if (response.getFilterItems() != null && response.getFilterItems().size() > 0) {
                filterFragment.setSpecificationFilterItem(response.getFilterItems());
            }

        } else if (productAdapter != null && productAdapter.getItemCount() > 0) {
            productAdapter.setLoaded();
            productAdapter.hideLoader();
        }
    }

    public void onEvent(final CategoryFeaturedProductAndSubcategoryResponse response) {
        if (response != null
                && response.getStatusCode() == 200
                && response.getSubCategories() != null
                && response.getSubCategories().size() > 0) {


            Log.e("product", "onItemClick: open cat product");
            Log.e("product", "onItemClick: open cat product2" + response.getName());
            categoryNameTextView.setVisibility(View.VISIBLE);

            final ListPopupWindow subcategoryPopupWindow = new ListPopupWindow(getContext());
            subcategoryPopupWindow.setAdapter(new SubCategoryAdapter(getActivity(), response.getSubCategories()));
            subcategoryPopupWindow.setAnchorView(categoryNameTextView);
            subcategoryPopupWindow.setModal(true);
            subcategoryPopupWindow.setBackgroundDrawable(new ColorDrawable(0));

            categoryNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (subcategoryPopupWindow.isShowing()) {
                        subcategoryPopupWindow.dismiss();
                    } else {
                        subcategoryPopupWindow.show();
                    }
                }
            });

            subcategoryPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ProductService.productId = response.getSubCategories().get(position).getId();
                    Log.e("product", "onItemClick: " + response.getSubCategories().get(position).getId() +
                            response.getSubCategories().get(position).getName());

                    gotoSubCategory(response.getSubCategories().get(position));
                    subcategoryPopupWindow.dismiss();
                    categoryNameTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_chevron_down_light, 0);
                }
            });
        }
    }

    private void gotoSubCategory(Category category) {

        if (category.getId() == 213 || category.getId() == 214 ||
                category.getId() == 255 || category.getId() == 256 ||
                category.getId() == 257 || category.getId() == 258) {

            Intent intent = new Intent(getContext(), GalleryActivity.class);
            intent.putExtra("categoryId", category.getId());
            (getActivity()).startActivity(intent);

        } else {

            getFragmentManager().beginTransaction()
                    .replace(R.id.container, ProductListFragmentFor3_8.newInstance(category.getName(), category.getId()))
                    .addToBackStack(null).commit();
        }
    }
}
