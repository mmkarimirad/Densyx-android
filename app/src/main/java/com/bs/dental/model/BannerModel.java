package com.bs.dental.model;

/**
 * Created by Ashraful on 5/11/2016.
 */
public class BannerModel {

    private int PictureId;
    private String Text;
    private String Link;
    private boolean IsProduct;
    private String ProductOrCategoryId;

    public boolean isProduct() {
        return IsProduct;
    }

    public void setProduct(boolean product) {
        IsProduct = product;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public int getPictureId() {
        return PictureId;
    }

    public void setPictureId(int pictureId) {
        PictureId = pictureId;
    }

    public String getProductOrCategoryId() {
        return ProductOrCategoryId;
    }

    public void setProductOrCategoryId(String productOrCategoryId) {
        ProductOrCategoryId = productOrCategoryId;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }
}
