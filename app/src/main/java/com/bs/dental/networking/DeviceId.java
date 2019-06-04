package com.bs.dental.networking;

import android.provider.Settings;

import com.bs.dental.MainActivity;

/**
 * Created by arif on 09-Dec-16.
 */

public class DeviceId {
    private static final String instance;

    static {
        if (MainActivity.self!=null){
            instance = Settings.Secure.getString(MainActivity.self.getContentResolver(), Settings.Secure.ANDROID_ID);
        }else {
            instance="";
        }
    }

    public static String get() {
        return instance;
    }
}
