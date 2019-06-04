package com.bs.dental.networking.postrequest;

/**
 * Created by Ashraful on 12/8/2015.
 */
public class ValuePost {

    private String value;
    public ValuePost()
    {

    }
    public ValuePost(String value)
    {
        this.value=value;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
