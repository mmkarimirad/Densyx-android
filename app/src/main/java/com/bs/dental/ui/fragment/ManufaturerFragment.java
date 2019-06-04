package com.bs.dental.ui.fragment;

import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.response.ProductsResponse;

/**
 * Created by Ashraful on 2/2/2016.
 */
public class ManufaturerFragment extends ProductListFragmentFor3_8 {
    public void callWebService() {
        if (productAdapter != null) {
            productAdapter.resetList();
        }
        resetList = true;
        pageNumber = 1;

        getQueryMap();
        RetroClient.getApi().getProductListByManufacturer(categoryId, queryMapping)
                .enqueue(new CustomCB<ProductsResponse>(rootViewRelativeLayout));
    }

    @Override
    public void onStart() {
        super.onStart();


    }
}
