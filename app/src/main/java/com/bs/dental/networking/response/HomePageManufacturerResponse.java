package com.bs.dental.networking.response;

import com.bs.dental.model.ManuFacturer;
import com.bs.dental.networking.BaseResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashraful on 11/9/2015.
 */
public class HomePageManufacturerResponse extends BaseResponse {
    public List<ManuFacturer> getData() {
        return Data;
    }

    public void setData(List<ManuFacturer> data) {
        Data = data;
    }

    private List<ManuFacturer> Data=new ArrayList<>();
}
