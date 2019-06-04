package com.bs.dental.networking.postrequest;

/**
 * Created by Ashraful on 12/21/2015.
 */
public class PaypalCheckoutRequest {
    private String OrderId;
    private String PaymentId;

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getPaymentId() {
        return PaymentId;
    }

    public void setPaymentId(String paymentId) {
        PaymentId = paymentId;
    }


}
