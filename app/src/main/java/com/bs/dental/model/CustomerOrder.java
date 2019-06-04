package com.bs.dental.model;

/**
 * Created by bs-110 on 12/18/2015.
 */
public class CustomerOrder {
    private String OrderTotal;
    private boolean IsReturnRequestAllowed;
    private int OrderStatusEnum;
    private String OrderStatus;
    private String PaymentStatus;
    private String ShippingStatus;
    private String CreatedOn;
    private int Id;


    public String getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        OrderTotal = orderTotal;
    }

    public boolean isReturnRequestAllowed() {
        return IsReturnRequestAllowed;
    }

    public void setReturnRequestAllowed(boolean returnRequestAllowed) {
        IsReturnRequestAllowed = returnRequestAllowed;
    }

    public int getOrderStatusEnum() {
        return OrderStatusEnum;
    }

    public void setOrderStatusEnum(int orderStatusEnum) {
        OrderStatusEnum = orderStatusEnum;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        PaymentStatus = paymentStatus;
    }

    public String getShippingStatus() {
        return ShippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        ShippingStatus = shippingStatus;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
