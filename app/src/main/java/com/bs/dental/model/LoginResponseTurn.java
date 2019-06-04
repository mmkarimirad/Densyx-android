package com.bs.dental.model;

/**
 * Created by bs-110 on 12/9/2015.
 */
public class LoginResponseTurn {
    private int ResultCode ;
    private String Message;
    private String RedirectTo;

    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int resultCode) {
        ResultCode = resultCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getRedirectTo() {
        return RedirectTo;
    }

    public void setRedirectTo(String redirectTo) {
        RedirectTo = redirectTo;
    }

    @Override
    public String toString() {
        return "LoginResponseTurn{" +
                "ResultCode=" + ResultCode +
                ", Message='" + Message + '\'' +
                ", RedirectTo='" + RedirectTo + '\'' +
                '}';
    }
}
