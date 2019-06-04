package com.bs.dental.networking.response;

import com.bs.dental.networking.BaseResponse;

/**
 * Created by Ashraful on 12/11/2015.
 */
public class CheckoutConfirmResponse extends BaseResponse{

    private long OrderId;
    private com.bs.dental.model.PayPal PayPal;
    private int Data;
    private boolean CompleteOrder;
    private int PaymentType;

    public void setData(int data) {
        Data = data;
    }
    public int getData( ) {
        return Data;
    }




    public boolean isCompleteOrder() {
        return CompleteOrder;
    }

    public void setCompleteOrder(boolean completeOrder) {
        CompleteOrder = completeOrder;
    }

    public com.bs.dental.model.PayPal getPayPal() {
        return PayPal;
    }

    public void setPayPal(com.bs.dental.model.PayPal payPal) {
        PayPal = payPal;
    }



    public long getOrderId() {
        return OrderId;
    }

    public void setOrderId(long orderId) {
        OrderId = orderId;
    }

    public int getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(int paymentType) {
        PaymentType = paymentType;
    }
}
