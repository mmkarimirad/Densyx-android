package com.bs.dental.model;

/**
 * Created by Ashraful on 11/9/2015.
 */
public class ProductPrice {
    private String OldPrice;
    private String Price;
    private Boolean CallForPrice;

    public Boolean getCallForPrice() {
        return CallForPrice;
    }

    public void setCallForPrice(Boolean callForPrice) {
        CallForPrice = callForPrice;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getOldPrice() {
        return OldPrice;
    }

    public void setOldPrice(String oldPrice) {
        OldPrice = oldPrice;
    }

}
