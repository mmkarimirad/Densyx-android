package com.bs.dental.model;

import java.util.List;

/**
 * Created by Ashraful on 11/10/2015.
 */
public class ExpandableListCategory {
    private Category parentCategory;

    public List<Category> getChildCategory() {
        return childCategory;
    }

    public void setChildCategory(List<Category> childCategory) {
        this.childCategory = childCategory;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    List<Category>childCategory;
}
