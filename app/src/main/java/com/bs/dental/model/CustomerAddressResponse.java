package com.bs.dental.model;

import com.bs.dental.networking.BaseResponse;

import java.util.List;

/**
 * Created by bs-110 on 12/17/2015.
 */
public class CustomerAddressResponse extends BaseResponse {
    private List<CustomerAddress> ExistingAddresses;

    public List<CustomerAddress> getExistingAddresses() {
        return ExistingAddresses;
    }

    public void setExistingAddresses(List<CustomerAddress> ExistingAddresses) {
        this.ExistingAddresses = ExistingAddresses;
    }
}
