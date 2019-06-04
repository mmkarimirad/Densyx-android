package com.bs.dental.model;

/**
 * Created by Ashraful on 11/25/2015.
 */
public class AttributeControlValue {
    private String Name;
    private String ColorSquaresRgb;
    private String PriceAdjustment;
    private double PriceAdjustmentValue;
    private boolean IsPreSelected;
    private int Id;
    private PictureModel PictureModel;

    public String getColorSquaresRgb() {
        return ColorSquaresRgb;
    }

    public void setColorSquaresRgb(String colorSquaresRgb) {
        ColorSquaresRgb = colorSquaresRgb;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isPreSelected() {
        return IsPreSelected;
    }

    public void setIsPreSelected(boolean isPreSelected) {
        IsPreSelected = isPreSelected;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public com.bs.dental.model.PictureModel getPictureModel() {
        return PictureModel;
    }

    public void setPictureModel(com.bs.dental.model.PictureModel pictureModel) {
        PictureModel = pictureModel;
    }

    public String getPriceAdjustment() {
        return PriceAdjustment;
    }

    public void setPriceAdjustment(String priceAdjustment) {
        PriceAdjustment = priceAdjustment;
    }

    public double getPriceAdjustmentValue() {
        return PriceAdjustmentValue;
    }

    public void setPriceAdjustmentValue(double priceAdjustmentValue) {
        PriceAdjustmentValue = priceAdjustmentValue;
    }


}
