package com.bs.dental.networking.response;

import com.bs.dental.model.BillingAddress;
import com.bs.dental.networking.BaseResponse;

import java.util.List;

/**
 * Created by Ashraful on 12/7/2015.
 */
public class BillingAddressResponse extends BaseResponse {

    public List<BillingAddress> getExistingAddresses() {
        return ExistingAddresses;
    }

    public void setExistingAddresses(List<BillingAddress> existingAddresses) {
        ExistingAddresses = existingAddresses;
    }

    private List<BillingAddress>ExistingAddresses;
      private BillingAddress NewAddress;

    public boolean isNewAddressPreselected() {
        return NewAddressPreselected;
    }

    public void setNewAddressPreselected(boolean newAddressPreselected) {
        NewAddressPreselected = newAddressPreselected;
    }

    public BillingAddress getNewAddress() {
        return NewAddress;
    }

    public void setNewAddress(BillingAddress newAddress) {
        NewAddress = newAddress;
    }

    private boolean NewAddressPreselected;

}
