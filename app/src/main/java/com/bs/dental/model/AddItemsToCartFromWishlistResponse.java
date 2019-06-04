package com.bs.dental.model;

import com.bs.dental.networking.BaseResponse;

/**
 * Created by bs-110 on 1/4/2016.
 */
public class AddItemsToCartFromWishlistResponse extends BaseResponse{
    int Count;
    int productId;
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }



    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}

