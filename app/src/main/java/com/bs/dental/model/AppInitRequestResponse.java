package com.bs.dental.model;

/**
 * Created by Ashraful on 3/2/2016.
 */
public class AppInitRequestResponse  {
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
