package com.bs.dental.model;

/**
 * Created by Ashraful on 12/8/2015.
 */
public class ShippingMethod {
    private String ShippingRateComputationMethodSystemName;
    private String Name;
    private String Description;
    private String Fee;
    private boolean Selected;
    private ShippingOption ShippingOption;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getFee() {
        return Fee;
    }

    public void setFee(String fee) {
        Fee = fee;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getShippingRateComputationMethodSystemName() {
        return ShippingRateComputationMethodSystemName;
    }

    public void setShippingRateComputationMethodSystemName(String shippingRateComputationMethodSystemName) {
        ShippingRateComputationMethodSystemName = shippingRateComputationMethodSystemName;
    }

    public com.bs.dental.model.ShippingOption getShippingOption() {
        return ShippingOption;
    }

    public void setShippingOption(com.bs.dental.model.ShippingOption shippingOption) {
        ShippingOption = shippingOption;
    }

}
