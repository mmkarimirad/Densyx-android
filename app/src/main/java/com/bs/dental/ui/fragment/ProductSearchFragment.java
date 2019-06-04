package com.bs.dental.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.bs.dental.MainActivity;
import com.bs.dental.R;
import com.bs.dental.model.Search;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.response.ProductsResponse;
import com.bs.dental.ui.adapter.ProductAdapter;

/**
 * Created by bs-110 on 1/13/2016.
 */
public class ProductSearchFragment extends ProductsListFragment {

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_product_search_fragment, menu);

        MenuItem item = menu.findItem(R.id.action_search);
       final SearchView sv = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW|MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("search query submit");
                clearList();
                callSearchWebService(query);
                getActivity().setTitle(query);
                sv.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("tap");
                return false;
            }
        });

        sv.setIconified(false);
        item.expandActionView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkEventBusRegistration();
        clearList();
        hideprogress();

        //filterDrawer.setVisibility(View.GONE);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }


    private void clearList(){
        if(productList != null) {
            productList.clear();
            productAdapter.products.clear();
            productAdapter.notifyDataSetChanged();
        }
    }

    private void callWebServiceGetFeaturedProductAndSubcategory() {}


    public void callWebService(){
        hideprogress();
    }

    public void callSearchWebService(String key){
        productList=null;
        RetroClient.getApi().searchProduct(new Search(key))
                .enqueue(new CustomCB<ProductsResponse>(rootViewRelativeLayout));
    }


    public void onEvent(ProductsResponse productsResponse)
    {
        hideprogress();
        totalProductpage = productsResponse.getTotalPages();
        categoryName = productsResponse.getName();
        getActivity().setTitle(categoryName);
        loading = true;
        if (productsResponse.getProducts() != null && productsResponse.getProducts().size() == 0){
            showSnack(getString(R.string.no_product_found));
        }
        if (productList != null ) {
            int range = productAdapter.getItemCount();
            productAdapter.products.addAll(productsResponse.getProducts());
            System.out.print("Size" + productList.size());
            productAdapter.notifyItemRangeInserted(range, productsResponse.getProducts().size());
            productAdapter.notifyDataSetChanged();

        } else{
            productList = productsResponse.getProducts();
            productAdapter = new ProductAdapter(getActivity(), productList);
            listProduct.setAdapter(productAdapter);
        }
        //  listProduct.hideMoreProgress();
        populateProductinGrid();
    }
    public void  hideprogress()
    {

        listProduct.hideProgress();
        listProduct.hideMoreProgress();
        // listProduct.setLoadingMore(false);
    }
}
