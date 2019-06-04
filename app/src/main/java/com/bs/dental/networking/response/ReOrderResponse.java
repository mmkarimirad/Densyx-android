package com.bs.dental.networking.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by BS168 on 3/3/2017.
 */

public class ReOrderResponse {

    @SerializedName("Data")
    private Boolean data;
    @SerializedName("SuccessMessage")
    private String SuccessMessage;
    @SerializedName("StatusCode")
    private  int statusCode;
    @SerializedName("ErrorList")
    private List<String > errorList;

    public Boolean getData() {
        return data;
    }

    public void setData(Boolean data) {
        this.data = data;
    }

    public String getSuccessMessage() {
        return SuccessMessage;
    }

    public void setSuccessMessage(String successMessage) {
        SuccessMessage = successMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }
}
