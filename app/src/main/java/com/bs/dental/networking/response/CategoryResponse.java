package com.bs.dental.networking.response;

import com.bs.dental.model.Category;
import com.bs.dental.networking.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ashraful on 11/6/2015.
 */
public class CategoryResponse extends BaseResponse{
    public List<Category> getData() {
        return Data;
    }

    public void setData(List<Category> data) {
        this.Data = data;
    }

    private List<Category> Data;

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    private int Count;

    @SerializedName("DisplayTaxInOrderSummary")
    private boolean displayTaxInOrderSummary ;

    @SerializedName("ShowDiscountBox")
    private boolean showDiscountBox;


    public boolean isDisplayTaxInOrderSummary() {
        return displayTaxInOrderSummary;
    }

    public void setDisplayTaxInOrderSummary(boolean displayTaxInOrderSummary) {
        this.displayTaxInOrderSummary = displayTaxInOrderSummary;
    }

    public boolean isShowDiscountBox() {
        return showDiscountBox;
    }

    public void setShowDiscountBox(boolean showDiscountBox) {
        this.showDiscountBox = showDiscountBox;
    }
}
