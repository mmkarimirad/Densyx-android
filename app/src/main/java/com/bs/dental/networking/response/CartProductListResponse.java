package com.bs.dental.networking.response;

import com.bs.dental.model.CartProduct;
import com.bs.dental.model.ProductAttribute;
import com.bs.dental.networking.BaseResponse;

import java.util.List;

/**
 * Created by Ashraful on 11/30/2015.
 */
public class CartProductListResponse extends BaseResponse {
    private List<CartProduct>Items;
    private List<ProductAttribute>CheckoutAttributes;
    private com.bs.dental.model.OrderReviewData OrderReviewData;
    private OrderTotalResponse OrderTotalResponseModel;

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    private  int Count;
    public List<ProductAttribute> getCheckoutAttributes() {
        return CheckoutAttributes;
    }

    public void setCheckoutAttributes(List<ProductAttribute> checkoutAttributes) {
        CheckoutAttributes = checkoutAttributes;
    }



    public com.bs.dental.model.OrderReviewData getOrderReviewData() {
        return OrderReviewData;
    }

    public void setOrderReviewData(com.bs.dental.model.OrderReviewData orderReviewData) {
        OrderReviewData = orderReviewData;
    }

    public List<CartProduct> getItems() {
        return Items;
    }

    public void setItems(List<CartProduct> items) {
        Items = items;
    }

    public OrderTotalResponse getOrderTotalResponseModel() {
        return OrderTotalResponseModel;
    }

    public void setOrderTotalResponseModel(OrderTotalResponse orderTotalResponseModel) {
        OrderTotalResponseModel = orderTotalResponseModel;
    }
}
