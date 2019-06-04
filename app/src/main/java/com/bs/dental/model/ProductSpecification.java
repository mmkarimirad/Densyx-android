package com.bs.dental.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bs156 on 22-Dec-16.
 */

public class ProductSpecification {
    @SerializedName("SpecificationAttributeId")
    private int id;

    @SerializedName("SpecificationAttributeName")
    private String name;

    @SerializedName("ValueRaw")
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
