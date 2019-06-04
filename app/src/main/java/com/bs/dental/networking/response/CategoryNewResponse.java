package com.bs.dental.networking.response;

import com.bs.dental.model.Category;
import com.bs.dental.model.LanguageDM;
import com.bs.dental.networking.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ashraful on 11/6/2015.
 */
public class CategoryNewResponse extends BaseResponse{
    private List<Category> Data;
    private LanguageDM Language;

    @SerializedName("DisplayTaxInOrderSummary")
    private boolean displayTaxInOrderSummary ;

    @SerializedName("ShowDiscountBox")
    private boolean showDiscountBox;
    private int Count;

    public List<Category> getData() {
        return Data;
    }

    public void setData(List<Category> data) {
        this.Data = data;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

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

    public LanguageDM getLanguage() {
        return Language;
    }

    public void setLanguage(LanguageDM language) {
        Language = language;
    }
}
