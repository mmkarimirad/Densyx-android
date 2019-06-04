package com.bs.dental.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ashraful on 11/20/2015.
 */
public class ProductDetail extends ProductModel {

    List<PictureModel> PictureModels;
    String FullDescription;
    List<ProductDetail>AssociatedProducts;

    @SerializedName("ProductSpecifications")
    List<ProductSpecification> productSpecifications;

    @SerializedName("Quantity")
    private Quantity quantity=new Quantity();

    public List<ProductAttribute> getProductAttributes() {
        return ProductAttributes;
    }

    public void setProductAttributes(List<ProductAttribute> productAttributes) {
        ProductAttributes = productAttributes;
    }

    List<ProductAttribute> ProductAttributes;

    public String getFullDescription() {
        return FullDescription;
    }

    public void setFullDescription(String fullDescription) {
        FullDescription = fullDescription;
    }

    public List<PictureModel> getPictureModels() {
        return PictureModels;
    }

    public void setPictureModels(List<PictureModel> pictureModels) {
        PictureModels = pictureModels;
    }

    public List<ProductDetail> getAssociatedProducts() {
        return AssociatedProducts;
    }

    public void setAssociatedProducts(List<ProductDetail> associatedProducts) {
        AssociatedProducts = associatedProducts;
    }

    public List<ProductSpecification> getProductSpecifications() {
        return productSpecifications;
    }

    public void setProductSpecifications(List<ProductSpecification> productSpecifications) {
        this.productSpecifications = productSpecifications;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }
}
