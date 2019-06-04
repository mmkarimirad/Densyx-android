package com.bs.dental.model;

/**
 * Created by Ashraful on 3/2/2016.
 */
public class AppStartRequest  {

           private int DeviceTypeId;
           private String SubscriptionId;
           public String EmailAddress;

    public int getDeviceTypeId() {
        return DeviceTypeId;
    }

    public void setDeviceTypeId(int deviceTypeId) {
        DeviceTypeId = deviceTypeId;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getSubscriptionId() {
        return SubscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        SubscriptionId = subscriptionId;
    }
}
