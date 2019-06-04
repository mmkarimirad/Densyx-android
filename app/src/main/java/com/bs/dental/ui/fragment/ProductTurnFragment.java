package com.bs.dental.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import com.bs.dental.R;
import com.bs.dental.model.AvailableSortOption;
import com.bs.dental.model.CategoryFeaturedProductAndSubcategoryResponse;
import com.bs.dental.model.ProductTurn;
import com.bs.dental.model.ProductTurnObject;
import com.bs.dental.model.ProductModel;
import com.bs.dental.model.ViewType;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.RetroClientTurn;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.adapter.ProductTurnAdapter;
import com.bs.dental.ui.views.DrawerManipulationFromFragment;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.inject.InjectView;

/**
 * Created by bs156 on 23-Feb-17.
 * This filter is for nopCommerce version 3.7 and earlier
 */

public class ProductTurnFragment extends BaseFragment {

    private static final String CATEGORY_NAME = "categoryName";
    private static final String CATEGORY_ID = "categoryId";
    @InjectView(R.id.list_product)
    RecyclerView listProduct;
    @InjectView(R.id.rl_rootLayout) RelativeLayout rootViewRelativeLayout;
    //@InjectView(R.id.tv_category_name) TextView categoryNameTextView;
    @InjectView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @InjectView(R.id.bottomsheet) BottomSheetLayout bottomSheetLayout;

    private View rootView;
    List<ProductModel> productList;

    String categoryName;
    private GridLayoutManager layoutManager;
    private int newSpanCount = 2;
    private int itemViewType = ViewType.GRID;
    public Map<String, String> queryMapping = new HashMap<>();
    protected int pageNumber = 1;
    protected ProductTurnAdapter productAdapter;
    protected int categoryId = 0;
    FilterFragment filterFragment;
    private List<AvailableSortOption> availableSortOptions = new ArrayList<>();
    private int selectedPosition = -1;
    private ArrayAdapter<String> sortAdapter;
    protected boolean resetList = true;
    private ProductTurnObject pdo;

    public static ProductTurnFragment newInstance(String categoryName, int categoryId) {
        ProductTurnFragment fragment = new ProductTurnFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY_NAME, categoryName);
        args.putInt(CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_product_list_custom_turn, container, false);
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
        //inflater.inflate(R.menu.menu_product_list_fragment, menu);
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
            //showSortByView();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void drawerSetup() {
        DrawerManipulationFromFragment drawerManipulationFromFragment =
                new DrawerManipulationFromFragment(drawerLayout);
        drawerManipulationFromFragment.DrawerSetup(this);
        filterFragment = new FilterFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.drawer_filter, filterFragment).commit();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkEventBusRegistration();
        calculateAutomaticGridColumn();

        callWebService();

    }

    private void calculateAutomaticGridColumn() {
        layoutManager = new GridLayoutManager(getActivity(), 1);
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
        if (productAdapter != null) {
            productAdapter.resetList();
        }
        //resetList = true;
        //pageNumber = 1;
        //getQueryMap();
        // TODO: 5/17/2019 --- mmkr : remove comment after turn web service correction
        RetroClientTurn.getApi().getProductsTurn(preferenceService.GetPreferenceValue(PreferenceService.COOKIE_TURN))
                .enqueue(new CustomCB<ProductTurnObject>(rootViewRelativeLayout));
        /*RetroClientTurn.getApi().getProductTurn(Constants.COOKIE_TEMP)
                .enqueue(new CustomCB<ProductTurnObject>(rootViewRelativeLayout));*/
    }

    private void callSubCategoryList() {
        RetroClient.getApi().getCategoryFeaturedProductAndSubcategory(categoryId)
                .enqueue(new CustomCB<CategoryFeaturedProductAndSubcategoryResponse>());
    }

    public void onEvent(ProductTurnObject response) {
        if (response != null /*&& response.getStatusCode() == 200*/) {

            List<ProductTurn> responseProducts = response.getData();
            if (responseProducts != null && responseProducts.size() > 0) {
                if (productAdapter == null ) {

                    listProduct.setLayoutManager(layoutManager);
                    productAdapter = new ProductTurnAdapter(getContext(), responseProducts, listProduct, 1);
                    listProduct.setAdapter(productAdapter);

                } else {
                    productAdapter.setLoaded();
                    if (resetList) {
                        resetList = false;
                        //productAdapter.addMoreProducts(responseProducts);
                        productAdapter.notifyDataSetChanged();
                    } else {
                        //productAdapter.hideLoader();

                        int start = productAdapter.getItemCount();
                        int size = responseProducts.size();
                        //productAdapter.addMoreProducts(responseProducts);
                        productAdapter.notifyItemRangeChanged(start, size);
                    }
                }
            } else if (responseProducts != null && responseProducts.size() == 0 /*&& productAdapter != null && productAdapter.getItemCount() > 0*/) {
                //productAdapter.setLoaded();
                //productAdapter.hideLoader();
                showToast(getString(R.string.not_item_to_process));
            }

        } else if (productAdapter != null && productAdapter.getItemCount() > 0) {
            productAdapter.setLoaded();
            productAdapter.hideLoader();
        }
    }

}
