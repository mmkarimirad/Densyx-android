package com.bs.dental.model;

import com.bs.dental.networking.BaseResponse;

/**
 * Created by bs-110 on 12/22/2015.
 */
public class ConfirmPayPalCheckoutResponse extends BaseResponse{
    long OrderId;

    public long getOrderId() {
        return OrderId;
    }

    public void setOrderId(long orderId) {
        OrderId = orderId;
    }
}
