package com.bs.dental.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bs156 on 28-Feb-17.
 */
public class FilterAttribute {
    @SerializedName("SpecificationAttributeOptionName") private String attributeName;
    @SerializedName("FilterId") private int id;
    @SerializedName("Selected") private boolean isSelected;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
