package com.bs.dental.networking.response;

import com.bs.dental.model.AvailableState;
import com.bs.dental.networking.BaseResponse;

import java.util.List;

/**
 * Created by Ashraful on 12/8/2015.
 */
public class StateListResponse extends BaseResponse {
    public List<AvailableState> getData() {
        return Data;
    }

    public void setData(List<AvailableState> data) {
        Data = data;
    }

    List<AvailableState>Data;
}
