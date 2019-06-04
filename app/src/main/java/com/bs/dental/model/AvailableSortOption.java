package com.bs.dental.model;

/**
 * Created by Ashraful on 12/15/2015.
 */
public class AvailableSortOption {
    private Object Group;

    private Boolean Disabled;
    private Boolean Selected;
    private String Text;
    private String Value;

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public Boolean isSelected() {
        return Selected;
    }

    public void setSelected(Boolean selected) {
        Selected = selected;
    }

    public Object getGroup() {
        return Group;
    }

    public void setGroup(Object group) {
        Group = group;
    }

    public Boolean isDisabled() {
        return Disabled;
    }

    public void setDisabled(Boolean disabled) {
        Disabled = disabled;
    }


}
