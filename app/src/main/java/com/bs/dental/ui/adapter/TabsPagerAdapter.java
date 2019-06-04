package com.bs.dental.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bs.dental.ui.fragment.BillingAddressFragment;
import com.bs.dental.ui.fragment.ConfirmOrderFragment;
import com.bs.dental.ui.fragment.PaymentInformationFragment;
import com.bs.dental.ui.fragment.PaymentMethodFragment;
import com.bs.dental.ui.fragment.ShippingAddressFragment;
import com.bs.dental.ui.fragment.ShippingMethodFragment;

/**
 * Created by Ashraful on 12/7/2015.
 */
public class TabsPagerAdapter extends FragmentStatePagerAdapter {

    public String title[] = {"Billing", "Shipping", "Shipping Method",
            "Payment Method","Confirm Order", "Payment Infromation"};
    public static int adapterSize=5;

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == FragmentClass.BillingAddress)
          fragment=new BillingAddressFragment();
       else if (position == FragmentClass.ShippingAddress)
            fragment=new ShippingAddressFragment();
        else if (position == FragmentClass.ShippingMethod)
            fragment=new ShippingMethodFragment();
        else if (position == FragmentClass.PaymentMethod)
            fragment=new PaymentMethodFragment();
        else if (position == FragmentClass.PaymentInfromation)
            fragment=new PaymentInformationFragment();

        else if (position == FragmentClass.ConfirmOrder)
            fragment=new ConfirmOrderFragment();

            return fragment;

    }

    @Override
    public int getCount() {
        return adapterSize;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }


}
