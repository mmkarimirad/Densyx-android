package com.bs.dental.model;

import com.bs.dental.networking.BaseResponse;

/**
 * Created by bs-110 on 12/9/2015.
 */
public class DoctorCustomer extends BaseResponse {
    private String FirstName;
    private String LastName;
    private String Username;
    private String Email;
    private String PhoneNumber;
    private String Password;
    private int Id;
    private String CreatedOnUtc;

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

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCreatedOnUtc() {
        return CreatedOnUtc;
    }

    public void setCreatedOnUtc(String createdOnUtc) {
        CreatedOnUtc = createdOnUtc;
    }

    @Override
    public String toString() {
        return "DoctorCustomer{" +
                "FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Username=" + Username +
                ", Email=" + Email +
                ", PhoneNumber=" + PhoneNumber +
                ", Password='" + Password + '\'' +
                ", Id='" + Id + '\'' +
                ", CreatedOnUtc=" + CreatedOnUtc +
                '}';
    }


}
