package com.bs.dental.ui.fragment;

import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.constants.ProductSort;
import com.bs.dental.model.AvailableSortOption;
import com.bs.dental.model.CategoryFeaturedProductAndSubcategoryResponse;
import com.bs.dental.model.ProductModel;
import com.bs.dental.model.ProductService;
import com.bs.dental.model.ViewType;
import com.bs.dental.networking.Api;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.response.ProductsResponse;
import com.bs.dental.ui.adapter.ProductAdapter;
import com.bs.dental.ui.adapter.SubCategoryAdapter;
import com.bs.dental.ui.views.DrawerManipulationFromFragment;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.inject.InjectView;

/**
 * Created by Ashraful on 11/11/2015.
 */
public class ProductsListFragment extends BaseFragment {
    @InjectView(R.id.drawerLayout)
    public DrawerLayout drawerLayout;
    @InjectView(R.id.drawer_filter)
    FrameLayout filterDrawer;

    @InjectView(R.id.list_product)
    SuperRecyclerView listProduct;

    @InjectView(R.id.rl_rootLayout)
    RelativeLayout rootViewRelativeLayout;

    @InjectView(R.id.bottomsheet)
    BottomSheetLayout bottomSheetLayout;

    @InjectView(R.id.imgBtn_viewtype)
    ImageButton productViewTypeImgBtn;

    @InjectView(R.id.tv_category_name)
    TextView categoryNameTextView;

    public Map<String, String> queryMapping = new HashMap<>();
    int pageNumber = 1;

    String categoryName;
    int itemViewtype = ViewType.GRID;

    private int newSpanCount = 1;

    GridLayoutManager layoutManager;

    public ProductAdapter productAdapter;

    List<ProductModel> productList;

    private int totalItemCount;
    private int visibleItemCount;
    private int pastVisiblesItems;
    protected int totalProductpage = 1;
    protected boolean loading = true;
    public boolean isFilterApplied;

    private View rootView;

    FilterFragment filterFragment;
    private int selectedPosition;

    List<AvailableSortOption> availableSortOptions = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_products_list, container, false);
            productList = null;
            selectedPosition = -1;
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkEventBusRegistration();

        getActivity().setTitle(categoryName);

        if (productList != null) {
            populateProductinGrid();
            filterFragment.generateView();
            filterFragment.generateAlreadyFilteredView();
        } else {
            drawerSetup();
            getQueryMap();
            //  setBottomSheetLayout();
            calculateAutomatiGridColumn();
            doActionOnItemViewTypeImgBtn();
            callWebService();

        }

    }

    private void callWebServiceGetFeaturedProductAndSubcategory() {
        RetroClient.getApi().getCategoryFeaturedProductAndSubcategory(ProductService.productId)
                .enqueue(new CustomCB<CategoryFeaturedProductAndSubcategoryResponse>());
    }

    public void callWebServiceMoreLoad() {
        getQueryMap();

        RetroClient.getApi().getProductList(ProductService.productId, queryMapping)
                .enqueue(new CustomCB<ProductsResponse>(rootViewRelativeLayout));
    }

    public void onEvent(final CategoryFeaturedProductAndSubcategoryResponse response) {
        categoryNameTextView.setVisibility(View.GONE);

        if (response.getStatusCode() == 200) {
            if (response.getSubCategories() != null && response.getSubCategories().size() > 0) {
                categoryNameTextView.setText(categoryName);
                categoryNameTextView.setVisibility(View.VISIBLE);
                //categoryNameTextView.setCompoundDrawablesWithIntrinsicBounds(  0, 0, R.drawable.ic_chevron_down_light, 0);


                final ListPopupWindow listPopupWindow = new ListPopupWindow(getContext());
                listPopupWindow.setAdapter(new SubCategoryAdapter(getActivity(), response.getSubCategories()));
                listPopupWindow.setAnchorView(categoryNameTextView);
                listPopupWindow.setModal(true);
                listPopupWindow.setBackgroundDrawable(new ColorDrawable(0));

                categoryNameTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listPopupWindow.isShowing()) {
                            listPopupWindow.dismiss();
                            //categoryNameTextView.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.ic_chevron_down_light, 0);
                        } else {
                            listPopupWindow.show();
                            //categoryNameTextView.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.ic_chevron_up_light, 0);
                        }
                    }
                });

                listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ProductService.productId =
                                response.getSubCategories().get(position).getId();
                        gotoSubCategory();
                        listPopupWindow.dismiss();
                        categoryNameTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_chevron_down_light, 0);
                    }
                });
            }
        }
    }


    private void drawerSetup() {
        DrawerManipulationFromFragment drawerManipulationFromFragment =
                new DrawerManipulationFromFragment(drawerLayout);
        drawerManipulationFromFragment.DrawerSetup(this);
        filterFragment = new FilterFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.drawer_filter, filterFragment).commit();
    }


    public void callWebService() {
        getQueryMap();
        RetroClient.getApi().getProductList(ProductService.productId, queryMapping)
                .enqueue(new CustomCB<ProductsResponse>(rootViewRelativeLayout));
    }

    public void getQueryMap() {
        queryMapping.put(Api.qs_page_number, "" + pageNumber);
    }


    private void doActionOnItemViewTypeImgBtn() {
        final int threshold = 2;
        productViewTypeImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productAdapter != null) {
                    itemViewtype++;
                    itemViewtype = itemViewtype % threshold;
                    setProductListViewType();
                }
            }
        });
    }


    private void setProductListViewType() {
        if (itemViewtype == ViewType.GRID) {
            setGridProductView();
        } else if (itemViewtype == ViewType.LIST) {
            setListProductView();
        } else {
            productViewTypeImgBtn.setImageResource(R.drawable.product_more);
        }


    }


    private void setGridProductView() {
        productViewTypeImgBtn.setImageResource(R.drawable.ic_gridview);
        productAdapter.ViewFormat = ViewType.GRID;
        updateColumninPerRow(newSpanCount);
    }


    private void setListProductView() {
        productViewTypeImgBtn.setImageResource(R.drawable.ic_listview);
        productAdapter.ViewFormat = ViewType.LIST;
        updateColumninPerRow(1);
    }


    private void updateColumninPerRow(int numberofColumPerRow) {
        layoutManager.setSpanCount(numberofColumPerRow);
        layoutManager.requestLayout();
    }


    private void calculateAutomatiGridColumn() {
        // listProduct.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 1);


        listProduct.setLayoutManager(layoutManager);

        listProduct.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT < 16) {
                            listProduct.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            listProduct.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                        int viewWidth = listProduct.getMeasuredWidth();
                        float cardViewWidth =
                                getActivity().getResources().getDimension(R.dimen.cardviewWidth);
                        newSpanCount = (int) Math.floor(viewWidth / cardViewWidth);
                        if (itemViewtype == ViewType.GRID) {
                            updateColumninPerRow(newSpanCount);
                        }
                    }
                });
        //  setScrollListener();

    }


    private void setScrollListener() {
       /* if (totalProductpage > pageNumber)
        listProduct.setLoadingMore(true);*/

        listProduct.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
                if (totalProductpage > pageNumber) {
                    ++pageNumber;

                    callWebService();
                } else {
                    listProduct.hideProgress();

                    listProduct.hideMoreProgress();
                    //   listProduct.setLoadingMore(false);
                }


            }
        }, 1);


    }

    protected void populateProductinGrid() {
        //   productAdapter = new ProductAdapter(getActivity(), patientList);
        // productAdapter.notifyDataSetChanged();
        //     listProduct.setAdapter(productAdapter);
        setScrollListener();

        productAdapter.SetOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (view instanceof CheckBox) {

                } else

                {
                    ProductDetailFragment.productModel = productAdapter.products.get(position);
                    getFragmentManager().beginTransaction().replace(R.id.container, new ProductDetailFragment()).addToBackStack(null).commit();

                }
            }
        });
    }

    public void onEvent(ProductsResponse productsResponse) {
        hideprogress();
        totalProductpage = productsResponse.getTotalPages();
        categoryName = productsResponse.getName();
        getActivity().setTitle(categoryName);
        loading = true;
        this.availableSortOptions = productsResponse.getAvailableSortOptions();


        if (productsResponse.getProducts() != null && productsResponse.getProducts().size() == 0) {
            showSnack(getString(R.string.no_product_found));
        }
        if (productList != null && !isFilterApplied) {
            int range = productAdapter.getItemCount() + 1;
            // productAdapter.products.addAll(productsResponse.getProducts());
            productAdapter.addAll(productsResponse.getProducts());

            System.out.print("Size" + productList.size());
            productAdapter.notifyItemRangeInserted(range, productsResponse.getProducts().size());
            productAdapter.notifyDataSetChanged();
        } else {
            productList = productsResponse.getProducts();
            productAdapter = new ProductAdapter(getActivity(), productList);
            listProduct.setAdapter(productAdapter);
            productAdapter.notifyDataSetChanged();
            isFilterApplied = false;
            callWebServiceGetFeaturedProductAndSubcategory();
            if (productList.size() != 0) {
                setScrollListener();
            }
        }
        //  listProduct.hideMoreProgress();
        populateProductinGrid();
        processFilter(productsResponse);
    }

    protected void processFilter(ProductsResponse productsResponse) {
        try {
            if (productsResponse.getNotFilteredItems().size() > 0) {
                filterFragment.generateFilterView(productsResponse.getNotFilteredItems());
            } else if (productsResponse.getPriceRange() != null) {
                filterFragment.setPriceFilter(productsResponse.getPriceRange());
            } else {
                filterFragment.actionOnNoitemFilter();
            }
            if (productsResponse.getAlreadyFilteredItems().size() > 0) {
                filterFragment.generateAlreadyFilteredView(productsResponse.getAlreadyFilteredItems());
            }
        } catch (Exception ex) {

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        hideprogress();

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

    protected void showSortByView() {
        if (availableSortOptions != null) {
            LinearLayout sortLinearLayout = (LinearLayout) getLayoutInflater().
                    inflate(R.layout.list_sort_by, bottomSheetLayout, false);
            ListView sortListView = (ListView) sortLinearLayout.findViewById(R.id.lv_sortby);
            bottomSheetLayout.showWithSheetView(sortLinearLayout);
            sortListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.simple_list_item_single_choice, ProductSort.getSortOptionTextList(availableSortOptions));

            sortListView.setAdapter(adapter);
            if (selectedPosition >= 0) {
                sortListView.setItemChecked(selectedPosition, true);
            }

            sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String value = availableSortOptions.get(position).getValue();
                    selectedPosition = position;
                    queryMapping.put("orderBy", value);
                    pageNumber = 1;
                    isFilterApplied = true;
                    callWebService();
                    bottomSheetLayout.dismissSheet();

                }
            });
        }
    }


    private void gotoSubCategory() {
        getFragmentManager().beginTransaction().replace
                (R.id.container, new ProductsListFragment()).addToBackStack(null).commit();
    }

    public void hideprogress() {
        listProduct.hideProgress();
        listProduct.hideMoreProgress();
        // listProduct.setLoadingMore(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        pushAnalyticalEvent();
    }

    private void pushAnalyticalEvent() {
        String SCREEN_NAME = "";
        if (this.getClass().equals(ManufaturerFragment.class)) {
            SCREEN_NAME = "Manfucturer Product List";
        } else if (this.getClass().equals(ProductSearchFragment.class)) {
            SCREEN_NAME = "Search Product List";
        } else if (this.getClass().equals(ProductsListFragment.class)) {
            SCREEN_NAME = "Category Product List ";
        }

        //pushAnalyticsEvent(SCREEN_NAME);
    }


}