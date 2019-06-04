package com.bs.dental.model;

/**
 * Created by Ashraful on 11/11/2015.
 */
public class ReviewModel {
    private long ProductId;

    public long getRatingSum() {
        return RatingSum;
    }

    public void setRatingSum(long ratingSum) {
        RatingSum = ratingSum;
    }

    public boolean isAllowCustomerReviews() {
        return AllowCustomerReviews;
    }

    public void setAllowCustomerReviews(boolean allowCustomerReviews) {
        AllowCustomerReviews = allowCustomerReviews;
    }

    public long getProductId() {
        return ProductId;
    }

    public void setProductId(long productId) {
        ProductId = productId;
    }

    public long getTotalReviews() {
        return TotalReviews;
    }

    public void setTotalReviews(long totalReviews) {
        TotalReviews = totalReviews;
    }

    private long RatingSum;
    private boolean AllowCustomerReviews;
    private long TotalReviews;
}
