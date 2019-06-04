package com.bs.dental.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BS168 on 3/3/2017.
 */

public class Quantity {

    @SerializedName("OrderMinimumQuantity")
    private int orderMinimumQuantity;

    @SerializedName("OrderMaximumQuantity")
    private int orderMaximumQuantity;

    @SerializedName("StockQuantity")
    private int stockQuantity;


    public int getOrderMinimumQuantity() {
        return orderMinimumQuantity;
    }

    public void setOrderMinimumQuantity(int orderMinimumQuantity) {
        this.orderMinimumQuantity = orderMinimumQuantity;
    }

    public int getOrderMaximumQuantity() {
        return orderMaximumQuantity;
    }

    public void setOrderMaximumQuantity(int orderMaximumQuantity) {
        this.orderMaximumQuantity = orderMaximumQuantity;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
