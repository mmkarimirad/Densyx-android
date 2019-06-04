package com.bs.dental.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bs.dental.R;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;


@ContentView(R.layout.activity_gallery)
public class GalleryActivity extends RoboActionBarActivity {

    // TODO: 4/29/2019 mmkr chaneged to https
    private String url_news= "http://api.densyx.com/news";
    private String url_weblog= "http://api.densyx.com/blog";
    private String url;
    private int categoryId;

    @InjectView(R.id.wv_gallery)
    WebView wv_gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getCategoryId();

        setWebViewRequiredInfo();
    }

    private void getCategoryId() {
        categoryId = getIntent().getIntExtra("categoryId" , 1000000);

        if (categoryId == 1000000)
            url = url_news;
        else if (categoryId == 1000001)
            url = url_weblog;
    }


    private void setWebViewRequiredInfo()
    {
        wv_gallery.getSettings().setJavaScriptEnabled(true);
        wv_gallery.getSettings().setDomStorageEnabled(true);
        wv_gallery.getSettings().setBuiltInZoomControls(true);
        wv_gallery.getSettings().setDisplayZoomControls(false);
        wv_gallery.setInitialScale(150);

        wv_gallery.setWebViewClient(new WebViewClient());

        /*wv_gallery.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url, NetworkUtil.getHeaders());
                return false;
            }
        });
        wv_gallery.loadUrl(url, NetworkUtil.getHeaders());*/


        /*wv_gallery.setWebViewClient(new WebViewClient() {
            ProgressWheel progressDialog = new ProgressWheel(getApplicationContext());

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                //progressDialog = (ProgressWheel) findViewById(R.id.progress_wheel);
                progressDialog.setVisibility(View.VISIBLE);
                progressDialog.spin();
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                //progressDialog.spin();
                *//*if (progressDialog.isSpinning()) {
                    progressDialog.stopSpinning();
                }*//*
            }
        });*/


        wv_gallery.loadUrl(url);
    }


}
