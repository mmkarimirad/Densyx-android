package com.bs.dental.model;

import java.util.List;

/**
 * Created by Ashraful on 12/7/2015.
 */
public class BillingAddress {

    private String FirstName;
    private String LastName;

    public List<AvailableCountry> getAvailableCountries() {
        return AvailableCountries;
    }

    public void setAvailableCountries(List<AvailableCountry> availableCountries) {
        AvailableCountries = availableCountries;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public List<AvailableCountry> getAvailableStates() {
        return AvailableStates;
    }

    public void setAvailableStates(List<AvailableCountry> availableStates) {
        AvailableStates = availableStates;
    }

    private List<AvailableCountry>AvailableCountries;
    private List<AvailableCountry>AvailableStates;
    private long Id;
    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public boolean isCityEnabled() {
        return CityEnabled;
    }

    public void setCityEnabled(boolean cityEnabled) {
        CityEnabled = cityEnabled;
    }

    public boolean isCityRequired() {
        return CityRequired;
    }

    public void setCityRequired(boolean cityRequired) {
        CityRequired = cityRequired;
    }

    public boolean isCompanyEnabled() {
        return CompanyEnabled;
    }

    public void setCompanyEnabled(boolean companyEnabled) {
        CompanyEnabled = companyEnabled;
    }

    public boolean isCompanyRequired() {
        return CompanyRequired;
    }

    public void setCompanyRequired(boolean companyRequired) {
        CompanyRequired = companyRequired;
    }

    public boolean isCountryEnabled() {
        return CountryEnabled;
    }

    public void setCountryEnabled(boolean countryEnabled) {
        CountryEnabled = countryEnabled;
    }

    public String getCountryId() {
        return CountryId;
    }

    public void setCountryId(String countryId) {
        CountryId = countryId;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public boolean isFaxEnabled() {
        return FaxEnabled;
    }

    public void setFaxEnabled(boolean faxEnabled) {
        FaxEnabled = faxEnabled;
    }

    public String getFaxNumber() {
        return FaxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        FaxNumber = faxNumber;
    }

    public boolean isFaxRequired() {
        return FaxRequired;
    }

    public void setFaxRequired(boolean faxRequired) {
        FaxRequired = faxRequired;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public boolean isPhoneEnabled() {
        return PhoneEnabled;
    }

    public void setPhoneEnabled(boolean phoneEnabled) {
        PhoneEnabled = phoneEnabled;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public boolean isPhoneRequired() {
        return PhoneRequired;
    }

    public void setPhoneRequired(boolean phoneRequired) {
        PhoneRequired = phoneRequired;
    }

    public boolean isStateProvinceEnabled() {
        return StateProvinceEnabled;
    }

    public void setStateProvinceEnabled(boolean stateProvinceEnabled) {
        StateProvinceEnabled = stateProvinceEnabled;
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

    public boolean isStreetAddress2Enabled() {
        return StreetAddress2Enabled;
    }

    public void setStreetAddress2Enabled(boolean streetAddress2Enabled) {
        StreetAddress2Enabled = streetAddress2Enabled;
    }

    public boolean isStreetAddress2Required() {
        return StreetAddress2Required;
    }

    public void setStreetAddress2Required(boolean streetAddress2Required) {
        StreetAddress2Required = streetAddress2Required;
    }

    public boolean isStreetAddressEnabled() {
        return StreetAddressEnabled;
    }

    public void setStreetAddressEnabled(boolean streetAddressEnabled) {
        StreetAddressEnabled = streetAddressEnabled;
    }

    public boolean isStreetAddressRequired() {
        return StreetAddressRequired;
    }

    public void setStreetAddressRequired(boolean streetAddressRequired) {
        StreetAddressRequired = streetAddressRequired;
    }

    public String getZipPostalCode() {
        return ZipPostalCode;
    }

    public void setZipPostalCode(String zipPostalCode) {
        ZipPostalCode = zipPostalCode;
    }

    public boolean isZipPostalCodeEnabled() {
        return ZipPostalCodeEnabled;
    }

    public void setZipPostalCodeEnabled(boolean zipPostalCodeEnabled) {
        ZipPostalCodeEnabled = zipPostalCodeEnabled;
    }

    public boolean isZipPostalCodeRequired() {
        return ZipPostalCodeRequired;
    }

    public void setZipPostalCodeRequired(boolean zipPostalCodeRequired) {
        ZipPostalCodeRequired = zipPostalCodeRequired;
    }

    private String Email;
    private boolean CompanyEnabled;
    private boolean CompanyRequired;
    private String Company;
    private boolean CountryEnabled;
    private String CountryId;
    private String CountryName;
    private boolean StateProvinceEnabled;
    private String StateProvinceId;
    private String StateProvinceName;
    private boolean CityEnabled;
    private boolean CityRequired;
    private String City;
    private boolean StreetAddressEnabled;
    private boolean StreetAddressRequired;
    private String Address1;
    private boolean StreetAddress2Enabled;
    private boolean StreetAddress2Required;
    private String Address2;
    private boolean ZipPostalCodeEnabled;
    private boolean ZipPostalCodeRequired;
    private String ZipPostalCode;
    private boolean PhoneEnabled;
    private boolean PhoneRequired;
    private String PhoneNumber;
    private boolean FaxEnabled;
    private boolean FaxRequired;
    private String FaxNumber;
}
