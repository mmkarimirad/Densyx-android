package com.bs.dental.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bs156 on 28-Feb-17.
 */

public class ProductFilterItem {
    @SerializedName("SpecificationAttributeName") private String specificationName;
    @SerializedName("SpecificationAttributeOptions") private List<FilterAttribute> attributes;

    public String getSpecificationName() {
        return specificationName;
    }

    public void setSpecificationName(String specificationName) {
        this.specificationName = specificationName;
    }

    public List<FilterAttribute> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<>();
        }
        return attributes;
    }

    public void setAttributes(List<FilterAttribute> attributes) {
        this.attributes = attributes;
    }
}
