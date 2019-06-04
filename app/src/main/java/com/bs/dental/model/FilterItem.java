package com.bs.dental.model;

/**
 * Created by Ashraful on 12/15/2015.
 */
public class FilterItem {
    private String SpecificationAttributeName;
    private String SpecificationAttributeOptionName;
    private int FilterId;

    public int getFilterId() {
        return FilterId;
    }

    public void setFilterId(int filterId) {
        FilterId = filterId;
    }

    public String getSpecificationAttributeName() {
        return SpecificationAttributeName;
    }

    public void setSpecificationAttributeName(String specificationAttributeName) {
        SpecificationAttributeName = specificationAttributeName;
    }

    public String getSpecificationAttributeOptionName() {
        return SpecificationAttributeOptionName;
    }

    public void setSpecificationAttributeOptionName(String specificationAttributeOptionName) {
        SpecificationAttributeOptionName = specificationAttributeOptionName;
    }


}
