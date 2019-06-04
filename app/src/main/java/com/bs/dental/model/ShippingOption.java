package com.bs.dental.model;

/**
 * Created by Ashraful on 12/8/2015.
 */
public class ShippingOption {

    private String ShippingRateComputationMethodSystemName;
    private double Rate;
    private String Name;
    private String Description;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getRate() {
        return Rate;
    }

    public void setRate(double rate) {
        Rate = rate;
    }

    public String getShippingRateComputationMethodSystemName() {
        return ShippingRateComputationMethodSystemName;
    }

    public void setShippingRateComputationMethodSystemName(String shippingRateComputationMethodSystemName) {
        ShippingRateComputationMethodSystemName = shippingRateComputationMethodSystemName;
    }


}
