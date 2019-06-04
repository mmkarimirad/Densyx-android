package com.bs.dental.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.model.ShippingMethod;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.postrequest.ValuePost;
import com.bs.dental.networking.response.ShippingMethodRetrievalResponse;
import com.bs.dental.networking.response.ShippingMethodSelttingResponse;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.adapter.FragmentClass;
import com.bs.dental.ui.customview.CheckableLinearLayout;
import com.bs.dental.ui.customview.RadioGridGroupforReyMaterial;
import com.bs.dental.ui.views.MethodSelctionProcess;
import com.bs.dental.utils.Language;
import com.google.inject.Inject;
import com.rey.material.widget.RadioButton;

import java.util.List;

import roboguice.inject.InjectView;

/**
 * Created by Ashraful on 12/7/2015.
 */
public class ShippingMethodFragment extends BaseFragment implements View.OnClickListener {
    int id = 0;
    @InjectView(R.id.rg_shipiingMethod)
    RadioGridGroupforReyMaterial radioGridGroup;
    @InjectView(R.id.btn_continue)
    Button continueBtn;
    MethodSelctionProcess methodSelctionProcess;
    String shippingMethodValue="";

    @Inject
    PreferenceService preferenceService;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shipping_method, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callShippingMethodRetrievalApi();
        continueBtn.setOnClickListener(this);

    }

    private void callShippingMethodRetrievalApi() {
        RetroClient.getApi().getShippingMethod().enqueue(new CustomCB<ShippingMethodRetrievalResponse>(this.getView()));
    }

    public void onEvent(ShippingMethodRetrievalResponse shippingMethodRetrievalResponse) {
        addMethodRadioGroup(shippingMethodRetrievalResponse.getShippingMethods());
    }



    private void addMethodRadioGroup(List<ShippingMethod> shippingMethods) {
        methodSelctionProcess=new MethodSelctionProcess(radioGridGroup);
        for (ShippingMethod method : shippingMethods)
            generateRadioButton(method);
        }


    private void generateRadioButton(final ShippingMethod method) {
        CheckableLinearLayout linearLayout = (CheckableLinearLayout) getLayoutInflater().
                inflate(R.layout.item_shipping_method, radioGridGroup, false);

        TextView textView = (TextView) linearLayout.findViewById(R.id.tv_shippingMethodDescription);
        final RadioButton radioButton = (RadioButton) linearLayout.findViewById(R.id.rb_shippingChoice);
        radioButton.setText(method.getName());
        radioButton.setId(++id);
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            textView.setGravity(Gravity.RIGHT);
        }

        if (isPreselected(method)) {
            radioButton.setChecked(true);
            shippingMethodValue = method.getName() + "___" + method.getShippingRateComputationMethodSystemName();

        }

        textView.setText(Html.fromHtml(method.getDescription()));

        radioGridGroup.addView(linearLayout);


        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    shippingMethodValue = method.getName() + "___" + method.getShippingRateComputationMethodSystemName();
                    methodSelctionProcess.resetRadioButton(buttonView.getId());
                }
            }
        });

        linearLayout.setOnCheckedChangeListener(new CheckableLinearLayout.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View checkableView, boolean isChecked) {
                if (isChecked)
                    radioButton.setChecked(true);
            }
        });
    }

    private boolean isPreselected(ShippingMethod shippingMethod) {
        return shippingMethod.isSelected();
    }



    @Override
    public void onClick(View v) {
        int resourceId = v.getId();
        if (resourceId == R.id.btn_continue)
            System.out.println(shippingMethodValue);
        saveShippingMethod();
    }

    private void saveShippingMethod() {
        ValuePost valuePost = new ValuePost();
        valuePost.setValue(shippingMethodValue);
        RetroClient.getApi().setShippingMethod(valuePost)
                .enqueue(new CustomCB<ShippingMethodSelttingResponse>(this.getView()));
    }

    public void  onEvent(ShippingMethodSelttingResponse response)
    {
        ((CheckoutStepFragment)getParentFragment()).replaceFragment(FragmentClass.ShippingMethod);

    }

}
