package com.bs.dental.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bs.dental.MainActivity;
import com.bs.dental.R;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.activity.BaseActivity;
import com.google.inject.Inject;
import com.pnikosis.materialishprogress.ProgressWheel;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.NoSubscriberEvent;
import roboguice.RoboGuice;
import roboguice.fragment.RoboFragment;

/**
 * Created by Ashraful on 11/5/2015.
 */
public class BaseFragment extends RoboFragment {
   /* @javax.annotation.Nullable
    @InjectView(R.id.app_toolbar)
   */

    Toast mToast;
    ProgressWheel progress;

    private Toolbar toolbar2;

    @Inject
    PreferenceService preferenceService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).setLocale(false);
        EventBus.getDefault().register(this);
        setHasOptionsMenu(true);
        RoboGuice.getInjector(getActivity()).injectMembersWithoutViews(this);
    }



    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        checkEventBusRegistration();

        super.onResume();
        //  setToolbar();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    protected LayoutInflater getLayoutInflater() {
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater;
    }

    public void checkEventBusRegistration() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public void onEvent(NoSubscriberEvent noSubscriberEvent) {

    }

    public void onEvent(ClassCastException noSubscriberEvent) {

    }

    public void setToolbar() {
        try {
            if (toolbar2 != null)
                MainActivity.self.setToolbar(toolbar2);
        } catch (Exception Ex) {

        }
    }

    public void goMenuItemFragment(android.support.v4.app.Fragment fragment) {
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
    }

    protected void gotoNewFragment(android.support.v4.app.Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();

    }

    public void showSnack(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }


    public void showToast(String msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG);
        mToast.show();
    }

    protected void showProgress(RelativeLayout layout) {
        RelativeLayout.LayoutParams params = new RelativeLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        progress = (ProgressWheel) getLayoutInflater().inflate(R.layout.materialish_progressbar, null);
        layout.addView(progress, params);
        progress.spin();
    }

    public boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
