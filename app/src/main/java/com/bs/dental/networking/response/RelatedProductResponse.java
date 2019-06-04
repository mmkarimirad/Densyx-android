package com.bs.dental.networking.response;

import com.bs.dental.model.ProductModel;
import com.bs.dental.networking.BaseResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashraful on 11/27/2015.
 */
public class RelatedProductResponse extends BaseResponse {
    public List<ProductModel> getData() {
        return Data;
    }

    public void setData(List<ProductModel> data) {
        Data = data;
    }

    private List<ProductModel> Data=new ArrayList<>();
}
