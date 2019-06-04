package com.bs.dental.model;

import java.util.List;

/**
 * Created by Ashraful on 12/7/2015.
 */
public class AvailableCountry {


    private boolean Disabled;
    private boolean Selected;
    private String Text;
    private String Value;

    public List<Object> getExistingAddresses() {
        return ExistingAddresses;
    }

    public void setExistingAddresses(List<Object> existingAddresses) {
        ExistingAddresses = existingAddresses;
    }

    private List<Object>ExistingAddresses;

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    public boolean isDisabled() {
        return Disabled;
    }

    public void setDisabled(boolean disabled) {
        Disabled = disabled;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }



}
