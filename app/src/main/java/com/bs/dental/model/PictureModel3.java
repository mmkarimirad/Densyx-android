package com.bs.dental.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by employee on 4/18/2018.
 */

public class PictureModel3 {

    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("ThumbImageUrl")
    @Expose
    private Object thumbImageUrl;
    @SerializedName("FullSizeImageUrl")
    @Expose
    private Object fullSizeImageUrl;
    @SerializedName("Title")
    @Expose
    private Object title;
    @SerializedName("AlternateText")
    @Expose
    private Object alternateText;
        /*@SerializedName("CustomProperties")
        @Expose
        private CustomProperties customProperties;*/

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Object getThumbImageUrl() {
        return thumbImageUrl;
    }

    public void setThumbImageUrl(Object thumbImageUrl) {
        this.thumbImageUrl = thumbImageUrl;
    }

    public Object getFullSizeImageUrl() {
        return fullSizeImageUrl;
    }

    public void setFullSizeImageUrl(Object fullSizeImageUrl) {
        this.fullSizeImageUrl = fullSizeImageUrl;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public Object getAlternateText() {
        return alternateText;
    }

    public void setAlternateText(Object alternateText) {
        this.alternateText = alternateText;
    }

       /* public CustomProperties getCustomProperties() {
            return customProperties;
        }

        public void setCustomProperties(CustomProperties customProperties) {
            this.customProperties = customProperties;
        }*/

}
