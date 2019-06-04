package com.bs.dental.networking;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bs.dental.BuildConfig;
import com.bs.dental.R;
import com.bs.dental.ui.fragment.Utility;
import com.pnikosis.materialishprogress.ProgressWheel;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bs156 on 23-Dec-16.
 */

public class CustomCB<T> implements Callback<T> {

    private ProgressWheel progressWheel;

    public CustomCB() {
        // no loading
    }

    public CustomCB(View layout) {
        this((RelativeLayout) layout);
    }

    public CustomCB(RelativeLayout layout) {
        RelativeLayout.LayoutParams params = new RelativeLayout
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        ProgressWheel progressWheel = getProgressWheel();
        layout.addView(progressWheel, params);
    }

    public CustomCB(CoordinatorLayout layout) {
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;

        ProgressWheel progressWheel = getProgressWheel();
        layout.addView(progressWheel, params);
    }

    public CustomCB(DrawerLayout layout) {
        DrawerLayout.LayoutParams params = new DrawerLayout
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ProgressWheel progressWheel = getProgressWheel();

        layout.addView(progressWheel, params);
    }

    private ProgressWheel getProgressWheel() {
        if (progressWheel == null) {
            progressWheel = (ProgressWheel) Utility.getActivity()
                    .getLayoutInflater().inflate(R.layout.materialish_progressbar, null);
        }
        progressWheel.spin();
        return progressWheel;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        manipulateBaseResponse(response);
        if (response.body() != null) {

           Log.d("RETROFIT", "CUSTOMCB : " + response.body());

            EventBus.getDefault().post(response.body());
        }
        dismissProgress();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        dismissProgress();
        if (BuildConfig.DEBUG) {
            t.printStackTrace();
        }
        //Toast.makeText(Utility.getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

        //================================ Internet Connection Error ====================================

        Toast.makeText(Utility.getActivity(), R.string.internet_connection_error, Toast.LENGTH_SHORT).show();

        //EventBus.getDefault().post(t.getMessage());
    }

    private void manipulateBaseResponse(Response<T> response) {
        try {
            BaseResponse baseResponse = (BaseResponse) response.body();
            if (baseResponse.getErrorList().length > 0) {
                Toast.makeText(Utility.getActivity(), baseResponse.getErrorsAsFormattedString(), Toast.LENGTH_LONG).show();
            }

        } catch (Exception ex) {

        }

    }

    private void dismissProgress() {
        if (progressWheel != null) {
            progressWheel.stopSpinning();
        }
    }
}
