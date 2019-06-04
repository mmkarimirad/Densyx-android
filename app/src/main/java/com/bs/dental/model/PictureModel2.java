package com.bs.dental.model;

/**
 * Created by employee on 4/18/2018.
 */

public class PictureModel2 extends ImageModel {
    private String ImageUrl;

    @Override
    public String getImageUrl() {
        return ImageUrl;
    }

    @Override
    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
