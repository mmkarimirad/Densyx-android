package com.bs.dental.networking.response;

import com.bs.dental.model.ImageModel;
import com.bs.dental.networking.BaseResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashraful on 11/9/2015.
 */
public class HomePageBannerResponse extends BaseResponse {
    public List<ImageModel> getData() {
        return Data;
    }

    public void setData(List<ImageModel> data) {
        Data = data;
    }

    private List<ImageModel>Data=new ArrayList<>();


}
