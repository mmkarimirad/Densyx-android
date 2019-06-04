package com.bs.dental.ui.fragment;

/**
 * Created by Ashraful on 3/25/2016.
 */
public class BarcodeProductDetailsFragment extends ProductDetailFragment {

    @Override
    protected void setProduceNamePrice() {

        if(productModel.getName()!=null && !productModel.getName().isEmpty())
         super.setProduceNamePrice();

        }

}
