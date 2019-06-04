package com.bs.dental.ui.fragment;

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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.constants.ProductSort;
import com.bs.dental.model.AvailableSortOption;
import com.bs.dental.model.Category;
import com.bs.dental.model.CategoryFeaturedProductAndSubcategoryResponse;
import com.bs.dental.model.ProductModel;
import com.bs.dental.model.ViewType;
import com.bs.dental.networking.Api;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.response.ProductsResponse;
import com.bs.dental.ui.adapter.CategoryHomePageAdapter;
import com.bs.dental.ui.adapter.ProductListAdapter;
import com.bs.dental.ui.views.DrawerManipulationFromFragment;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.inject.InjectView;

/**
 * Created by employee on 4/10/2018.
 */

public class ProductListFragmentForHomePage extends BaseFragment {

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
    List<ProductModel> responseProducts;

    public ImageView iv_toolbar;

    public static ProductListFragmentForHomePage newInstance(String categoryName, int categoryId) {
        ProductListFragmentForHomePage fragment = new ProductListFragmentForHomePage();
        Bundle args = new Bundle();
        args.putString(CATEGORY_NAME, categoryName);
        args.putInt(CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.e("product", "onCreateView: " + "  open");
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_product_list_custom, container, false);

            //((AppCompatActivity) getActivity()).getSupportActionBar().hide();

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

        // show imageview in first fragment
        iv_toolbar = (ImageView) getActivity().findViewById(R.id.iv_toolbar);
        iv_toolbar.setVisibility(View.GONE);
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

            // =============================== choose default sort (expensive to cheap) ================================

            selectedPosition = 4;
            queryMapping.put("orderBy", "11");

            //

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

            responseProducts = response.getProducts();
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

            //start
            ArrayList<Category> categories = new ArrayList<>();
            for (int i = 0; i < response.getSubCategories().size(); i++) {
                Category category = new Category();
                category.setName(response.getSubCategories().get(i).getName());
                //category.setName("hi");
              /*  if (response.getSubCategories().get(i).getPictureModel().getImageUrl() != null) {
                    category.setImageUrl(response.getSubCategories().get(i).getPictureModel2().getImageUrl());
                }*/

                category.setImagePath(response.getSubCategories().get(i).getPictureModel().getImageUrl());

                //category.setImageUrl(null);
                category.setId(response.getSubCategories().get(i).getId());
                categories.add(category);
            }

            // ============================== Recyclerview subcategory ===============================

            CategoryHomePageAdapter categoryHomePageAdapter = new CategoryHomePageAdapter(getActivity(), categories, preferenceService, response.getSubCategories(), this );
            listProduct.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            listProduct.setAdapter(categoryHomePageAdapter);

        }
    }

}
