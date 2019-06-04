package com.bs.dental.model;

import com.bs.dental.networking.BaseResponse;

/**
 * Created by BS-182 on 7/19/2017.
 */

public class ForgetResponse extends BaseResponse {
    private String SuccessMessage;

    public String getSuccessMessage() {
        return SuccessMessage;
    }

    public void setSuccessMessage(String successMessage) {
        SuccessMessage = successMessage;
    }
}
