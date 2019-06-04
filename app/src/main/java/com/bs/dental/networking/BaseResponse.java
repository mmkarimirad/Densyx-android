package com.bs.dental.networking;

/**
 * Created by Ashraful on 11/6/2015.
 */
public class BaseResponse {
    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    private int StatusCode;

    public String[] getErrorList() {
        return ErrorList;
    }

    public void setErrorList(String[] errorList) {
        ErrorList = errorList;
    }

    private String[] ErrorList;

    public String getErrorsAsFormattedString(){
        String errors = "";
        if(getErrorList().length > 0){
            for(int i=0; i< getErrorList().length; i++ ){
                errors += "  "+(i+1) + ": " + getErrorList()[i] + " \n";
            }
        }
        return errors;
    }
}
