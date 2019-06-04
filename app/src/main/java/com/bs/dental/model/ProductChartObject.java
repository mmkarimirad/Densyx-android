package com.bs.dental.model;

public class ProductChartObject {
    private String Name;
    private String Count;


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    @Override
    public String toString() {
        return "ProductChartObject{" +
                "Name='" + Name + '\'' +
                ", Count='" + Count + '\'' +
                '}';
    }
}
