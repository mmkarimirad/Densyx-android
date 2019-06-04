package com.bs.dental.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.ImageView;

import com.bs.dental.MainActivity;
import com.bs.dental.R;
import com.bs.dental.model.AppThemeResponse;
import com.bs.dental.networking.NetworkUtil;
import com.bs.dental.service.PreferenceService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.inject.Inject;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * Created by Ashraful on 2/17/2016.
 */
public class SplashScreenActivity extends RoboActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 5000;
    @InjectView(R.id.iv_background)
    private ImageView backgroundImageview;

    @Inject
    private PreferenceService preferenceService;

    private static String CONTAINER_ID = "GTM-WPHVH7";
    private static final String TAG = SplashScreenActivity.class.getCanonicalName();
    BroadcastReceiver mRegistrationBroadcastReceiver;

    private FirebaseAnalytics mFirebaseAnalytics;

    // TODO: 4/26/2019 : mmkr : Finally most add NST keys to Constants.java settings
    private final String NST_KEY = "bm9wU3RhdGlvblRva2Vu";
    private final String NST_SECRET = "bm9wS2V5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        setContentView(R.layout.activity_splash_screen);
        initializeData();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    }

    private void initializeData() {
        Picasso.with(this).load(R.drawable.splash).into(backgroundImageview);
    
        String compactJws = null;
        try {
            compactJws = Jwts.builder()
                    .claim("NST_KEY", NST_KEY)
                    .signWith(SignatureAlgorithm.HS512, NST_SECRET.getBytes("UTF-8")).compact();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        preferenceService.SetPreferenceValue(PreferenceService.NST, compactJws);
        NetworkUtil.setNst(compactJws);
        
        startThread();
    }

    private void startThread() {
        final Handler handler = new Handler();
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                handler.postDelayed(new Runnable() {
                    public void run() {
                        startMainActivity();
                    }
                }, 3000);
            }
        }, 0);
    }


    private void startMainActivity() {
        Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
        if (getIntent().getExtras() != null) {
            i.putExtras(getIntent().getExtras());
        }
        startActivity(i);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(PreferenceService.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
    public void onEvent(AppThemeResponse appThemeResponse)
    {

    }


}
