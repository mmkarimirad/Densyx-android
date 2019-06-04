package com.bs.dental.utils;

/**
 * Created by bs-110 on 12/18/2015.
 */
public class AppUtils {
    public static String getNullSafeString(String string) {
        return string == null ? "" : string;
    }
}
