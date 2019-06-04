package com.bs.dental.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bs.dental.R;
import com.bs.dental.model.ChangePasswordModel;
import com.bs.dental.model.ChangePasswordResponse;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;

import de.greenrobot.event.EventBus;
import roboguice.inject.InjectView;

/**
 * Created by bs-110 on 12/23/2015.
 */
public class PasswordChangeFragment extends BaseFragment implements View.OnClickListener{
    @InjectView(R.id.et_current_password)
    EditText currentPasswordEditText;
    @InjectView(R.id.et_password)
    EditText passwordEditText;
    @InjectView(R.id.et_confirm_password)
    EditText confirmPasswordEditText;
    @InjectView(R.id.btn_save)
    Button saveButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        getActivity().setTitle(getString(R.string.changePassword));
        saveButton.setOnClickListener(this);
    }

    private void saveChangePassword(){
        if(isValid(getChangePassword())){
            callChangePasswordWebService();
        } else {
            saveButton.setEnabled(true);
        }
    }

    private void callChangePasswordWebService(){
        RetroClient.getApi().changePassword(getChangePassword()).enqueue(new CustomCB<ChangePasswordResponse>());
    }

    public void onEvent(ChangePasswordResponse response){
        saveButton.setEnabled(true);
        String tMessage = "";

            if(response.getStatusCode() == 400 && response.getErrorList().length > 0){
                tMessage = response.getErrorsAsFormattedString();
            } else {
                tMessage = getString(R.string.password_change_success);
                getFragmentManager().popBackStack();
            }

        showToast(tMessage);
    }

    private boolean isValid(ChangePasswordModel cpm){
        boolean isValid = true;
        boolean isEmpty =  cpm.getOldPassword().length() == 0
                || cpm.getNewPassword().length() == 0
                || cpm.getConfirmNewPassword().length() == 0;

        if(isEmpty) {
            showToast(getString(R.string.all_field_are_require));
        } else {
            if (!cpm.getNewPassword().equals(cpm.getConfirmNewPassword())) {
                isValid = false;
                showToast(getString(R.string.new_password_not_match));
            } else if (cpm.getNewPassword().equals(cpm.getOldPassword())) {
                isValid = false;
                showToast(getString(R.string.new_password_same_as));
            }
        }

        return !isEmpty && isValid;
    }


    private ChangePasswordModel getChangePassword(){
        ChangePasswordModel cpm = new ChangePasswordModel();
        cpm.setOldPassword(currentPasswordEditText.getText().toString().trim());
        cpm.setNewPassword(passwordEditText.getText().toString().trim());
        cpm.setConfirmNewPassword(confirmPasswordEditText.getText().toString().trim());

        return cpm;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_save:
                saveButton.setEnabled(false);
                saveChangePassword();
                break;
        }
    }
}
