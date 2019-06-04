package com.bs.dental.model;

import java.util.List;

/**
 * Created by mmkr on 2/5/2019 --- for add product list specially for densyx doctors.
 */
public class ProductTurn {
    private int Id;
    private String Name;
    private String Price;
    private String Count;
    private String Description;

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

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
