package com.bs.dental.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashraful on 11/30/2015.
 */
public class CartProduct {
    private Object Sku;
    private int ProductId;
    private String ProductName;
    private String ProductSeName;
    private String UnitPrice;
    private String SubTotal;
    private Object Discount;
    private int Quantity;
    private List<Object> AllowedQuantities = new ArrayList<Object>();
    private String AttributeInfo;
    private boolean AllowItemEditing;
    private List<Object> Warnings = new ArrayList<>();
    private int Id;

    public PictureModel getPicture() {
        return Picture;
    }

    public void setPicture(PictureModel picture) {
        Picture = picture;
    }

    private PictureModel Picture;

    /**
     *
     * @return
     * The Sku
     */
    public Object getSku() {
        return Sku;
    }

    /**
     *
     * @param Sku
     * The Sku
     */
    public void setSku(Object Sku) {
        this.Sku = Sku;
    }

    /**
     *
     * @return
     * The ProductId
     */
    public int getProductId() {
        return ProductId;
    }

    /**
     *
     * @param ProductId
     * The ProductId
     */
    public void setProductId(int ProductId) {
        this.ProductId = ProductId;
    }

    /**
     *
     * @return
     * The ProductName
     */
    public String getProductName() {
        return ProductName;
    }

    /**
     *
     * @param ProductName
     * The ProductName
     */
    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    /**
     *
     * @return
     * The ProductSeName
     */
    public String getProductSeName() {
        return ProductSeName;
    }

    /**
     *
     * @param ProductSeName
     * The ProductSeName
     */
    public void setProductSeName(String ProductSeName) {
        this.ProductSeName = ProductSeName;
    }

    /**
     *
     * @return
     * The UnitPrice
     */
    public String getUnitPrice() {
        return UnitPrice;
    }

    /**
     *
     * @param UnitPrice
     * The UnitPrice
     */
    public void setUnitPrice(String UnitPrice) {
        this.UnitPrice = UnitPrice;
    }

    /**
     *
     * @return
     * The SubTotal
     */
    public String getSubTotal() {
        return SubTotal;
    }

    /**
     *
     * @param SubTotal
     * The SubTotal
     */
    public void setSubTotal(String SubTotal) {
        this.SubTotal = SubTotal;
    }

    /**
     *
     * @return
     * The Discount
     */
    public Object getDiscount() {
        return Discount;
    }

    /**
     *
     * @param Discount
     * The Discount
     */
    public void setDiscount(Object Discount) {
        this.Discount = Discount;
    }

    /**
     *
     * @return
     * The Quantity
     */
    public int getQuantity() {
        return Quantity;
    }

    /**
     *
     * @param Quantity
     * The Quantity
     */
    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    /**
     *
     * @return
     * The AllowedQuantities
     */
    public List<Object> getAllowedQuantities() {
        return AllowedQuantities;
    }

    /**
     *
     * @param AllowedQuantities
     * The AllowedQuantities
     */
    public void setAllowedQuantities(List<Object> AllowedQuantities) {
        this.AllowedQuantities = AllowedQuantities;
    }

    /**
     *
     * @return
     * The AttributeInfo
     */
    public String getAttributeInfo() {
        return AttributeInfo;
    }

    /**
     *
     * @param AttributeInfo
     * The AttributeInfo
     */
    public void setAttributeInfo(String AttributeInfo) {
        this.AttributeInfo = AttributeInfo;
    }

    /**
     *
     * @return
     * The AllowItemEditing
     */
    public boolean isAllowItemEditing() {
        return AllowItemEditing;
    }

    /**
     *
     * @param AllowItemEditing
     * The AllowItemEditing
     */
    public void setAllowItemEditing(boolean AllowItemEditing) {
        this.AllowItemEditing = AllowItemEditing;
    }

    /**
     *
     * @return
     * The Warnings
     */
    public List<Object> getWarnings() {
        return Warnings;
    }

    /**
     *
     * @param Warnings
     * The Warnings
     */
    public void setWarnings(List<Object> Warnings) {
        this.Warnings = Warnings;
    }

    /**
     *
     * @return
     * The Id
     */
    public int getId() {
        return Id;
    }

    /**
     *
     * @param Id
     * The Id
     */
    public void setId(int Id) {
        this.Id = Id;
    }
}
