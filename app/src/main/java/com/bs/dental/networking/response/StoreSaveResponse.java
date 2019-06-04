package com.bs.dental.networking.response;

import com.bs.dental.networking.BaseResponse;

/**
 * Created by BS-182 on 7/27/2017.
 */

public class StoreSaveResponse extends BaseResponse {

    private boolean Data;

    public boolean isData() {
        return Data;
    }

    public void setData(boolean data) {
        Data = data;
    }
}
