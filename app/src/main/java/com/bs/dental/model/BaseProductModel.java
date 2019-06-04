package com.bs.dental.model;

/**
 * Created by Ashraful on 11/10/2015.
 */
public class BaseProductModel {
    private DefaultPictureModel DefaultPictureModel;
    private long Id;

    public DefaultPictureModel getDefaultPictureModel() {
        return DefaultPictureModel;
    }

    public void setDefaultPictureModel(DefaultPictureModel defaultPictureModel) {
        DefaultPictureModel = defaultPictureModel;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    private String Name;
}
