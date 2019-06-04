package com.bs.dental.model;

/**
 * Created by bs-110 on 12/9/2015.
 */
public class LoginResponse {
    private String StatusCode;

    private String Phone;

    private String Email;

    private String CustomerId;

    private String Username;

    private String CountryId;

    private String FirstName;

    private String Token;

    private String StateProvinceId;

    private String StreetAddress2;

    private String LastName;

    private String City;

    private String StreetAddress;

    private String[] ErrorList;

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String StatusCode) {
        this.StatusCode = StatusCode;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String CustomerId) {
        this.CustomerId = CustomerId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getCountryId() {
        return CountryId;
    }

    public void setCountryId(String CountryId) {
        this.CountryId = CountryId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public String getStateProvinceId() {
        return StateProvinceId;
    }

    public void setStateProvinceId(String StateProvinceId) {
        this.StateProvinceId = StateProvinceId;
    }

    public String getStreetAddress2() {
        return StreetAddress2;
    }

    public void setStreetAddress2(String StreetAddress2) {
        this.StreetAddress2 = StreetAddress2;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getStreetAddress() {
        return StreetAddress;
    }

    public void setStreetAddress(String StreetAddress) {
        this.StreetAddress = StreetAddress;
    }

    public String[] getErrorList() {
        return ErrorList;
    }

    public void setErrorList(String[] ErrorList) {
        this.ErrorList = ErrorList;
    }

    @Override
    public String toString() {
        return "ClassPojo [StatusCode = " + StatusCode + ", Phone = " + Phone + ", Email = " + Email + ", CustomerId = " + CustomerId + ", Username = " + Username + ", CountryId = " + CountryId + ", FirstName = " + FirstName + ", Token = " + Token + ", StateProvinceId = " + StateProvinceId + ", StreetAddress2 = " + StreetAddress2 + ", LastName = " + LastName + ", City = " + City + ", StreetAddress = " + StreetAddress + ", ErrorList = " + ErrorList + "]";
    }
}
