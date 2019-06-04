package com.bs.dental.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bs.dental.R;
import com.bs.dental.model.ForgetData;
import com.bs.dental.model.ForgetResponse;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;

import roboguice.inject.InjectView;

/**
 * Created by BS-175 on 25-Apr-17.
 */

public class ForgotPasswordFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.forgetSend)
    Button forgetSend;

    @InjectView(R.id.etForgetEmail)
    EditText etForgetEmail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.forget_password, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        forgetSend.setOnClickListener(this);
        getActivity().setTitle(getString(R.string.forgot_password));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == forgetSend.getId()) {
            if (TextUtils.isEmpty(etForgetEmail.getText().toString())) {
                etForgetEmail.setError(getString(R.string.enter_email));
                etForgetEmail.requestFocus();
            } else if (!isValidEmail(etForgetEmail.getText().toString())) {
                etForgetEmail.setError(getString(R.string.enter_valid_email));
                etForgetEmail.requestFocus();
            } else {
                ForgetData forgetData=new ForgetData(etForgetEmail.getText().toString());
                RetroClient.getApi().forgetPassword(forgetData).enqueue(new CustomCB<ForgetResponse>(this.getView()));
            }
        }
    }

    public void onEvent(ForgetResponse response) {
        if (response.getStatusCode() == 200) {
            etForgetEmail.setText("");
            Toast.makeText(getActivity(),response.getSuccessMessage(),Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        } else {
            Toast.makeText(getActivity(),response.getErrorsAsFormattedString(),Toast.LENGTH_SHORT).show();

        }

    }
}
