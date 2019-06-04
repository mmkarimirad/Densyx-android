package com.bs.dental.ui.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bs.dental.R;
import com.bs.dental.model.CustomerInfo;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.utils.UiUtils;
import com.google.inject.Inject;

import java.util.Calendar;

import de.greenrobot.event.EventBus;
import roboguice.inject.InjectView;

/**
 * Created by bs-110 on 12/9/2015.
 */
public class CustomerInfoFragment extends BaseFragment {

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

    @InjectView(R.id.lv_customer_name_edit_layout)
    LinearLayout customerNameEditText;


    @InjectView(R.id.et_password)
    EditText passwordEditText;
    @InjectView(R.id.et_confirm_password)
    EditText confirmPasswordEditText;
    @InjectView(R.id.tv_password)
    TextView passwordTextView;
    @InjectView(R.id.tv_confirm_password_label)
    TextView confirmPasswordLabel;

    @InjectView(R.id.cb_newsletter)
    CheckBox cbNewsLetter;

    @InjectView(R.id.customer_username)
    TextView usernameTextView;
    @InjectView(R.id.et_customer_username)
    EditText usernameEditText;

    @InjectView(R.id.customer_phone)
    TextView phoneTextView;
    @InjectView(R.id.et_customer_phone)
    EditText phoneEditText;

    Calendar myCalendar = null;

    private CustomerInfo customerInfo;


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
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        getActivity().setTitle(getString(R.string.my_account));

        clearUI();
        hideEditPanels();
        initEditButtonsAction();
        callWebservice();
    }

    public void callWebservice() {
        RetroClient.getApi().getCustomerInfo().enqueue(new CustomCB<CustomerInfo>(this.getView()));
    }

    private void callSaveCustomerInfoWebService() {
        RetroClient.getApi().saveCustomerInfo(customerInfo).enqueue(new CustomCB<CustomerInfo>(this.getView()));
    }


    public void onEvent(CustomerInfo customer) {
        if(customer.getStatusCode() == 200) {
            customerInfo = customer;

            customerNameTextView.setText(customer.getFirstName() + " " + customer.getLastName());
            customerFirstNameEditText.setText(customer.getFirstName());
            customerLastNameEditText.setText(customer.getLastName());

            if(customer.getDateOfBirthDay() > 0) {
                dateOfBirthTextView.setText(customer.getDateOfBirthDay() + "/" + customer.getDateOfBirthMonth() + "/" + customer.getDateOfBirthYear());
                myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, customer.getDateOfBirthYear());
                myCalendar.set(Calendar.MONTH, customer.getDateOfBirthMonth() - 1);
                myCalendar.set(Calendar.DAY_OF_MONTH, customer.getDateOfBirthDay());
            } else {
                dateOfBirthTextView.setText("");
            }

            emailTextView.setText(customer.getEmail());
            emailEditText.setText(customer.getEmail());

            phoneTextView.setText(customer.getPhone());
            phoneEditText.setText(customer.getPhone());

            usernameEditText.setText(customer.getUsername());
            usernameTextView.setText(customer.getUsername());

            companyInfoTextView.setText(customer.getCompany());
            companyInfoEditText.setText(customer.getCompany());


            if (customer.getGender().equalsIgnoreCase("m")) {
                genderMaleRadioButton.setChecked(true);
                genderTextView.setText(getString(R.string.male));
            } else if (customer.getGender().equalsIgnoreCase("f")) {
                genderFemaleRadioButton.setChecked(true);
                genderTextView.setText(getString(R.string.female));
            }

            cbNewsLetter.setChecked(customer.isNewsletter());

            hideEditPanels();
            showTextPanels();

            if(!saveBtn.isEnabled()) {
                saveBtn.setEnabled(true);
                Toast.makeText(getActivity(), R.string.customer_info_updated, Toast.LENGTH_LONG).show();
            }
        } else {
            if(!saveBtn.isEnabled()) {
                saveBtn.setEnabled(true);
                String errors = getString(R.string.error_saving_data)+"\n";
                if (customer.getErrorList().length > 0) {
                    for (int i = 0; i < customer.getErrorList().length; i++) {
                        errors += "  " + (i + 1) + ": " + customer.getErrorList()[i] + " \n";
                    }
                    Toast.makeText(getActivity(), errors, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void showTextPanels() {
        customerNameTextView.setVisibility(View.VISIBLE);
        emailTextView.setVisibility(View.VISIBLE);
        companyInfoTextView.setVisibility(View.VISIBLE);
        genderTextView.setVisibility(View.VISIBLE);
        usernameTextView.setVisibility(View.VISIBLE);
        phoneTextView.setVisibility(View.VISIBLE);
    }

    private void clearUI() {
        customerNameTextView.setText("");
        dateOfBirthTextView.setText("");
        emailTextView.setText("");
        companyInfoTextView.setText("");
        genderTextView.setText("");
        usernameTextView.setText("");
        phoneTextView.setText("");

        customerFirstNameEditText.setText("");
        customerLastNameEditText.setText("");
        emailEditText.setText("");
        companyInfoEditText.setText("");
        usernameEditText.setText("");
        phoneEditText.setText("");
    }

    private void hideEditPanels() {
        customerNameEditText.setVisibility(View.GONE);
        emailEditText.setVisibility(View.GONE);
        companyInfoEditText.setVisibility(View.GONE);
        genderRadioGroup.setVisibility(View.GONE);
        saveBtn.setVisibility(View.GONE);

        usernameEditText.setVisibility(View.GONE);
        passwordEditText.setVisibility(View.GONE);
        confirmPasswordEditText.setVisibility(View.GONE);
        confirmPasswordLabel.setVisibility(View.GONE);
        phoneEditText.setVisibility(View.GONE);

    }

    private void initEditButtonsAction() {
        setEditButtonAction(customerNameTextView, customerNameEditText);
        setEditButtonAction(emailTextView, emailEditText);
        setEditButtonAction(phoneTextView, phoneEditText);
        setEditButtonAction(companyInfoTextView, companyInfoEditText);
        setEditButtonAction(genderTextView, genderRadioGroup);
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
                saveCustomerInfo();
                saveBtn.setEnabled(false);
                UiUtils.hideSoftKeyboard(getActivity());
            }
        });

        cbNewsLetter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                saveBtn.setVisibility(View.VISIBLE);
            }
        });

        passwordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().add(R.id.container, new PasswordChangeFragment()).addToBackStack(null).commit();
            }
        });


    }

    private void saveCustomerInfo() {
        getCustomerInfo();
        if (isValidCustomerInfo()) {
            callSaveCustomerInfoWebService();
        }
    }

    private boolean isValidCustomerInfo() {
        return true;
    }

    private void getCustomerInfo() {
        customerInfo.setFirstName(customerFirstNameEditText.getText().toString());
        customerInfo.setLastName(customerLastNameEditText.getText().toString());
        customerInfo.setEmail(emailEditText.getText().toString());
        customerInfo.setCompany(companyInfoEditText.getText().toString());
        customerInfo.setPhone(phoneEditText.getText().toString());
        if(myCalendar!=null) {
            customerInfo.setDateOfBirthYear(myCalendar.get(Calendar.YEAR));
            customerInfo.setDateOfBirthMonth(myCalendar.get(Calendar.MONTH) + 1);
            customerInfo.setDateOfBirthDay(myCalendar.get(Calendar.DAY_OF_MONTH));
        }
        if (genderMaleRadioButton.isChecked()) {
            customerInfo.setGender("M");
        } else if (genderFemaleRadioButton.isChecked()) {
            customerInfo.setGender("F");
        }
        customerInfo.setNewsletter(cbNewsLetter.isChecked());
    }


    private void setEditButtonAction(final TextView tvWithRightDrawable, final View editView) {
        tvWithRightDrawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvWithRightDrawable.setVisibility(View.GONE);
                editView.setVisibility(View.VISIBLE);
                saveBtn.setVisibility(View.VISIBLE);
            }
        });
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
            dateOfBirthTextView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            saveBtn.setVisibility(View.VISIBLE);
        }

    };


}
