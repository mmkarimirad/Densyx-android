package com.bs.dental.networking.response;

import com.bs.dental.networking.BaseResponse;

/**
 * Created by Ashraful on 12/11/2015.
 */
public class PaymentMethodSaveResponse extends BaseResponse {
    public boolean isData() {
        return Data;
    }

    public void setData(boolean data) {
        Data = data;
    }

    private boolean Data;
}