package com.bs.dental.networking;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bs156 on 09-Dec-16.
 */

public class NetworkUtil {
    private static String deviceId = "";
    private static String token = "";
    private static String nst = "";

    static String getDeviceId() {
        if (deviceId.isEmpty()) {
            deviceId = DeviceId.get();
        }
        return deviceId;
    }
    
    public static void setNst(String nst) {
        NetworkUtil.nst = nst;
    }
    
    static String getNst() {
        return nst;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        NetworkUtil.token = token;
    }

    public static Map<String, String> getHeaders() {
        Map<String, String> headerMap = new HashMap<>();
        if (deviceId.isEmpty()) {
            deviceId = DeviceId.get();
        }
        headerMap.put("DeviceId", deviceId);

        if (token != null && !token.isEmpty()) {
            headerMap.put("Token", token);
        }
        headerMap.put("NST", nst);
        return headerMap;
    }
}
