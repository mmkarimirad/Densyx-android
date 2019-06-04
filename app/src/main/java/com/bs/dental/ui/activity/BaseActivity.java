package com.bs.dental.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.TextView;

import com.bs.dental.Constants;
import com.bs.dental.R;
import com.bs.dental.model.LoginResponse;
import com.bs.dental.networking.NetworkUtil;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.fragment.CartFragment;
import com.bs.dental.ui.fragment.ContactFragment;
import com.bs.dental.ui.fragment.CustomerAddressesFragment;
import com.bs.dental.ui.fragment.CustomerInfoFragment;
import com.bs.dental.ui.fragment.CustomerOrdersFragment;
import com.bs.dental.ui.fragment.HomePageFragment;
import com.bs.dental.ui.fragment.LoginFragment;
import com.bs.dental.ui.fragment.SearchFragment;
import com.bs.dental.ui.fragment.Utility;
import com.bs.dental.ui.fragment.WishlistFragment;
import com.bs.dental.utils.Language;
import com.facebook.login.LoginManager;
import com.github.clans.fab.FloatingActionMenu;
import com.google.inject.Inject;

import java.lang.reflect.Field;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import roboguice.activity.RoboActionBarActivity;

/**
 * Created by Ashraful on 11/5/2015.
 */
public class BaseActivity extends RoboActionBarActivity {
    public ActionBarDrawerToggle mDrawerToggle;
    public TextView ui_hot;
    @Inject
    PreferenceService preferenceService;


    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLocale(false);
    }

    public void onEvent(LoginResponse response) {
        if (response.getToken() != null) {
            invalidateOptionsMenu();
        }
    }

    public void getBaseUrl() {
        // TODO: 4/29/2019 mmkr chaneged to https
        //*** mmkr commented and removed: Constants.BASE_URL="http://api.densyx.com/api/";
        //Constants.BASE_URL="http://area1.kokonad.com/api/";
      /*  if (preferenceService.GetPreferenceBooleanValue(PreferenceService.DO_USE_NEW_URL)) {
            Constants.BASE_URL = preferenceService.GetPreferenceValue
                    (PreferenceService.URL_PREFER_KEY);

            Log.e("base", "getBaseUrl: "+ Constants.BASE_URL);
        }*/
    }

    public void goMenuItemFragment(Fragment fragment) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();

    }

    public void goMenuItemFragmentifloggedIn(Fragment fragment) {
        if (!isLoggedIn())
            fragment = new LoginFragment();
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        changeMenuItemLoginAction(menu);
        setOrRefreshCurtMenuItem(menu);
        return true;
    }

    private void setOrRefreshCurtMenuItem(Menu menu) {
        final View menu_hotlist = menu.findItem(R.id.menu_cart).getActionView();
        ui_hot = (TextView) menu_hotlist.findViewById(R.id.hotlist_hot);
        menu_hotlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.closeLeftDrawer();

                if (!(getSupportFragmentManager().findFragmentById(R.id.container) instanceof CartFragment))
                    goMenuItemFragment(new CartFragment());
            }
        });
        updateHotCount(Utility.cartCounter);
    }

    public void updateHotCount(final int badgeCount) {
        // badgeCount = new_hot_number;
        if (ui_hot == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (badgeCount == 0)
                    ui_hot.setVisibility(View.INVISIBLE);
                else {
                    ui_hot.setVisibility(View.VISIBLE);
                    ui_hot.setText(Long.toString(badgeCount));
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // hide imageview in first fragment
        ImageView iv_toolbar = (ImageView) findViewById(R.id.iv_toolbar);
        iv_toolbar.setVisibility(View.GONE);

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int resource = item.getItemId();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (resource == R.id.menu_cart) {
            goMenuItemFragment(new CartFragment());
            return true;
        } else if (resource == R.id.menu_item_login && !(fragment instanceof LoginFragment)) {
            if (isLoggedIn()) {
                logoutConfirmationDialog();
                item.setTitle(getString(R.string.login));
            } else {
                goMenuItemFragment(new LoginFragment());
            }
            return true;
        } else if (resource == R.id.menu_item_customer_info && !(fragment instanceof CustomerInfoFragment)) {
            goMenuItemFragmentifloggedIn(new CustomerInfoFragment());
            return true;
        } else if (resource == R.id.menu_item_addresses && !(fragment instanceof CustomerAddressesFragment)) {
            goMenuItemFragmentifloggedIn(new CustomerAddressesFragment());
            return true;
        } else if (resource == R.id.menu_item_orders && !(fragment instanceof CustomerOrdersFragment)) {
            goMenuItemFragmentifloggedIn(new CustomerOrdersFragment());
            return true;
        } else if (resource == R.id.menu_item_wishlist && !(fragment instanceof WishlistFragment)) {
            goMenuItemFragmentifloggedIn(new WishlistFragment());
            return true;
        } else if (resource == R.id.menu_search && !(fragment instanceof SearchFragment)) {
            goMenuItemFragment(new SearchFragment());
            return true;
        }/*else if (resource == R.id.menu_item_news) {
            Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
            intent.putExtra("categoryId", 1000000);
            startActivity(intent);
            return true;
        }else if (resource == R.id.menu_item_weblog) {
            Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
            intent.putExtra("categoryId", 1000001);
            startActivity(intent);
            return true;
        }*/ else if (resource == R.id.menu_item_contact_us/* && !(fragment instanceof ContactusFragment)*/) {
            goMenuItemFragment(new ContactFragment());
            return true;
        }

        //comment
       /* else if (resource == R.id.menu_item_barcode_read && !(fragment instanceof BarCodeCaptureFragment)) {
            goMenuItemFragment(new BarCodeCaptureFragment());
            return true;
        }

        else if (resource == R.id.menu_item_change_url) {
            Intent intent = new Intent(this, BaseUrlChangeActivity.class);
            startActivity(intent);
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }


    public void changeMenuItemLoginAction(Menu menu) {
        if (isLoggedIn()) {
            menu.findItem(R.id.menu_item_login).setTitle(R.string.log_out);
        } else
            menu.findItem(R.id.menu_item_login).setTitle(R.string.sign_in);
    }

    public void makeActionOverflowMenuShown() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            Log.d("", e.getLocalizedMessage());
        }
    }

    public boolean isLoggedIn() {
        return preferenceService.GetPreferenceBooleanValue(PreferenceService.LOGGED_PREFER_KEY);
    }

    public void performLogout() {
        /*NetworkUtilities.token = "";*/
        NetworkUtil.setToken("");
        Utility.cartCounter = 0;
        preferenceService.SetPreferenceValue(PreferenceService.TOKEN_KEY, "");
        preferenceService.SetPreferenceValue(PreferenceService.LOGGED_PREFER_KEY, false);
        LoginManager.getInstance().logOut();
        goMenuItemFragment(new HomePageFragment());
        invalidateOptionsMenu();
    }

    private void logoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);

        builder.setMessage(R.string.are_you_logout)
                .setTitle(R.string.log_out);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                performLogout();
                // TODO: 5/18/2019 --- Login -- check turn menu visibility
                preferenceService.SetPreferenceValue(PreferenceService.LOGGED_PREFER_KEY_TURN, false);
                FloatingActionMenu fam = (FloatingActionMenu) findViewById(R.id.fam);
                fam.setVisibility(View.INVISIBLE);
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public View getActivityContentView() {
        return this.getWindow().getDecorView().findViewById(android.R.id.content);

    }

    public void setLocale(boolean recreate) {
        String preferredLanguage = preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE);
        //boolean shouldRecreate = getActivityContentView() != null;
        if (TextUtils.isEmpty(preferredLanguage)) {
//            preferredLanguage = Language.ENGLISH;
            preferredLanguage = Language.PERSIAN;
            preferenceService.SetPreferenceValue(PreferenceService.CURRENT_LANGUAGE, preferredLanguage);
        }

        Locale current = Locale.getDefault();
        String currentLanguage = current.getLanguage();


        if (!currentLanguage.equalsIgnoreCase(preferredLanguage)) {
            Locale preferredLocale = new Locale(preferredLanguage);
            Locale.setDefault(preferredLocale);
            Configuration configuration = new Configuration();
            configuration.locale = preferredLocale;
            getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
            if (recreate) {
                recreate();
            }
        }
    }

}
