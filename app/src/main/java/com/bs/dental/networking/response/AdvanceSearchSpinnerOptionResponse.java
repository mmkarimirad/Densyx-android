package com.bs.dental.networking.response;

import com.bs.dental.model.AdvanceSearchSpinnerOption;
import com.bs.dental.networking.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bs156 on 20-Feb-17.
 */
public class AdvanceSearchSpinnerOptionResponse  extends BaseResponse{
    @SerializedName("Categories")
    private List<AdvanceSearchSpinnerOption> categories;

    @SerializedName("Manufacturers")
    private List<AdvanceSearchSpinnerOption> manufacturer;

    public List<AdvanceSearchSpinnerOption> getCategories() {
        return categories;
    }

    public void setCategories(List<AdvanceSearchSpinnerOption> categories) {
        this.categories = categories;
    }

    public List<AdvanceSearchSpinnerOption> getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(List<AdvanceSearchSpinnerOption> manufacturer) {
        this.manufacturer = manufacturer;
    }
}
