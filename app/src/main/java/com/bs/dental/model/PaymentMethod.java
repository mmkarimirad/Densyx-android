package com.bs.dental.model;

/**
 * Created by Ashraful on 12/9/2015.
 */
public class PaymentMethod {
    private Object Fee;

    private String PaymentMethodSystemName;
    private String Name;
    private boolean Selected;
    private String LogoUrl;




    public String getLogoUrl() {
        return LogoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        LogoUrl = logoUrl;
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

    public String getPaymentMethodSystemName() {
        return PaymentMethodSystemName;
    }

    public void setPaymentMethodSystemName(String paymentMethodSystemName) {
        PaymentMethodSystemName = paymentMethodSystemName;
    }


}
