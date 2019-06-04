package com.bs.dental.networking.response;

import com.bs.dental.networking.BaseResponse;

/**
 * Created by Ashraful on 12/10/2015.
 */
public class ShoppingCartCheckoutAttributeApplyResponse extends BaseResponse{
    public boolean isData() {
        return Data;
    }

    public void setData(boolean data) {
        Data = data;
    }

    private boolean Data;

    private OrderTotalResponse OrderTotalResponseModel;

    public OrderTotalResponse getOrderTotalResponseModel() {
        return OrderTotalResponseModel;
    }

    public void setOrderTotalResponseModel(OrderTotalResponse orderTotalResponseModel) {
        OrderTotalResponseModel = orderTotalResponseModel;
    }
}
