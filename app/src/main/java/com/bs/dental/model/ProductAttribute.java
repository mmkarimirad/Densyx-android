package com.bs.dental.model;

import java.util.List;

/**
 * Created by Ashraful on 11/20/2015.
 */
public class ProductAttribute {
    private long ProductId;
    private int Id;



    private long ProductAttributeId;
    private String Name;
    private String Description;
    private String TextPrompt;
    private boolean IsRequired;
    private String DefaultValue;
    private int AttributeControlType;
    private List<AttributeControlValue>Values;
    private Object SelectedDay;
    private Object SelectedMonth;
    private Object SelectedYear;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
    public List<AttributeControlValue> getValues() {
        return Values;
    }

    public void setValues(List<AttributeControlValue> values) {
        Values = values;
    }

    public int getAttributeControlType() {
        return AttributeControlType;
    }

    public void setAttributeControlType(int attributeControlType) {
        AttributeControlType = attributeControlType;
    }

    public String getDefaultValue() {
        return DefaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        DefaultValue = defaultValue;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isRequired() {
        return IsRequired;
    }

    public void setIsRequired(boolean isRequired) {
        IsRequired = isRequired;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public long getProductAttributeId() {
        return ProductAttributeId;
    }

    public void setProductAttributeId(long productAttributeId) {
        ProductAttributeId = productAttributeId;
    }

    public long getProductId() {
        return ProductId;
    }

    public void setProductId(long productId) {
        ProductId = productId;
    }

    public String getTextPrompt() {
        return TextPrompt;
    }

    public void setTextPrompt(String textPrompt) {
        TextPrompt = textPrompt;
    }


}
