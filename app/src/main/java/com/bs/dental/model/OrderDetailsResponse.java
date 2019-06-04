package com.bs.dental.model;

import com.bs.dental.networking.BaseResponse;

import java.util.List;

/**
 * Created by bs-110 on 12/18/2015.
 */
public class OrderDetailsResponse extends BaseResponse {
    private int Id;
    private boolean PrintMode;
    private boolean PdfInvoiceDisabled;
    private String CreatedOn;
    private String OrderStatus;
    private boolean IsReOrderAllowed;
    private boolean IsReturnRequestAllowed;
    private boolean IsShippable;
    private boolean PickUpInStore;
    private CustomerAddress ShippingAddress;
    private CustomerAddress BillingAddress;
    private CustomerAddress PickupAddress;

    private String OrderSubtotal;
    private String  OrderSubTotalDiscount;
    private String OrderShipping;
    private String  PaymentMethodAdditionalFee;
    private String CheckoutAttributeInfo;
    private boolean PricesIncludeTax;
    private boolean DisplayTaxShippingInfo;
    private String Tax;
    private boolean DisplayTax;
    private boolean DisplayTaxRates;
    private String OrderTotalDiscount;
    private int RedeemedRewardPoints;
    private String  RedeemedRewardPointsAmount;
    private String OrderTotal;

    private  String PaymentMethod;
    private  String PaymentMethodStatus;
    private String ShippingMethod;
    private String ShippingStatus;

    private List<CartProduct> Items;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isPrintMode() {
        return PrintMode;
    }

    public void setPrintMode(boolean printMode) {
        PrintMode = printMode;
    }

    public boolean isPdfInvoiceDisabled() {
        return PdfInvoiceDisabled;
    }

    public void setPdfInvoiceDisabled(boolean pdfInvoiceDisabled) {
        PdfInvoiceDisabled = pdfInvoiceDisabled;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public boolean isReOrderAllowed() {
        return IsReOrderAllowed;
    }

    public void setReOrderAllowed(boolean reOrderAllowed) {
        IsReOrderAllowed = reOrderAllowed;
    }

    public boolean isReturnRequestAllowed() {
        return IsReturnRequestAllowed;
    }

    public void setReturnRequestAllowed(boolean returnRequestAllowed) {
        IsReturnRequestAllowed = returnRequestAllowed;
    }

    public boolean isShippable() {
        return IsShippable;
    }

    public void setShippable(boolean shippable) {
        IsShippable = shippable;
    }

    public boolean isPickUpInStore() {
        return PickUpInStore;
    }

    public void setPickUpInStore(boolean pickUpInStore) {
        PickUpInStore = pickUpInStore;
    }

    public String getShippingStatus() {
        return ShippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        ShippingStatus = shippingStatus;
    }

    public CustomerAddress getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(CustomerAddress shippingAddress) {
        ShippingAddress = shippingAddress;
    }

    public CustomerAddress getBillingAddress() {
        return BillingAddress;
    }

    public void setBillingAddress(CustomerAddress billingAddress) {
        BillingAddress = billingAddress;
    }

    public String getOrderSubtotal() {
        return OrderSubtotal;
    }

    public void setOrderSubtotal(String orderSubtotal) {
        OrderSubtotal = orderSubtotal;
    }


    public String getOrderShipping() {
        return OrderShipping;
    }

    public void setOrderShipping(String orderShipping) {
        OrderShipping = orderShipping;
    }


    public String getCheckoutAttributeInfo() {
        return CheckoutAttributeInfo;
    }

    public void setCheckoutAttributeInfo(String checkoutAttributeInfo) {
        CheckoutAttributeInfo = checkoutAttributeInfo;
    }

    public boolean isPricesIncludeTax() {
        return PricesIncludeTax;
    }

    public void setPricesIncludeTax(boolean pricesIncludeTax) {
        PricesIncludeTax = pricesIncludeTax;
    }

    public boolean isDisplayTaxShippingInfo() {
        return DisplayTaxShippingInfo;
    }

    public void setDisplayTaxShippingInfo(boolean displayTaxShippingInfo) {
        DisplayTaxShippingInfo = displayTaxShippingInfo;
    }

    public String getTax() {
        return Tax;
    }

    public void setTax(String tax) {
        Tax = tax;
    }

    public boolean isDisplayTax() {
        return DisplayTax;
    }

    public void setDisplayTax(boolean displayTax) {
        DisplayTax = displayTax;
    }

    public boolean isDisplayTaxRates() {
        return DisplayTaxRates;
    }

    public void setDisplayTaxRates(boolean displayTaxRates) {
        DisplayTaxRates = displayTaxRates;
    }

    public String getOrderTotalDiscount() {
        return OrderTotalDiscount;
    }

    public void setOrderTotalDiscount(String orderTotalDiscount) {
        OrderTotalDiscount = orderTotalDiscount;
    }

    public int getRedeemedRewardPoints() {
        return RedeemedRewardPoints;
    }

    public void setRedeemedRewardPoints(int redeemedRewardPoints) {
        RedeemedRewardPoints = redeemedRewardPoints;
    }


    public String getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        OrderTotal = orderTotal;
    }

    public List<CartProduct> getItems() {
        return Items;
    }

    public void setItems(List<CartProduct> items) {
        Items = items;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public String getPaymentMethodStatus() {
        return PaymentMethodStatus;
    }

    public void setPaymentMethodStatus(String paymentMethodStatus) {
        PaymentMethodStatus = paymentMethodStatus;
    }

    public String getShippingMethod() {
        return ShippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        ShippingMethod = shippingMethod;
    }


    public String getOrderSubTotalDiscount() {
        return OrderSubTotalDiscount;
    }

    public void setOrderSubTotalDiscount(String orderSubTotalDiscount) {
        OrderSubTotalDiscount = orderSubTotalDiscount;
    }

    public String getPaymentMethodAdditionalFee() {
        return PaymentMethodAdditionalFee;
    }

    public void setPaymentMethodAdditionalFee(String paymentMethodAdditionalFee) {
        PaymentMethodAdditionalFee = paymentMethodAdditionalFee;
    }

    public String getRedeemedRewardPointsAmount() {
        return RedeemedRewardPointsAmount;
    }

    public void setRedeemedRewardPointsAmount(String redeemedRewardPointsAmount) {
        RedeemedRewardPointsAmount = redeemedRewardPointsAmount;
    }

    public CustomerAddress getPickupAddress() {
        return PickupAddress;
    }

    public void setPickupAddress(CustomerAddress pickupAddress) {
        PickupAddress = pickupAddress;
    }
}
