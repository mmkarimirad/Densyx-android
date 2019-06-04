package com.bs.dental.model;

/**
 * Created by Ashraful on 11/27/2015.
 */
public class KeyValuePair {
    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public KeyValuePair(String key, String value)
    {
        this.Value=value;
        this.Key=key;
    }
    public KeyValuePair()
    {

    }

    private String Value;
    private String Key;
}
