package com.bs.dental.model;

import com.bs.dental.networking.BaseResponse;

/**
 * Created by Ashraful on 2/2/2016.
 */
public class AddAllItemsToCartFromWishlistResponse extends BaseResponse {
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
