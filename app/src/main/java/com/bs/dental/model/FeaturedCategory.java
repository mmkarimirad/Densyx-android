package com.bs.dental.model;

import java.util.List;

/**
 * Created by Ashraful on 12/11/2015.
 */
public class FeaturedCategory {
    private List<ProductModel> Product;
    private BaseProductModel Category;
    private List<BaseProductModel> SubCategory;

    public BaseProductModel getCategory() {
        return Category;
    }

    public void setCategory(BaseProductModel category) {
        Category = category;
    }

    public List<ProductModel> getProduct() {
        return Product;
    }

    public void setProduct(List<ProductModel> product) {
        Product = product;
    }

    public List<BaseProductModel> getSubCategory() {
        return SubCategory;
    }

    public void setSubCategory(List<BaseProductModel> subCategory) {
        SubCategory = subCategory;
    }
}
