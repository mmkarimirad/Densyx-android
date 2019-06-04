package com.bs.dental.model;

import com.bs.dental.networking.BaseResponse;

/**
 * Created by Ashraful on 5/11/2016.
 */
public class AppThemeResponse extends BaseResponse {
    public AppTheme getData() {
        return Data;
    }

    public void setData(AppTheme data) {
        Data = data;
    }

    private AppTheme Data;

}
