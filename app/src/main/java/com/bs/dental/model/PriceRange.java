package com.bs.dental.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ashraful on 11/11/2015.
 */
public class PriceRange {
    @SerializedName("From") private double from;
    @SerializedName("To") private double to;

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }
}
