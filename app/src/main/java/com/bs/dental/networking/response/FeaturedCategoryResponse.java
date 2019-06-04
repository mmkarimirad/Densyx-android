package com.bs.dental.networking.response;

import com.bs.dental.model.FeaturedCategory;
import com.bs.dental.networking.BaseResponse;

import java.util.List;

/**
 * Created by Ashraful on 12/11/2015.
 */
public class FeaturedCategoryResponse  extends BaseResponse{
    public List<FeaturedCategory> getData() {
        return Data;
    }

    public void setData(List<FeaturedCategory> data) {
        Data = data;
    }

    private List<FeaturedCategory>Data;
}