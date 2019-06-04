package com.bs.dental.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashraful on 3/23/2015.
 */
public class Category {
    private int ParentCategoryId;
    private int DisplayOrder;
    private String IconPath,ImagePath,ImageUrl;
    private int Id;
    private String Name;
    private String Description;
    private int ProductCount;
    private List<SubCategory> Children=new ArrayList<>();

    @SerializedName("PictureModel")
    @Expose
    private PictureModel3 pictureModel;


    public PictureModel3 getPictureModel() {
        return pictureModel;
    }

    public void setPictureModel(PictureModel3 pictureModel) {
        this.pictureModel = pictureModel;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public int getParentCategoryId() {
        return ParentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        ParentCategoryId = parentCategoryId;
    }

    public int getDisplayOrder() {
        return DisplayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        DisplayOrder = displayOrder;
    }

    public String getIconPath() {
        return IconPath;
    }

    public void setIconPath(String iconPath) {
        IconPath = iconPath;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getProductCount() {
        return ProductCount;
    }

    public void setProductCount(int productCount) {
        ProductCount = productCount;
    }

    public List<SubCategory> getChildren() {
        return Children;
    }

    public void setChildren(List<SubCategory> children) {
        Children = children;
    }
}
