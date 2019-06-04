package com.bs.dental.networking.response;

import com.bs.dental.model.TaxRate;
import com.bs.dental.networking.BaseResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashraful on 12/4/2015.
 */
public class OrderTotalResponse extends BaseResponse {
    private Object SelectedShippingMethod;
    private Object PaymentMethodAdditionalFee;
    private Object RedeemedRewardPointsAmount;
    private Object SubTotalDiscount;
    private String OrderTotalDiscount;
    private List<Object> GiftCards = new ArrayList<>();

    private boolean DisplayTax;
    private boolean DisplayTaxRates;

    private boolean AllowRemovingOrderTotalDiscount;
    private long RedeemedRewardPoints;
    private long WillEarnRewardPoints;
    private String OrderTotal;
    private boolean IsEditable;
    private String SubTotal;
    private boolean AllowRemovingSubTotalDiscount;
    private String Shipping;
    private boolean RequiresShipping;


    private String Tax;

    public List<TaxRate> getTaxRates() {
        return TaxRates;
    }

    public void setTaxRates(List<TaxRate> taxRates) {
        TaxRates = taxRates;
    }

    private List<TaxRate>TaxRates;




    public boolean isEditable() {
        return IsEditable;
    }

    public void setIsEditable(boolean isEditable) {
        IsEditable = isEditable;
    }


    public String getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        OrderTotal = orderTotal;
    }

    public String getOrderTotalDiscount() {
        return OrderTotalDiscount;
    }

    public void setOrderTotalDiscount(String orderTotalDiscount) {
        OrderTotalDiscount = orderTotalDiscount;
    }

    public Object getPaymentMethodAdditionalFee() {
        return PaymentMethodAdditionalFee;
    }

    public void setPaymentMethodAdditionalFee(Object paymentMethodAdditionalFee) {
        PaymentMethodAdditionalFee = paymentMethodAdditionalFee;
    }

    public long getRedeemedRewardPoints() {
        return RedeemedRewardPoints;
    }

    public void setRedeemedRewardPoints(long redeemedRewardPoints) {
        RedeemedRewardPoints = redeemedRewardPoints;
    }

    public Object getRedeemedRewardPointsAmount() {
        return RedeemedRewardPointsAmount;
    }

    public void setRedeemedRewardPointsAmount(Object redeemedRewardPointsAmount) {
        RedeemedRewardPointsAmount = redeemedRewardPointsAmount;
    }

    public boolean isRequiresShipping() {
        return RequiresShipping;
    }

    public void setRequiresShipping(boolean requiresShipping) {
        RequiresShipping = requiresShipping;
    }

    public Object getSelectedShippingMethod() {
        return SelectedShippingMethod;
    }

    public void setSelectedShippingMethod(Object selectedShippingMethod) {
        SelectedShippingMethod = selectedShippingMethod;
    }

    public String getShipping() {
        return Shipping;
    }

    public void setShipping(String shipping) {
        Shipping = shipping;
    }

    public String getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(String subTotal) {
        SubTotal = subTotal;
    }

    public Object getSubTotalDiscount() {
        return SubTotalDiscount;
    }

    public void setSubTotalDiscount(Object subTotalDiscount) {
        SubTotalDiscount = subTotalDiscount;
    }

    public String getTax() {
        return Tax;
    }

    public void setTax(String tax) {
        Tax = tax;
    }

    public long getWillEarnRewardPoints() {
        return WillEarnRewardPoints;
    }

    public void setWillEarnRewardPoints(long willEarnRewardPoints) {
        WillEarnRewardPoints = willEarnRewardPoints;
    }



    public boolean isAllowRemovingOrderTotalDiscount() {
        return AllowRemovingOrderTotalDiscount;
    }

    public void setAllowRemovingOrderTotalDiscount(boolean allowRemovingOrderTotalDiscount) {
        AllowRemovingOrderTotalDiscount = allowRemovingOrderTotalDiscount;
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


}
