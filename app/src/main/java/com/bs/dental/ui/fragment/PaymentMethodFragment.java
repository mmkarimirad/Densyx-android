package com.bs.dental.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bs.dental.R;
import com.bs.dental.model.PaymentMethod;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.postrequest.ValuePost;
import com.bs.dental.networking.response.PaymentMethodRetrievalResponse;
import com.bs.dental.networking.response.PaymentMethodSaveResponse;
import com.bs.dental.ui.adapter.FragmentClass;
import com.bs.dental.ui.customview.RadioGridGroupforReyMaterial;
import com.bs.dental.ui.views.MethodSelctionProcess;
import com.google.gson.Gson;
import com.rey.material.widget.RadioButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import roboguice.inject.InjectView;

/**
 * Created by Ashraful on 12/7/2015.
 */
public class PaymentMethodFragment extends BaseFragment implements View.OnClickListener {
    int id=0;
    @InjectView(R.id.rg_shipiingMethod)
    RadioGridGroupforReyMaterial radioGridGroup;
    @InjectView(R.id.btn_continue)
    Button continueBtn;
    MethodSelctionProcess methodSelctionProcess;
    String PaymentMethodValue="";
    //String PaymentMethodValuePrefix="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shipping_method,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id=0;
        callApiOfGettingPaymentMethod();
        continueBtn.setOnClickListener(this);
    }

    private void callApiOfGettingPaymentMethod() {
        RetroClient.getApi().getPaymentMethod().enqueue(new CustomCB<PaymentMethodRetrievalResponse>(this.getView()));
    }

    public void onEvent(PaymentMethodRetrievalResponse paymentMethodRetrievalResponse)
    {
        Log.d("paymentMethods",new Gson().toJson(paymentMethodRetrievalResponse));
       addMethodRadioGroup(paymentMethodRetrievalResponse.getPaymentMethods());
    }
    private void addMethodRadioGroup(List<PaymentMethod> paymentMethods) {
        methodSelctionProcess=new MethodSelctionProcess(radioGridGroup);
        for (PaymentMethod method : paymentMethods)
            generateRadioButton(method);
    }


    private void generateRadioButton(final PaymentMethod method) {
        LinearLayout linearLayout = (LinearLayout) getLayoutInflater().
                inflate(R.layout.item_payment_method, radioGridGroup, false);

        final RadioButton radioButton = (RadioButton) linearLayout.findViewById(R.id.rb_paymentChoice);
        radioButton.setText(method.getName());
        radioButton.setId(++id);

        if (isPreselected(method)) {
            radioButton.setChecked(true);
            PaymentMethodValue=method.getPaymentMethodSystemName();
        }

        ImageView imageView=(ImageView)linearLayout.findViewById(R.id.iv_paymentMethodImage);
        Picasso.with(getActivity()).load(method.getLogoUrl()).into(imageView);

        radioGridGroup.addView(linearLayout);

        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    methodSelctionProcess.resetRadioButton(buttonView.getId());
                    PaymentMethodValue = method.getPaymentMethodSystemName();
                }
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radioButton.setChecked(true);
            }
        });

    }

    private boolean isPreselected(PaymentMethod method) {
        return method.isSelected();
    }

    @Override
    public void onClick(View v) {

        if(!PaymentMethodValue.isEmpty())
        RetroClient.getApi().saveCheckoutPaymentMethod(new ValuePost(PaymentMethodValue))
                .enqueue(new CustomCB<PaymentMethodSaveResponse>(this.getView()));
    }

    public void onEvent(PaymentMethodSaveResponse saveResponse)
    {
        if(saveResponse.getStatusCode()==200)
            ((CheckoutStepFragment)getParentFragment()).replaceFragment(FragmentClass.PaymentMethod);
    }

}

