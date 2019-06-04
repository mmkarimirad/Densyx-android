package com.bs.dental.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bs.dental.R;
import com.bs.dental.model.AddAddressResponse;
import com.bs.dental.model.AvailableCountry;
import com.bs.dental.model.AvailableState;
import com.bs.dental.model.CustomerAddress;
import com.bs.dental.model.EditAddressResponse;
import com.bs.dental.model.KeyValuePair;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.response.BillingAddressResponse;
import com.bs.dental.networking.response.StateListResponse;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.utils.TextUtils;
import com.bs.dental.utils.UiUtils;
import com.google.gson.Gson;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;
import roboguice.inject.InjectView;

/**
 * Created by bs-110 on 12/15/2015.
 */
public class CustomerAddAddressFragment extends BaseFragment implements View.OnClickListener{
    @Inject
    PreferenceService preferenceService;
    CustomerAddress address;
    @InjectView(R.id.et_first_name)
    EditText firstNameEditText;
    @InjectView(R.id.et_last_name)
    EditText lastNameEditText;
    @InjectView(R.id.et_email)
    EditText emailEditText;
    @InjectView(R.id.et_city)
    EditText cityEditText;
    @InjectView(R.id.et_address1)
    EditText address1EditText;
    @InjectView(R.id.et_address2)
    EditText address2EditText;
    @InjectView(R.id.et_zip_code)
    EditText zipOrPostalCodeEditText;
    @InjectView(R.id.et_fax_number)
    EditText faxNumberEditText;
    @InjectView(R.id.et_phone_number)
    EditText phoneNumberEditText;
    @InjectView(R.id.et_company)
    EditText companyEditText;
    @InjectView(R.id.btn_save)
    Button submitButton;

    @InjectView(R.id.sp_country)
    AppCompatSpinner countrySpinner;
    @InjectView(R.id.spinner_state)
    AppCompatSpinner stateSpinner;

    String countryCode;
    String StateProvinceCode="";

    private boolean isEdit = false;
    private int addressIndex;

    HashMap<String, String> addressHashMap;
    BillingAddressResponse billingAddressResponse;
    List<KeyValuePair> keyValuePairs;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_add_address, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        getActivity().setTitle(getString(R.string.add_address));
        callBillingAddressApi();

        addressHashMap = new HashMap();

        Bundle args = getArguments();
        if(args!=null){
            isEdit = true;
            getActivity().setTitle(getString(R.string.edit_address));
            addressIndex = args.getInt("index");
            String addressJson = args.getString("addressJson");
            address = new Gson().fromJson(addressJson , CustomerAddress.class);
            firstNameEditText.setText(TextUtils.getNullSafeString(address.getFirstName()));
            lastNameEditText.setText(TextUtils.getNullSafeString(address.getLastName()));
            emailEditText.setText(TextUtils.getNullSafeString(address.getEmail()));
            companyEditText.setText(TextUtils.getNullSafeString(address.getCompany()));
            cityEditText.setText(TextUtils.getNullSafeString(address.getCity()));
            address1EditText.setText(TextUtils.getNullSafeString(address.getAddress1()));
            address2EditText.setText(TextUtils.getNullSafeString(address.getAddress2()));
            phoneNumberEditText.setText(TextUtils.getNullSafeString(address.getPhoneNumber()));
            zipOrPostalCodeEditText.setText(TextUtils.getNullSafeString(address.getZipPostalCode()));
            faxNumberEditText.setText(TextUtils.getNullSafeString(address.getFaxNumber()));
        }
        emptyInitializationSpinner();
        submitButton.setOnClickListener(this);


    }

    public void onEvent(EditAddressResponse response){
        submitButton.setEnabled(true);
        if(response.getStatusCode() == 400){
            String errors = getString(R.string.error_adding_address)+"\n";
            if(response.getErrorList().length > 0){
                for(int i=0; i< response.getErrorList().length; i++ ){
                    errors += "  "+(i+1) + ": " + response.getErrorList()[i] + " \n";
                }
                Toast.makeText(getActivity(), errors, Toast.LENGTH_LONG).show();
            }
        } else if(response.getStatusCode() == 200){
            Toast.makeText(getActivity(), R.string.address_update_succss, Toast.LENGTH_SHORT).show();
        }
    }

    public void onEvent(AddAddressResponse response){
        if(response.getStatusCode() == 400){
            String errors = getString(R.string.error_adding_address)+"\n";
            if(response.getErrorList().length > 0){
                for(int i=0; i< response.getErrorList().length; i++ ){
                    errors += "  "+(i+1) + ": " + response.getErrorList()[i] + " \n";
                }
                Toast.makeText(getActivity(), errors, Toast.LENGTH_LONG).show();
            }
        } else if(response.getStatusCode() == 200){
            Toast.makeText(getActivity(), R.string.address_added_success, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_save){
            addOrUpdateAddress();
            //submitButton.setEnabled(false);
            UiUtils.hideSoftKeyboard(getActivity());
        }
    }

    private void addOrUpdateAddress() {
        if(validateForm()) {
            if (isEdit) {
                callEditAddressesWebservice();
            } else {
                callAddAddressWebservice();
            }
        }
    }

    private void callAddAddressWebservice() {
        RetroClient.getApi().addAddress(keyValuePairs).enqueue(new CustomCB<AddAddressResponse>(this.getView()));
    }

    private void callEditAddressesWebservice() {
        RetroClient.getApi().editAddress(Integer.parseInt(address.getId()), keyValuePairs)
                .enqueue(new CustomCB<EditAddressResponse>(this.getView()));
    }


    private  String getString(EditText et){
        return  et.getText().toString();
    }

    private void callBillingAddressApi(){
        RetroClient.getApi().getBillingAddress().enqueue(new CustomCB<BillingAddressResponse>(this.getView()));
    }

    public void onEvent(BillingAddressResponse billingAddressResponse){
        this.billingAddressResponse=billingAddressResponse;
        List<String> CountryListName=getCountryList(billingAddressResponse.getNewAddress().getAvailableCountries());
        populateDatainCountrySpinner(CountryListName, billingAddressResponse.getNewAddress().getAvailableCountries());
    }



    private void  populateDatainCountrySpinner( List<String>CountryListName, final List<AvailableCountry> availableCountries){

        setAdapter(countrySpinner, CountryListName);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    StateProvinceCode = "";
                    countryCode = availableCountries.get(position).getValue();
                    RetroClient.getApi().getStates(countryCode).enqueue(new CustomCB<StateListResponse>());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(isEdit && address.getCountryId() != null){
            int i = 0;
            for(AvailableCountry aCountry: availableCountries){
                if(aCountry.getValue().equalsIgnoreCase(address.getCountryId())){
                    countrySpinner.setSelection(i);
                    break;
                }
                i++;
            }
        }

    }

    private void setAdapter(AppCompatSpinner spinner, List<String>stringList){
//        com.bs.ecommerce.ui.adapter.SpinnerAdapter adapter=new com.bs.ecommerce.ui.adapter.SpinnerAdapter(getActivity(), R.layout.simple_spinner_item_black_color,stringList);
        ArrayAdapter categorySpinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.simple_spinner_item_black_color, stringList);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(categorySpinnerAdapter);
    }

    private List<String> getCountryList(List<AvailableCountry> availableCountries){
        List<String>CountryListName=new ArrayList<>();
        for(AvailableCountry country:availableCountries)
            CountryListName.add(country.getText());
        return CountryListName;

    }

    public void onEvent(final StateListResponse stateListResponse){
        setAdapter(stateSpinner, getStateList(stateListResponse.getData()));
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    StateProvinceCode=""+stateListResponse.getData().get(position-1).getId();
                //Log.d("test", stateListResponse.getData().get(position-1).getId()+"");
                }else{
                    StateProvinceCode="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(isEdit && address.getStateProvinceId() != null){
            int stateId = Integer.parseInt(address.getStateProvinceId());
            //stateSpinner.setSelection(3);
            for(int i =0; i<= stateListResponse.getData().size(); i++){
                //Log.d("test", stateListResponse.getData().get(i).getId() + " " +stateId);
                if(stateListResponse.getData().get(i).getId() == stateId){
                    stateSpinner.setSelection(i+1);
                    break;
                }
            }
        }
    }

    public List<String> getStateList(List<AvailableState> states)
    {
        List<String>stateList=new ArrayList<>();
        stateList.add(getString(R.string.select_state));
//        if(states.size() == 1 && states.get(0).getId() ==0){
//            StateProvinceCode="0";
//        } else {
//            StateProvinceCode="";
//        }
        for(AvailableState availableState:states)
            stateList.add(availableState.getName());
        return stateList;
    }

    private boolean validateForm() {
        boolean isValid = true;
        if(countryCode == null || countryCode.isEmpty()){
            Snackbar.make(getView(),getString(R.string.select_a_country), Snackbar.LENGTH_SHORT).show();
            isValid=false;
        }
        else if(StateProvinceCode == null || StateProvinceCode.isEmpty()) {
            Snackbar.make(getView(),getString(R.string.select_state), Snackbar.LENGTH_SHORT).show();
            isValid=false;
        }
        if(isValid)
        {
            keyValuePairs = new ArrayList<>();
            keyValuePairs.add(new KeyValuePair("Address.CountryId",countryCode));
            keyValuePairs.add(new KeyValuePair( "Address.StateProvinceId",StateProvinceCode));
            keyValuePairs.add(new KeyValuePair( "Address.FirstName",getString(firstNameEditText)));
            keyValuePairs.add(new KeyValuePair( "Address.LastName",getString(lastNameEditText)));
            keyValuePairs.add(new KeyValuePair( "Address.Email",getString(emailEditText)));
            keyValuePairs.add(new KeyValuePair( "Address.Company",getString(companyEditText)));
            keyValuePairs.add(new KeyValuePair("Address.City",getString(cityEditText)));
            keyValuePairs.add(new KeyValuePair( "Address.Address1",getString(address1EditText)));
            keyValuePairs.add(new KeyValuePair("Address.Address2",getString(address2EditText)));
            keyValuePairs.add(new KeyValuePair( "Address.ZipPostalCode",getString(zipOrPostalCodeEditText)));
            keyValuePairs.add(new KeyValuePair( "Address.PhoneNumber",getString(phoneNumberEditText)));
            keyValuePairs.add(new KeyValuePair( "Address.FaxNumber",getString(faxNumberEditText)));

        }
        return isValid;
    }

    private void emptyInitializationSpinner() {
        setEmptyAdapter(countrySpinner, getString(R.string.select_state));
        setEmptyAdapter(stateSpinner, getString(R.string.select_state));
    }

    private void setEmptyAdapter(Spinner spinner,String hint){
        List<String > emtList=new ArrayList<>();
        emtList.add(hint);
        ArrayAdapter empAdapter = new ArrayAdapter<>(getContext(), R.layout.simple_spinner_item_black_color, emtList);
        empAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(empAdapter);
    }

}
