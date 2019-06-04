package com.bs.dental.ui.fragment;

import android.app.Activity;

import com.bs.dental.MainActivity;

/**
 * Created by Ashraful on 11/6/2015.
 */
public class Utility {
    public static int categoryId;
    public static Activity activity;
    public static String paypalKey="payPalKey";
    public static String clientId="AUvTxpQfYfgB2mV_IlFwkuK7j6lM9eBfKdMVkCoTYWvO4I-ctwYskPx5FvpTJh-pn3bDGPmfElgWJ1am";
    public static int cartCounter;
    public static final String orderIdKey = "orderIdKey";

    public static void drawerOpenCloseOperation() {

    }

    public static Activity getActivity() {
        return activity;
    }
    public static void setActivity(Activity activity)
    {
        Utility.activity=activity;
    }
    public static void  closeLeftDrawer()
    {

        MainActivity.self.closeDrawer();
    }
    public static void setCartCounter(int counter)
    {
        cartCounter=counter;
        MainActivity.self.updateHotCount(counter);
    }

}
