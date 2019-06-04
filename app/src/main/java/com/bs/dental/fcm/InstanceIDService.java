package com.bs.dental.fcm;

import android.util.Log;

import com.bs.dental.model.AppInitRequestResponse;
import com.bs.dental.model.AppStartRequest;
import com.bs.dental.model.AppThemeResponse;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.service.PreferenceService;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.inject.Inject;

import de.greenrobot.event.EventBus;

/**
 * Created by Arif Islam on 12-Sep-17.
 * nopStation | Brain Station-23
 * http://www.nop-station.com/
 */

public class InstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FCM_ID";
    
    @Inject
    PreferenceService  preferenceService;
    
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
    
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }
    
    private void sendRegistrationToServer(String token) {
        AppStartRequest appStartRequest=new AppStartRequest();
        appStartRequest.setDeviceTypeId(10);
        appStartRequest.setSubscriptionId(token);
        EventBus.getDefault().register(this);
    
        RetroClient.getApi().initApp(appStartRequest).enqueue(new CustomCB<AppThemeResponse>());
    }
    
    public void onEvent(AppInitRequestResponse appInitRequestResponse) {
        preferenceService.SetPreferenceValue(PreferenceService.SENT_TOKEN_TO_SERVER, true);
        Log.i(TAG,"Registered" );
    }
}
