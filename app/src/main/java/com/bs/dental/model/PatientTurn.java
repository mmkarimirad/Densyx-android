package com.bs.dental.model;

public class PatientTurn {
    private int Id;
    private String FirstName;
    private String LastName;
    private String MobileNumber;
    private String ServiceTypeTitle;
    private String TurnDateTime;
    private String PersianTurnDateTime;
    private String Description;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getServiceTypeTitle() {
        return ServiceTypeTitle;
    }

    public void setServiceTypeTitle(String serviceTypeTitle) {
        ServiceTypeTitle = serviceTypeTitle;
    }

    public String getTurnDateTime() {
        return TurnDateTime;
    }

    public void setTurnDateTime(String turnDateTime) {
        TurnDateTime = turnDateTime;
    }

    public String getPersianTurnDateTime() {
        return PersianTurnDateTime;
    }

    public void setPersianTurnDateTime(String persianTurnDateTime) {
        PersianTurnDateTime = persianTurnDateTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "PatientTurn{" +
                "Id=" + Id +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", MobileNumber='" + MobileNumber + '\'' +
                ", ServiceTypeTitle='" + ServiceTypeTitle + '\'' +
                ", TurnDateTime='" + TurnDateTime + '\'' +
                ", PersianTurnDateTime='" + PersianTurnDateTime + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
