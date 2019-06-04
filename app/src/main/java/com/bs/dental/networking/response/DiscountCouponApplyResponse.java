package com.bs.dental.networking.response;

import com.bs.dental.networking.BaseResponse;

/**
 * Created by Ashraful on 12/4/2015.
 */
public class DiscountCouponApplyResponse extends BaseResponse {
    public boolean isData() {
        return Data;
    }
    private OrderTotalResponse OrderTotalResponseModel;

    public void setData(boolean data) {
        Data = data;
    }

    private boolean Data;

    public OrderTotalResponse getOrderTotalResponseModel() {
        return OrderTotalResponseModel;
    }

    public void setOrderTotalResponseModel(OrderTotalResponse orderTotalResponseModel) {
        OrderTotalResponseModel = orderTotalResponseModel;
    }
}
