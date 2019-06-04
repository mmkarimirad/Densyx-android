package com.bs.dental.ui.activity;

import android.content.Context;
import android.os.Bundle;

import com.bs.dental.service.PreferenceService;
import com.bs.dental.utils.ContextWrapper;
import com.bs.dental.utils.Language;
import com.google.inject.Inject;

import java.util.Locale;

import roboguice.activity.RoboActionBarActivity;


public class LanguageBaseActivity extends RoboActionBarActivity {

    @Inject
    private PreferenceService preferenceService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        // .. create or get your new Locale object here.
//        preferenceService.SetPreferenceValue(PreferenceService.CURRENT_LANGUAGE, Language.ITALIAN);
        Locale newLocale = new Locale(Language.ENGLISH);

        Context context = ContextWrapper.wrap(newBase, newLocale);
        super.attachBaseContext(context);
    }

}
