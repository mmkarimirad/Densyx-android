package com.bs.dental;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.bs.dental.service.PreferenceService;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.inject.Inject;

import roboguice.RoboGuice;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Ashraful on 11/4/2015.
 */
public class MyApplication extends MultiDexApplication {
    @Inject
    PreferenceService preferenceService;


    static {
        RoboGuice.setUseAnnotationDatabases(false);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this); // install multidex
    }

    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        /*if(preferenceService.GetPreferenceBooleanValue(PreferenceService.DO_USE_NEW_URL))
        {
            Constants.BASE_URL=preferenceService.GetPreferenceValue
                    (PreferenceService.URL_PREFER_KEY);
        }*/

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/iransans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    private   String PROPERTY_ID ;

    //Logging TAG
    private static final String TAG = "MyApp";

    public static int GENERAL_TRACKER = 0;

    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }

}
