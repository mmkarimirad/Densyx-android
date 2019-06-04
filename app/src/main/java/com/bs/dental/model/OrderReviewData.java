package com.bs.dental.model;

/**
 * Created by Ashraful on 12/11/2015.
 */
public class OrderReviewData {
    private com.bs.dental.model.BillingAddress BillingAddress;

    private com.bs.dental.model.BillingAddress ShippingAddress;
    private com.bs.dental.model.BillingAddress PickupAddress;
    private String ShippingMethod;
    private String PaymentMethod;
    private boolean SelectedPickUpInStore=false;

    public com.bs.dental.model.BillingAddress getBillingAddress() {
        return BillingAddress;
    }

    public void setBillingAddress(com.bs.dental.model.BillingAddress billingAddress) {
        BillingAddress = billingAddress;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public com.bs.dental.model.BillingAddress getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(com.bs.dental.model.BillingAddress shippingAddress) {
        ShippingAddress = shippingAddress;
    }

    public String getShippingMethod() {
        return ShippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        ShippingMethod = shippingMethod;
    }


    public com.bs.dental.model.BillingAddress getPickupAddress() {
        return PickupAddress;
    }

    public void setPickupAddress(com.bs.dental.model.BillingAddress pickupAddress) {
        PickupAddress = pickupAddress;
    }

    public boolean isSelectedPickUpInStore() {
        return SelectedPickUpInStore;
    }

    public void setSelectedPickUpInStore(boolean selectedPickUpInStore) {
        SelectedPickUpInStore = selectedPickUpInStore;
    }
}
