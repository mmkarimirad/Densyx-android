package com.bs.dental.model;

import com.bs.dental.networking.BaseResponse;

import java.util.List;

/**
 * Created by bs-110 on 1/7/2016.
 */
public class CategoryFeaturedProductAndSubcategoryResponse extends BaseResponse {
    String Name;
    private List<Category> SubCategories;
    private List<ProductModel> FeaturedProducts;



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<ProductModel> getFeaturedProducts() {
        return FeaturedProducts;
    }

    public void setFeaturedProducts(List<ProductModel> featuredProducts) {
        FeaturedProducts = featuredProducts;
    }

    public List<Category> getSubCategories() {
        return SubCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        SubCategories = subCategories;
    }
}
