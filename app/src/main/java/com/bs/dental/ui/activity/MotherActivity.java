package com.bs.dental.ui.activity;

import android.support.v7.app.ActionBarDrawerToggle;

import com.bs.dental.service.PreferenceService;
import com.google.inject.Inject;

import de.greenrobot.event.EventBus;
import roboguice.activity.RoboActionBarActivity;

/**
 * Created by Ashraful on 2/10/2016.
 */
public class MotherActivity extends RoboActionBarActivity {
    public ActionBarDrawerToggle mDrawerToggle;
    @Inject
    public PreferenceService preferenceService;


    @Override
    protected void onStart() {
        super.onStart();
        try {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        } catch (Exception ex) {

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}