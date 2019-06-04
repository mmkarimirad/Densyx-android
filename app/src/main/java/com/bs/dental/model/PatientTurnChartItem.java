package com.bs.dental.model;

public class PatientTurnChartItem {
    private int Count;
    private String DateTime;
    private String PersianDateTime;

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getPersianDateTime() {
        return PersianDateTime;
    }

    public void setPersianDateTime(String persianDateTime) {
        PersianDateTime = persianDateTime;
    }

    @Override
    public String toString() {
        return "PatientTurnChartItem{" +
                "Count=" + Count +
                ", DateTime='" + DateTime + '\'' +
                ", PersianDateTime='" + PersianDateTime + '\'' +
                '}';
    }
}
