package com.bs.dental;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.bs.dental.fcm.MessagingService;
import com.bs.dental.model.ProductModel;
import com.bs.dental.model.ProductService;
import com.bs.dental.networking.DeviceId;
import com.bs.dental.networking.NetworkUtil;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.activity.BaseActivity;
import com.bs.dental.ui.fragment.HomePageFragment;
import com.bs.dental.ui.fragment.PatientTurnFragment;
import com.bs.dental.ui.fragment.ProductDetailFragment;
import com.bs.dental.ui.fragment.ProductTurnFragment;
import com.bs.dental.ui.fragment.ProductListFragmentFor3_8;
import com.bs.dental.ui.fragment.ReportFragment;
import com.bs.dental.utils.Language;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.inject.Inject;

import java.lang.reflect.Method;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.drawer_layout)
    public DrawerLayout drawerLayout;
    public static MainActivity self;

    Toolbar toolbar;
    FloatingActionButton fab1, fab2, fab3;
    public FloatingActionMenu fam;
    private boolean doubleBackToExitPressedOnce = false;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    public ImageView iv_toolbar;

    @Inject
    PreferenceService preferenceService;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*NetworkUtilities.context = this;*/
        self = this;
        setToolbar();
        getBaseUrl();
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.container, new HomePageFragment()).commit();

        setUp();

        if (!preferenceService.GetPreferenceBooleanValue(PreferenceService.FIRST_RUN)) {
            FirebaseMessaging.getInstance().subscribeToTopic("all");
            FirebaseMessaging.getInstance().subscribeToTopic("android");
            preferenceService.SetPreferenceValue(PreferenceService.FIRST_RUN, true);
        }

        fam = (FloatingActionMenu) findViewById(R.id.fam);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fam.close(true);
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.container, new PatientTurnFragment()).commit();

                /*Snackbar.make(view, "Calendar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fam.close(true);
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.container, new ReportFragment()).commit();

                /*Snackbar.make(view, "Reports", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fam.close(true);
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.container, new ProductTurnFragment()).commit();

                /*Snackbar.make(view, "Products", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        // TODO: 5/6/2019 --- mmkr : densyx features : show/hide doctor menu in main activity

        if (preferenceService.GetPreferenceBooleanValue(PreferenceService.LOGGED_PREFER_KEY_TURN) != null
                &&
                !preferenceService.GetPreferenceBooleanValue(PreferenceService.LOGGED_PREFER_KEY_TURN))
            fam.setVisibility(View.INVISIBLE);

        Log.e("main1", "onCreate: " + preferenceService.GetPreferenceValue(PreferenceService.NST));
        Log.e("main2", "onCreate: " + NetworkUtil.getToken());
        Log.e("main3", "onCreate: " + DeviceId.get());

        resetAuthenticatioToken();
        checkNotificationBundle();
    }

    private void checkNotificationBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int itemType = Integer.valueOf(bundle.getString(MessagingService.ITEM_TYPE, "0"));
            int itemId = Integer.valueOf(bundle.getString(MessagingService.ITEM_ID, "0"));

            if (itemType == MessagingService.ITEM_PRODUCT) {
                ProductModel productModel = new ProductModel();
                productModel.setId(itemId);
                productModel.setName("");
                ProductDetailFragment.productModel = productModel;
                gotoFragment(new ProductDetailFragment());
            } else if (itemType == MessagingService.ITEM_CATEGORY) {
                ProductService.productId = itemId;
                gotoFragment(ProductListFragmentFor3_8.newInstance("", itemId));
            }
        }
    }

    private void gotoFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).addToBackStack(null).commit();
    }

    private void setToolbar() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_toolbar_main);

        iv_toolbar = (ImageView) findViewById(R.id.iv_toolbar);
        iv_toolbar.setVisibility(View.GONE);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    private void resetAuthenticatioToken() {
        /*NetworkUtilities.token="";*/
        NetworkUtil.setToken("");
        if (preferenceService.GetPreferenceIntValue(PreferenceService.APP_VERSION_CODE_KEY) != getApplicationVersionCode()) {
            preferenceService.SetPreferenceValue(PreferenceService.APP_VERSION_CODE_KEY, getApplicationVersionCode());
            preferenceService.SetPreferenceValue(PreferenceService.LOGGED_PREFER_KEY, false);
        }
        if (preferenceService.GetPreferenceBooleanValue(preferenceService.LOGGED_PREFER_KEY)) {
            /*NetworkUtilities.token = preferenceService
                    .GetPreferenceValue(preferenceService.TOKEN_KEY);*/
            NetworkUtil.setToken(preferenceService
                    .GetPreferenceValue(preferenceService.TOKEN_KEY));
        }
    }

    private int getApplicationVersionCode() {
        int versionCode = -1;
        PackageManager manager = getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(
                    getPackageName(), -1);
            versionCode = info.versionCode;
            return versionCode;
        } catch (Exception exception) {

        }
        return versionCode;
    }

    public void setUp() {
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                invalidateOptionsMenu();
                syncState();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
                syncState();
            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle.syncState();
        toolbar.setNavigationOnClickListener(this);
        listeningFragmentTransaction();
        makeActionOverflowMenuShown();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            handleAppExit();
        } else
            super.onBackPressed();
    }

    public void handleAppExit() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), R.string.back_button_click_msg, Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        //Toast.makeText(this, R.string.back_button_click_msg, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void popBackstack() {
        FragmentManager fm = getSupportFragmentManager(); // or 'getSupportFragmentManager();'
        int count = fm.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            fm.popBackStackImmediate();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public void onClick(View v) {
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp() {
        Log.d("clicking", "yes");
        if (getSupportActionBar() != null) {
            if (shouldOpenDrawer()) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawers();
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                setArrowIconInDrawer();
                onBackPressed();
                shouldOpenDrawer();
            } else {
                mDrawerToggle.setDrawerIndicatorEnabled(true);
                // getSupportActionBar().setDisplayHomeAsUpEnabled(false);

            }
        }
    }

    public boolean shouldOpenDrawer() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            mDrawerToggle.setDrawerIndicatorEnabled(true);

            return true;

        } else {
            return false;
        }


    }

    public void listeningFragmentTransaction() {
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    setArrowIconInDrawer();
                } else {
                    mDrawerToggle.setDrawerIndicatorEnabled(true);
                    setAnimationOndrwerIcon();

                }
            }
        });
    }

//    public void setArrowIconInDrawer() {
//        mDrawerToggle.setDrawerIndicatorEnabled(false);
//        setUparrowColor();
//    }

    public void closeDrawer() {
        drawerLayout.closeDrawers();
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
        setUp();

    }

    private void setUparrowColor() {
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_action_nav_arrow_back);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorUparow), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private void setAnimationOndrwerIcon() {
        ValueAnimator anim = ValueAnimator.ofFloat(1, 0);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
                mDrawerToggle.onDrawerSlide(drawerLayout, slideOffset);
            }
        });
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(800);
        anim.start();
    }

    public void setArrowIconInDrawer() {
        mDrawerToggle.setDrawerIndicatorEnabled(false);

        getSupportActionBar().setHomeAsUpIndicator(getNavIconResId());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setDrawerIcon() {
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
    }

    protected int getNavIconResId() {
        String language = preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE);
        if (language.equals(Language.ARABIC)) {
            return R.drawable.ic_action_nav_arrow_back_inverted;
        } else {
            return R.drawable.ic_action_nav_arrow_back;
        }
    }

    public void displayHomeIcon(boolean displayHomeIcon) {
        if (getSupportActionBar() != null) {
            if (displayHomeIcon) {
                setArrowIconInDrawer();
            } else {
                setDrawerIcon();
            }
        }
    }

}
