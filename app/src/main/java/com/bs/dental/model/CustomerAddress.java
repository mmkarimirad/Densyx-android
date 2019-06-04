package com.bs.dental.model;

/**
 * Created by bs-110 on 12/17/2015.
 */
public class CustomerAddress {
    private String Id;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Company;
    private String CountryName;
    private String StateProvinceName;
    private String City;
    private String Address1;
    private String Address2;
    private String ZipPostalCode;
    private String PhoneNumber;
    private String FaxNumber;
    private String CountryId;
    private String StateProvinceId;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String Country) {
        this.CountryName = Country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String Address1) {
        this.Address1 = Address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String Address2) {
        this.Address2 = Address2;
    }

    public String getZipPostalCode() {
        return ZipPostalCode;
    }

    public void setZipPostalCode(String ZipPostalCode) {
        this.ZipPostalCode = ZipPostalCode;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getFaxNumber() {
        return FaxNumber;
    }

    public void setFaxNumber(String FaxNumber) {
        this.FaxNumber = FaxNumber;
    }

    public String getCountryId() {
        return CountryId;
    }

    public void setCountryId(String countryId) {
        CountryId = countryId;
    }

    public String getStateProvinceId() {
        return StateProvinceId;
    }

    public void setStateProvinceId(String stateProvinceId) {
        StateProvinceId = stateProvinceId;
    }

    public String getStateProvinceName() {
        return StateProvinceName;
    }

    public void setStateProvinceName(String stateProvinceName) {
        StateProvinceName = stateProvinceName;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }
}
