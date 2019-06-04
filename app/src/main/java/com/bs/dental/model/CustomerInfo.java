package com.bs.dental.model;

import com.bs.dental.networking.BaseResponse;

import java.util.List;

/**
 * Created by bs-110 on 12/9/2015.
 */
public class CustomerInfo extends BaseResponse {
    private String FirstName;
    private String LastName;
    private int DateOfBirthDay;
    private int DateOfBirthMonth;
    private int DateOfBirthYear;
    private String Email;
    private String Company;
    private boolean Newsletter;
    private String Gender;
    private String Username;
    private String Phone;
    // TODO: 6/2/2019 --- mmkr : customer international ID --> customer_attribute_3
    private List<FormValue> formValue;

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

    public int getDateOfBirthDay() {
        return DateOfBirthDay;
    }

    public void setDateOfBirthDay(int dateOfBirthDay) {
        DateOfBirthDay = dateOfBirthDay;
    }

    public int getDateOfBirthMonth() {
        return DateOfBirthMonth;
    }

    public void setDateOfBirthMonth(int dateOfBirthMonth) {
        DateOfBirthMonth = dateOfBirthMonth;
    }

    public int getDateOfBirthYear() {
        return DateOfBirthYear;
    }

    public void setDateOfBirthYear(int dateOfBirthYear) {
        DateOfBirthYear = dateOfBirthYear;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public boolean isNewsletter() {
        return Newsletter;
    }

    public void setNewsletter(boolean newsletter) {
        Newsletter = newsletter;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public List<FormValue> getFormValue() {
        return formValue;
    }

    public void setFormValue(List<FormValue> formValue) {
        this.formValue = formValue;
    }

    @Override
    public String toString() {
        return "CustomerInfo{" +
                "FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", DateOfBirthDay=" + DateOfBirthDay +
                ", DateOfBirthMonth=" + DateOfBirthMonth +
                ", DateOfBirthYear=" + DateOfBirthYear +
                ", Email='" + Email + '\'' +
                ", Company='" + Company + '\'' +
                ", Newsletter=" + Newsletter +
                ", Gender='" + Gender + '\'' +
                ", Username='" + Username + '\'' +
                ", Phone='" + Phone + '\'' +
                ", formValue=" + formValue +
                '}';
    }
}
