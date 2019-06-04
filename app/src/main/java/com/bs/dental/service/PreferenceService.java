package com.bs.dental.service;

import android.content.SharedPreferences;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Created by Ashraful on 11/25/2015.
 */
@Singleton
public class PreferenceService {
    public static final String FIRST_RUN      = "firstRun";
    public static String SHARED_PREF_KEY      ="PREFER_KEY";
    public static String loggedInText         ="isLoggedIn";
    public static String LOGGED_PREFER_KEY    ="isLoggedIn";
    public static String LOGGED_PREFER_KEY_TURN    ="isLoggedInTurn";
    public static String TOKEN_KEY            ="token";
    public static String URL_PREFER_KEY       ="URL_CHANGE_KEY";
    public static String DO_USE_NEW_URL       ="URL_CHANGE_KEY_BOOLEAN";
    public static String APP_VERSION_CODE_KEY ="VERSION_CODE";
    public static String SENT_TOKEN_TO_SERVER ="SENT_TOKEN_TO_SERVER_KEY";
    public static String REGISTRATION_COMPLETE ="REGISTRATION_COMPLETE_KEY";
    public static String taxShow="taxShow";
    public static String discuntShow="discuntShow";
    public static String CURRENT_LANGUAGE="current_language";
    public static String CURRENT_LANGUAGE_ID="current_language_id";
    public static String NST = "nst";

    // TODO: 5/8/2019 --- mmkr : turn api for densyx features
    public static String COOKIE_TURN ="Set-Cookie";


    @Inject
    private SharedPreferences preferences;

    public String GetPreferenceValue(String key)
    {

        return preferences.getString(key, null);
    }
    public int GetPreferenceIntValue(String key)
    {

        return preferences.getInt(key, -1);
    }

    public Boolean GetPreferenceBooleanValue(String key)
    {
        return preferences.getBoolean(key, false);
    }

    public void SetPreferenceValue(String key, String value)
    {
        preferences.edit().putString(key, value).commit();
    }

    public void SetPreferenceValue(String key, Boolean value)
    {
        preferences.edit().putBoolean(key, value).commit();
    }

    public void SetPreferenceValue(String key, int value)
    {
        preferences.edit().putInt(key, value).commit();
    }


}
