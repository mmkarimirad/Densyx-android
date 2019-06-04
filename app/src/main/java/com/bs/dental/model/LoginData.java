package com.bs.dental.model;

/**
 * Created by bs-110 on 12/9/2015.
 */
public class LoginData {
    private String email;
    private String password;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String Username;

    public LoginData(String email, String password) {
        this.email = email;
        this.Username=email;
        this.password = password;
    }
}
