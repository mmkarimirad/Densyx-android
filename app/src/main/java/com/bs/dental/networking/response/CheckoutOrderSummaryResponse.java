package com.bs.dental.networking.response;

import com.bs.dental.networking.BaseResponse;

/**
 * Created by Ashraful on 12/11/2015.
 */
public class CheckoutOrderSummaryResponse extends BaseResponse {
    private  OrderTotalResponse OrderTotalModel;
    private CartProductListResponse ShoppingCartModel ;

    public OrderTotalResponse getOrderTotalModel() {
        return OrderTotalModel;
    }

    public void setOrderTotalModel(OrderTotalResponse orderTotalModel) {
        OrderTotalModel = orderTotalModel;
    }

    public CartProductListResponse getShoppingCartModel() {
        return ShoppingCartModel;
    }

    public void setShoppingCartModel(CartProductListResponse shoppingCartModel) {
        ShoppingCartModel = shoppingCartModel;
    }


}
