package com.bs.dental.model;

/**
 * Created by bs-110 on 12/15/2015.
 */
public class CustomerRegistrationInfo extends CustomerInfo {
    private String password;
    private String confirmPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
