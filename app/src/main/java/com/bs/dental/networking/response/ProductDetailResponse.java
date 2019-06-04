package com.bs.dental.networking.response;

import com.bs.dental.model.ProductDetail;
import com.bs.dental.networking.BaseResponse;

/**
 * Created by Ashraful on 11/20/2015.
 */
public class ProductDetailResponse extends BaseResponse {
    public ProductDetail getData() {
        return Data;
    }

    public void setData(ProductDetail data) {
        Data = data;
    }

    private ProductDetail Data;
}
