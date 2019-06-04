package com.bs.dental.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bs.dental.Constants;
import com.bs.dental.MainActivity;
import com.bs.dental.R;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.NetworkUtil;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.response.CategoryResponse;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.views.FormViews;
import com.google.inject.Inject;

import de.greenrobot.event.EventBus;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Ashraful on 2/9/2016.
 */
@ContentView(R.layout.activity_base_url)
public class BaseUrlChangeActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.btn_test_url)
    Button testUrlBtn;

    @InjectView(R.id.btn_main_url)
    Button mainUrlBtn;

     @InjectView(R.id.et_new_base_url)
     EditText newBaseUrlEditText;

    @InjectView(R.id.app_toolbar)
    Toolbar toolbar;
    public DrawerLayout drawerLayout;

    @Inject
    PreferenceService preferenceService;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buttonClickListener();
        textChangeListener();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.change_base_url);
        }
    }

    private void buttonClickListener() {
        testUrlBtn.setOnClickListener(this);
        mainUrlBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int resourceId = v.getId();

        if(resourceId == testUrlBtn.getId()) {
            validateForm();
        } else if(resourceId == mainUrlBtn.getId()) {
             keepOldUrl();
        }
    }

    private void keepOldUrl() {
        Constants.BASE_URL = Constants.DEFAULT_URL;
        preferenceService.SetPreferenceValue(PreferenceService.DO_USE_NEW_URL, false);
        forceRunApp();
    }

    private void validateForm() {
        if (FormViews.getTexBoxFieldValue(newBaseUrlEditText).length() > 7) {
            TestApiCall();
        } else {
            newBaseUrlEditText.setError(getString(R.string.url_is_require));
        }
    }

    private void TestApiCall() {
        String url = FormViews.getTexBoxFieldValue(newBaseUrlEditText) + "/api/";

         RelativeLayout viewGroup = (RelativeLayout)
                 ((ViewGroup)getActivityContentView()).getChildAt(0);
        RetroClient.getApi().getCategory().enqueue(new CustomCB<CategoryResponse>(viewGroup));
    }

    public void changeBaseUrl() {
        //comment
//        String url = FormViews.getTexBoxFieldValue(newBaseUrlEditText)+"/api/";
        // TODO: 4/29/2019 mmkr chaneged to https
        //*** mmkr changed from: String url = "http://api.densyx.com/api/";
        String url = Constants.BASE_URL;
        //String url = "http://netbama.com/api/";
        //String url = "http://area1.kokonad.com/api/";

//        Constants.BASE_URL = url;
//        preferenceService.SetPreferenceValue(PreferenceService.URL_PREFER_KEY, url);
//        preferenceService.SetPreferenceValue(PreferenceService.DO_USE_NEW_URL, true);

    }

    public  void forceRunApp( ){
        NetworkUtil.setToken("");
        preferenceService.SetPreferenceValue(PreferenceService.LOGGED_PREFER_KEY, false);
        EventBus.getDefault().unregister(this);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }


    public void onEvent(CategoryResponse categoryResponse) {
        changeBaseUrl();
        forceRunApp();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void textChangeListener()
    {
        newBaseUrlEditText.setText("http://");
        Selection.setSelection(newBaseUrlEditText.getText(), newBaseUrlEditText.getText().length());


        newBaseUrlEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().startsWith("http://")){
                    newBaseUrlEditText.setText("http://");
                    Selection.setSelection(newBaseUrlEditText.getText(), newBaseUrlEditText.getText().length());

                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}
