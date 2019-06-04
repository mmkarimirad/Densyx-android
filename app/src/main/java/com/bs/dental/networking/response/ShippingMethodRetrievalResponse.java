package com.bs.dental.networking.response;

import com.bs.dental.model.ShippingMethod;
import com.bs.dental.networking.BaseResponse;

import java.util.List;

/**
 * Created by Ashraful on 12/8/2015.
 */
public class ShippingMethodRetrievalResponse extends BaseResponse{
    private List<ShippingMethod>ShippingMethods;
    private boolean NotifyCustomerAboutShippingFromMultipleLocations;


    public boolean isNotifyCustomerAboutShippingFromMultipleLocations() {
        return NotifyCustomerAboutShippingFromMultipleLocations;
    }

    public void setNotifyCustomerAboutShippingFromMultipleLocations(boolean notifyCustomerAboutShippingFromMultipleLocations) {
        NotifyCustomerAboutShippingFromMultipleLocations = notifyCustomerAboutShippingFromMultipleLocations;
    }


    public List<ShippingMethod> getShippingMethods() {
        return ShippingMethods;
    }

    public void setShippingMethods(List<ShippingMethod> shippingMethods) {
        ShippingMethods = shippingMethods;
    }


}
