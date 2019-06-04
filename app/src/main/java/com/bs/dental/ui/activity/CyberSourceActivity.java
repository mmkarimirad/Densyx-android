package com.bs.dental.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.bs.dental.Constants;
import com.bs.dental.R;
import com.bs.dental.networking.NetworkUtil;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Ashraful on 1/26/2016.
 */
@ContentView(R.layout.activity_cyber_source)
public class CyberSourceActivity extends RoboActionBarActivity {

    private String url= Constants.BASE_URL+"/checkout/OpcCompleteRedirectionPayment";
    private String toolbarTitle;
    @InjectView(R.id.wv_cyber_source)
    WebView cyberSourceWebView;
    private Toolbar toolbar;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWebViewRequiredInfo();

        //goToWebsite();

        toolbarTitle=getString(R.string.cyber_source_payment);
        setToolbar();
    }
    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(toolbarTitle);
        }

    }

    private void setWebViewRequiredInfo()
    {
        cyberSourceWebView.getSettings().setJavaScriptEnabled(true);
        cyberSourceWebView.getSettings().setDomStorageEnabled(true);
        cyberSourceWebView.getSettings().setBuiltInZoomControls(true);
        cyberSourceWebView.getSettings().setDisplayZoomControls(false);
        cyberSourceWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url, NetworkUtil.getHeaders());
                return false;
            }
        });
        cyberSourceWebView.loadUrl(url, NetworkUtil.getHeaders());
    }

    private void goToWebsite() {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request."
                    + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}
