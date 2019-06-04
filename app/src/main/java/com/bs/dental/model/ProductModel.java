package com.bs.dental.model;

/**
 * Created by Ashraful on 11/9/2015.
 */
public class ProductModel extends BaseProductModel {

    private String ShortDescription;
    private com.bs.dental.model.ProductPrice ProductPrice;
    private ReviewModel ReviewOverviewModel;


    public String getShortDescription() {
        return ShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public com.bs.dental.model.ProductPrice getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(com.bs.dental.model.ProductPrice productPrice) {
        ProductPrice = productPrice;
    }

    public ReviewModel getReviewOverviewModel() {
        return ReviewOverviewModel;
    }

    public void setReviewOverviewModel(ReviewModel reviewOverviewModel) {
        ReviewOverviewModel = reviewOverviewModel;
    }

}
