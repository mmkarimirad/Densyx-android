package com.bs.dental.model;

import com.bs.dental.networking.BaseResponse;

/**
 * Created by bs-110 on 12/23/2015.
 */
public class IsGuestCheckoutResponse extends BaseResponse{
    boolean Data;

    public boolean isData() {
        return Data;
    }

    public void setData(boolean data) {
        Data = data;
    }
}
