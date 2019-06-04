package com.bs.dental.networking.postrequest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bs156 on 23-Jan-17.
 */

public class FacebookLogin {
    @SerializedName("ProviderUserId")
    private String userId;

    @SerializedName("AccessToken")
    private String accessToken;

    @SerializedName("Email")
    private String email;

    @SerializedName("Name")
    private String name;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
