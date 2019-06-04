package com.bs.dental.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bs-110 on 1/13/2016.
 */
public class Search{

    @SerializedName("q")
    private String query;

    @SerializedName("adv")
    private boolean isAdvanceSearchSelected;

    @SerializedName("cid")
    private String categoryId;

    @SerializedName("isc")
    private boolean isSearchInSubcategory;

    @SerializedName("mid")
    private String manufacturerId;

    @SerializedName("pf")
    private String priceFrom;

    @SerializedName("pt")
    private String priceTo;

    @SerializedName("sid")
    private boolean isSearchInDescription;

    public Search() {
    }

    public Search(String query) {
        this.query = query;
    }


    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isAdvanceSearchSelected() {
        return isAdvanceSearchSelected;
    }

    public void setAdvanceSearchSelected(boolean advanceSearchSelected) {
        isAdvanceSearchSelected = advanceSearchSelected;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public boolean isSearchInSubcategory() {
        return isSearchInSubcategory;
    }

    public void setSearchInSubcategory(boolean searchInSubcategory) {
        isSearchInSubcategory = searchInSubcategory;
    }

    public String getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(String priceFrom) {
        this.priceFrom = priceFrom;
    }

    public String getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(String priceTo) {
        this.priceTo = priceTo;
    }

    public boolean isSearchInDescription() {
        return isSearchInDescription;
    }

    public void setSearchInDescription(boolean searchInDescription) {
        isSearchInDescription = searchInDescription;
    }
}
