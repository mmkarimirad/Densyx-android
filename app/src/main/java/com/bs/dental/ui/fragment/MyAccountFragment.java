package com.bs.dental.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bs.dental.R;
import com.bs.dental.networking.NetworkUtil;
import com.bs.dental.service.PreferenceService;
import com.google.inject.Inject;

import roboguice.inject.InjectView;

/**
 * Created by bs-110 on 12/10/2015.
 */
public class MyAccountFragment extends BaseFragment {
    private String[] titles = {getString(R.string.customer_info),
            getString(R.string.addresses),
            getString(R.string.orders),
            getString(R.string.downloadable_product),
//            "Back in stock subscriptions",
//            "Reward points",
            getString(R.string.changePassword),
            getString(R.string.log_out)
    };

    @InjectView(R.id.list)
    private ListView lvItems;
    private ArrayAdapter adapter;

    @Inject
    PreferenceService preferenceService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_my_account, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getString(R.string.my_account));
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_activated_1, titles);
        lvItems.setAdapter(adapter);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==5){
                    performLogout();
                }
                if(!isLoggedIn()){
                    showFragment(new LoginFragment());
                } else {
                    if(i==0) {
                        showFragment(new CustomerInfoFragment());
                    } else if(i==1) {
                        showFragment(new CustomerAddressesFragment());
                    } else if(i==2){
                        showFragment(new CustomerOrdersFragment());
                    }
                }
            }
        });
    }

    private void showFragment(Fragment fragment){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null).commit();
    }


    private boolean isLoggedIn(){
        return preferenceService.GetPreferenceBooleanValue(PreferenceService.LOGGED_PREFER_KEY);
    }

    private void performLogout(){
        /*NetworkUtilities.token="";*/
        NetworkUtil.setToken("");
        preferenceService.SetPreferenceValue(PreferenceService.LOGGED_PREFER_KEY, false);
    }
}
