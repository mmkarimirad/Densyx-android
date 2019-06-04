package com.bs.dental.model;

import com.bs.dental.networking.BaseResponse;

import java.util.List;

/**
 * Created by bs-110 on 12/18/2015.
 */
public class CustomerOrdersResponse extends BaseResponse {
    private List<CustomerOrder> Orders;

    public List<CustomerOrder> getOrders() {
        return Orders;
    }

    public void setOrders(List<CustomerOrder> orders) {
        Orders = orders;
    }
}
