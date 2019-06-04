package com.bs.dental.ui.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bs.dental.R;
import com.bs.dental.model.CustomerRegistrationInfo;
import com.bs.dental.model.FormValue;
import com.bs.dental.model.LoginData;
import com.bs.dental.model.LoginResponse;
import com.bs.dental.model.RegistrationResponse;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.NetworkUtil;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.utils.UiUtils;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import roboguice.inject.InjectView;

/**
 * Created by bs-110 on 12/9/2015.
 */
public class RegisterFragment extends BaseFragment {

    @InjectView(R.id.tvName)
    TextView nameTitleTextView;

    @InjectView(R.id.customer_name)
    TextView customerNameTextView;

    @InjectView(R.id.dateOfBirth)
    TextView dateOfBirthTextView;

    @InjectView(R.id.customer_email)
    TextView emailTextView;

    @InjectView(R.id.company_info)
    TextView companyInfoTextView;

    @InjectView(R.id.gender)
    TextView genderTextView;

    @InjectView(R.id.et_customer_first_name)
    EditText customerFirstNameEditText;

    @InjectView(R.id.et_customer_last_name)
    EditText customerLastNameEditText;

    // TODO: 6/2/2019 --- mmkr : dentyx features for national user identity
    @InjectView(R.id.et_customer_attribute_3)
    EditText customer_attribute_3;

    @InjectView(R.id.et_customer_email)
    EditText emailEditText;

    @InjectView(R.id.et_company_info)
    EditText companyInfoEditText;

    @InjectView(R.id.rb_male)
    RadioButton genderMaleRadioButton;

    @InjectView(R.id.rb_female)
    RadioButton genderFemaleRadioButton;

    @InjectView(R.id.genderRadioGroup)
    RadioGroup genderRadioGroup;

    @InjectView(R.id.btn_save)
    Button saveBtn;

    @InjectView(R.id.cb_newsletter)
    CheckBox cbNewsLetter;

    @InjectView(R.id.et_password)
    EditText passwordEditText;

    @InjectView(R.id.et_confirm_password)
    EditText confirmPasswordEditText;
    @InjectView(R.id.tv_password)
    TextView passwordTextView;

    @InjectView(R.id.customer_username)
    TextView usernameTextView;
    @InjectView(R.id.et_customer_username)
    EditText usernameEditText;

    @InjectView(R.id.customer_phone)
    TextView phoneTextView;
    @InjectView(R.id.et_customer_phone)
    EditText phoneEditText;

    Calendar myCalendar = null;

    private CustomerRegistrationInfo customerInfo;
    private List<FormValue> formValues ;

    @Inject
    PreferenceService preferenceService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_basic_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         checkEventBusRegistration();
        getActivity().setTitle(getString(R.string.register));

        clearUI();
        hideTextPanels();
        initEditButtonsAction();

    }

    private void callRegisterWebService() {
        RetroClient.getApi().preformRegistration(customerInfo).enqueue(new CustomCB<RegistrationResponse>(this.getView()));
    }

    public void callLoginWebservice(LoginData loginData) {
        RetroClient.getApi().performLogin(loginData).enqueue(new CustomCB<LoginResponse>(this.getView()));
    }

    public void onEvent(LoginResponse response) {
        if (response.getToken() != null) {
            preferenceService.SetPreferenceValue(PreferenceService.TOKEN_KEY, response.getToken());
            preferenceService.SetPreferenceValue(PreferenceService.LOGGED_PREFER_KEY, true);
            /*NetworkUtilities.token=response.getToken();*/
            NetworkUtil.setToken(response.getToken());
            getFragmentManager().beginTransaction().replace(R.id.container,
                    new MyAccountFragment()).commit();
        }
    }

    public void onEvent(RegistrationResponse response) {
        Log.d("REGISTER", customerInfo.toString());
        if(response.getStatusCode() == 400){
            saveBtn.setEnabled(true);
            String errors = getString(R.string.error_register_customer)+"\n";
            if(response.getErrorList().length > 0){
                for(int i=0; i< response.getErrorList().length; i++ ){
                    errors += "  "+(i+1) + ": " + response.getErrorList()[i] + " \n";
                }
                Toast.makeText(getActivity(), errors, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity(),getString(R.string.customer_succes_msg), Toast.LENGTH_LONG).show();
            callLoginWebservice(new LoginData(customerInfo.getEmail(), customerInfo.getPassword()));
        }
    }

    private void hideTextPanels() {
        nameTitleTextView.setVisibility(View.GONE);
        customerNameTextView.setVisibility(View.GONE);
        emailTextView.setVisibility(View.GONE);
        companyInfoTextView.setVisibility(View.GONE);
        genderTextView.setVisibility(View.GONE);
        usernameTextView.setVisibility(View.GONE);
        saveBtn.setText(getString(R.string.register_new));
        dateOfBirthTextView.setText("dd/mm/yyyy");
        passwordTextView.setVisibility(View.GONE);
        phoneTextView.setVisibility(View.GONE);
    }

    private void clearUI(){
        customerNameTextView.setText("");
        dateOfBirthTextView.setText("");
        emailTextView.setText("");
        companyInfoTextView.setText("");
        genderTextView.setText("");
        usernameTextView.setText("");
        phoneTextView.setText("");
        customer_attribute_3.setText("");
        customerFirstNameEditText.setText("");
        customerLastNameEditText.setText("");
        emailEditText.setText("");
        companyInfoEditText.setText("");
        usernameEditText.setText("");
        phoneEditText.setText("");
    }

    private void initEditButtonsAction(){

        dateOfBirthTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar c = Calendar.getInstance();
                if(myCalendar != null){
                    c = myCalendar;
                }

                new DatePickerDialog(getActivity(), dateSetListener, c
                        .get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performRegistration();
                saveBtn.setEnabled(false);
                UiUtils.hideSoftKeyboard(getActivity());
            }
        });
    }

    private void performRegistration() {
        getCustomerInfo();
        if (isValidCustomerInfo()) {
            callRegisterWebService();
        }
    }

    private boolean isValidCustomerInfo() {
        return true;
    }

    private void getCustomerInfo() {
        customerInfo = new CustomerRegistrationInfo();
        formValues = new ArrayList<>();
        formValues.add(new FormValue("customer_attribute_3",customer_attribute_3.getText().toString()));

        customerInfo.setFirstName(customerFirstNameEditText.getText().toString());
        customerInfo.setLastName(customerLastNameEditText.getText().toString());
        customerInfo.setEmail(emailEditText.getText().toString());
        customerInfo.setPhone(phoneEditText.getText().toString());
        customerInfo.setCompany(companyInfoEditText.getText().toString());

//        formValues.get(0).setKey("customer_attribute_3");
//        formValues.get(0).setValue(customer_attribute_3.getText().toString());

        customerInfo.setFormValue(formValues);
        if(myCalendar!=null) {
            customerInfo.setDateOfBirthYear(myCalendar.get(Calendar.YEAR));
            customerInfo.setDateOfBirthMonth(myCalendar.get(Calendar.MONTH) + 1);
            customerInfo.setDateOfBirthDay(myCalendar.get(Calendar.DAY_OF_MONTH));
        }
        if(genderMaleRadioButton.isChecked()){
            customerInfo.setGender("M");
        } else if(genderFemaleRadioButton.isChecked()){
            customerInfo.setGender("F");
        }
        customerInfo.setNewsletter(cbNewsLetter.isChecked());
        customerInfo.setPassword(passwordEditText.getText().toString());
        customerInfo.setConfirmPassword(confirmPasswordEditText.getText().toString());
        customerInfo.setUsername(usernameEditText.getText().toString());
    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar = Calendar.getInstance();
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            dateOfBirthTextView.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
            saveBtn.setVisibility(View.VISIBLE);
        }

    };

}
