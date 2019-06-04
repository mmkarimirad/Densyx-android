package com.bs.dental.model;

/**
 * Created by bs-110 on 12/9/2015.
 */
public class LoginDataTurn {
    private String UserName;
    private String Password;
    private String RememberMe;
    private String ReturnUrl;

    public LoginDataTurn(String userName, String password, String rememberMe, String returnUrl) {
        UserName = userName;
        Password = password;
        RememberMe = rememberMe;
        ReturnUrl = returnUrl;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRememberMe() {
        return RememberMe;
    }

    public void setRememberMe(String rememberMe) {
        RememberMe = rememberMe;
    }

    public String getReturnUrl() {
        return ReturnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        ReturnUrl = returnUrl;
    }

    @Override
    public String toString() {
        return "LoginDataTurn{" +
                "UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                ", RememberMe='" + RememberMe + '\'' +
                ", ReturnUrl='" + ReturnUrl + '\'' +
                '}';
    }
}
