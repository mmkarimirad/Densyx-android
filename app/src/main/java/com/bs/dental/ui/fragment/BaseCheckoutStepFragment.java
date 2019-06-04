package com.bs.dental.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bs.dental.R;
import com.bs.dental.ui.adapter.FragmentClass;

import roboguice.inject.InjectView;

/**
 * Created by Ashraful on 1/28/2016.
 */
public class BaseCheckoutStepFragment extends BaseFragment implements View.OnClickListener{
    @InjectView(R.id.tab_address)
    View addressTab;
    @InjectView(R.id.tab_shipping)
    View shippingTab;
    @InjectView(R.id.tab_payment)
    View paymentTab;
    @InjectView(R.id.tab_confirm)
    View confirmTab;

    public static boolean isBillingComplete = false;
    public static boolean isShippingComplete = false;
    public static boolean isPaymentMethodComplete = false;
    public static boolean isShippingMethodComplete = false;
    public static boolean isConfirmComplete = false;


    protected void replaceFragment(int returnFragmentId) {
        Fragment fragment = null;
        String tag=null;
      //  getChildFragmentManager().popBackStack(presentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        switch (returnFragmentId) {
            case FragmentClass.BillingAddress:
                isBillingComplete = true;
                fragment = new ShippingAddressFragment();
                clearSelected();
                addressTab.setBackgroundResource(R.drawable.border_bottom_accent);
                tag="shipping";
                popPreviousFragment(tag);
                break;
            case FragmentClass.ShippingAddress:
                gotoNextStepAfterShippingAddress(fragment);
                break;
            case FragmentClass.ShippingMethod:
                gotoNextStepAfterShippingMethod(fragment);
                break;
            case FragmentClass.PaymentMethod:
                isPaymentMethodComplete = true;
                fragment = new ConfirmOrderFragment();
                clearSelected();
                confirmTab.setBackgroundResource(R.drawable.border_bottom_accent);
                tag="confirm";
                break;
            default:
                gotoDefaultMethod(fragment);
                /*fragment = new BillingAddressFragment();
                clearSelected();
                addressTab.setBackgroundResource(R.drawable.border_bottom_accent);*/
        }

        if(fragment != null) {
            replaceFragment(fragment,tag);
           /* transaction.addToBackStack(null);
            transaction.replace(R.id.container, fragment);
            transaction.commit();
            getChildFragmentManager().executePendingTransactions();*/
        }
    }

    protected void replaceFragment( Fragment fragment,String tag)
    {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.addToBackStack(tag);
        transaction.replace(R.id.container, fragment);
        transaction.commit();
        getChildFragmentManager().executePendingTransactions();
    }


    protected void gotoNextStepAfterShippingAddress(Fragment fragment)
    {
        isShippingComplete = true;
        fragment = new ShippingMethodFragment();
        clearSelected();
        shippingTab.setBackgroundResource(R.drawable.border_bottom_accent);
        replaceFragment(fragment, "shipping_method");
    }

    protected void gotoNextStepAfterShippingMethod(Fragment fragment)
    {
        isShippingMethodComplete = true;
        fragment = new PaymentMethodFragment();
        clearSelected();
        paymentTab.setBackgroundResource(R.drawable.border_bottom_accent);
        replaceFragment(fragment,"payment_method");
    }

    protected void gotoDefaultMethod(Fragment fragment)
    {
        fragment = new BillingAddressFragment();
        clearSelected();
        addressTab.setBackgroundResource(R.drawable.border_bottom_accent);
        popPreviousFragment("billing");
        replaceFragment(fragment,"billing");

    }
    protected void clearSelected(){
        addressTab.setBackgroundResource(R.color.accent);
        shippingTab.setBackgroundResource(R.color.accent);
        paymentTab.setBackgroundResource(R.color.accent);
        confirmTab.setBackgroundResource(R.color.accent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("test", "tab 1 oncreateview");
        return inflater.inflate(R.layout.fragment_address_container, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        Log.e("test", "tab 1 init view");
        replaceFragment(-1);
        addressTab.setOnClickListener(this);
        shippingTab.setOnClickListener(this);
        paymentTab.setOnClickListener(this);
        confirmTab.setOnClickListener(this);
        isBillingComplete=false;
        isConfirmComplete=false;
        isPaymentMethodComplete=false;
        isShippingComplete=false;
        isShippingMethodComplete=false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tab_address:
                popPreviousFragment("shipping");
                replaceFragment(FragmentClass.BillingAddress);
                break;
            case R.id.tab_shipping:
                if(isShippingMethodComplete)
                {
                    popPreviousFragment("shipping_method");
                    replaceFragment(FragmentClass.ShippingAddress);
                }
                else
                    showMsg();
                break;
            case R.id.tab_payment:
                onPaymentTabClick();
                break;
            case R.id.tab_confirm:
                if(isConfirmComplete)
                {
                    popPreviousFragment("confirm");
                    replaceFragment(FragmentClass.PaymentMethod);
                }
                else
                    showMsg();
                break;
        }
    }

    protected void onPaymentTabClick()
    {
        if(isPaymentMethodComplete)
        {
            popPreviousFragment("payment_method");
            replaceFragment(FragmentClass.ShippingMethod);
        }
        else
            showMsg();
    }

    protected void popPreviousFragment(String tag)
    {
        getChildFragmentManager().popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    protected void showMsg() {
        showToast(getString(R.string.please_complete_previous_step));
    }

    @Override
    public void onStart() {
        super.onStart();
        onBackPressed();
    }

    public void onBackPressed()
    {
        View view=this.getView();
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.d("stacksize", "" + getChildFragmentManager().getBackStackEntryCount());
                    selectTab(getChildFragmentManager().getBackStackEntryCount());
                    if (getChildFragmentManager().getBackStackEntryCount() > 1)
                        getChildFragmentManager().popBackStackImmediate();
                    else
                        getFragmentManager().popBackStackImmediate();
                    return true;
                } else {
                    return false;
                }
            }


        });
    }

    public void selectTab(int position)
    {
        if(position>=1)
        {
            clearSelected();
            View view=null;
            if(position==4)
            {
                view=paymentTab;
                isPaymentMethodComplete=false;

            }

            else if(position==3)
            {
                view=shippingTab;
                isShippingMethodComplete=false;
            }
            else if(position==2)
            {
                view=addressTab;
                isShippingComplete=false;
            }

            else
            {
                view=addressTab;
                isBillingComplete=false;
            }
            view.setBackgroundResource(R.drawable.border_bottom_accent);
        }
    }
}
    /*
    static ViewPager viewPager;
    @InjectView(R.id.tablayout)
    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_checkout_steps,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Checkout");
         viewPager=(ViewPager)view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(1);
        TabsPagerAdapter tabsPagerAdapter=new TabsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(tabsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }




    public static void onTabChanged(int position) {
       viewPager.setCurrentItem(position);
    }
    */

