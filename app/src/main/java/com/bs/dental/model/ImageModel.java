package com.bs.dental.model;

/**
 * Created by Ashraful on 11/9/2015.
 */
public class ImageModel  {

    private int IsProduct;
    private int ProdOrCatId;
    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    private String ImageUrl;

    public int getIsProduct() {
        return IsProduct;
    }

    public void setIsProduct(int isProduct) {
        IsProduct = isProduct;
    }

    public int getProdOrCatId() {
        return ProdOrCatId;
    }

    public void setProdOrCatId(int prodOrCatId) {
        ProdOrCatId = prodOrCatId;
    }
}
