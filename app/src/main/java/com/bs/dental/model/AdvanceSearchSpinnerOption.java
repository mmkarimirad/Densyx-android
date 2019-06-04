package com.bs.dental.model;

import com.bs.dental.utils.TextUtils;
import com.google.gson.annotations.SerializedName;

/**
 * Created by bs156 on 20-Feb-17.
 */

public class AdvanceSearchSpinnerOption {
    @SerializedName("Id")
    private int id;

    @SerializedName("Name")
    private String name;

    public AdvanceSearchSpinnerOption(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return TextUtils.getNullSafeString(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
