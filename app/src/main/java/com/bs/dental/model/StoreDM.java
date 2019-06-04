package com.bs.dental.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BS-182 on 7/27/2017.
 */

public class StoreDM {

    @SerializedName("Id")
    private long id;

    @SerializedName("Name")
    private String  name;

    @SerializedName("ProviderSystemName")
    private String  providerSystemName;

    @SerializedName("Address")
    private String  address;

    @SerializedName("City")
    private String  city;


    @SerializedName("CountryName")
    private String  countryName;

    @SerializedName("ZipPostalCode")
    private String  zipPostalCode;

    @SerializedName("PickupFee")
    private String  pickupFee;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProviderSystemName() {
        return providerSystemName;
    }

    public void setProviderSystemName(String providerSystemName) {
        this.providerSystemName = providerSystemName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getZipPostalCode() {
        return zipPostalCode;
    }

    public void setZipPostalCode(String zipPostalCode) {
        this.zipPostalCode = zipPostalCode;
    }

    public String getPickupFee() {
        return pickupFee;
    }

    public void setPickupFee(String pickupFee) {
        this.pickupFee = pickupFee;
    }
}
