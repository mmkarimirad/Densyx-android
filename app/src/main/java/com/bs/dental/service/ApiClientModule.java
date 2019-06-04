package com.bs.dental.service;

import com.google.inject.Binder;
import com.google.inject.Module;

import roboguice.inject.SharedPreferencesName;

/**
 * Created by Ashraful on 11/25/2015.
 */
public class ApiClientModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bindConstant().annotatedWith(SharedPreferencesName.class).to(PreferenceService.SHARED_PREF_KEY);
    }
}
