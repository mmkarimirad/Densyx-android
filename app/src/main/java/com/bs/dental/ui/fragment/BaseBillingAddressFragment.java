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
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.bs.dental.R;
import com.bs.dental.model.AvailableCountry;
import com.bs.dental.model.AvailableState;
import com.bs.dental.model.BillingAddress;
import com.bs.dental.model.KeyValuePair;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.postrequest.ValuePost;
import com.bs.dental.networking.response.BillingAddressResponse;
import com.bs.dental.networking.response.BillingAddressSaveResponse;
import com.bs.dental.networking.response.StateListResponse;
import com.bs.dental.ui.adapter.FragmentClass;
import com.bs.dental.ui.adapter.SpinnerAdapter;
import com.bs.dental.ui.views.FormViews;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;

/**
 * Created by Ashraful on 2/2/2016.
 */
public class BaseBillingAddressFragment extends BaseFragment implements View.OnClickListener {
    @InjectView(R.id.spinner_country)
    AppCompatSpinner countrySpinner;

    @InjectView(R.id.spinner_state)
    AppCompatSpinner stateSpinner;

    @InjectView(R.id.spinner_addressSelection)
    AppCompatSpinner addressSelectionSpinner;


    @InjectView(R.id.btn_continue)
    Button continueBtn;

    @InjectView(R.id.ll_adddressParentLayout)
    LinearLayout adddressParentLinearLayout;

    String keyPrefixTag;

    protected String countryCode = "", StateProvinceCode = "";
    long addressID = 0;
    BillingAddressResponse billingAddressResponse;

    @InjectView(R.id.storeLayout)
    public LinearLayout storeLayout;

    @InjectView(R.id.shippingCheckBox)
    public CheckBox shippingCheckBox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_billing_address, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storeLayout.setVisibility(View.GONE);
        setTagName();
        continueBtn.setOnClickListener(this);
        emptyInitializationSpinner();
        callBillingAddressApi();

    }

    public void setTagName() {
        keyPrefixTag = "BillingNewAddress.";
    }

    private void emptyInitializationSpinner() {
        setEmptyAdapter(countrySpinner, getString(R.string.country));
        setEmptyAdapter(stateSpinner, getString(R.string.state));

    }

    private void setEmptyAdapter(Spinner spinner, String hint) {
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.simple_spinner_item_black_color, new ArrayList<String>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.addHint(hint);
        spinner.setAdapter(adapter);

    }

    private void callBillingAddressApi() {
        RetroClient.getApi().getBillingAddress().enqueue(new CustomCB<BillingAddressResponse>(this.getView()));
    }

    public void onEvent(BillingAddressResponse billingAddressResponse) {
        this.billingAddressResponse = billingAddressResponse;
        List<String> CountryListName = getCountryList(billingAddressResponse.getNewAddress().getAvailableCountries());
        populateDatainCountrySpinner(CountryListName, billingAddressResponse.getNewAddress().getAvailableCountries());
        generateDropdownList(billingAddressResponse.getExistingAddresses());
        setValueinFormField(billingAddressResponse.getNewAddress());
    }

    protected void generateDropdownList(final List<BillingAddress> existingAddress) {


        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(), R.layout.simple_spinner_item_black_color, getDropDownListData(existingAddress));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        addressSelectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                makeActionOnAddressSelection(existingAddress, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        addressSelectionSpinner.setAdapter(adapter);

    }

    protected void makeActionOnAddressSelection(List<BillingAddress> existingAddress, int position)
    {
        if (isNewAddressSelected(existingAddress,position)) {
            addressID = 0;
            adddressParentLinearLayout.setVisibility(View.VISIBLE);
            setValueinFormField(billingAddressResponse.getNewAddress());

        } else {
            makeActionOnSelectingExistingAddress(existingAddress,position);
        }
    }

    protected void makeActionOnSelectingExistingAddress(List<BillingAddress> existingAddress, int position)
    {
        addressID = existingAddress.get(position).getId();
        adddressParentLinearLayout.setVisibility(View.GONE);
    }

    protected boolean isNewAddressSelected(List<BillingAddress> existingAddress, int position)
    {
        if (position == existingAddress.size())
            return  true;
        else
            return false;
    }

    protected List<String> getDropDownListData(List<BillingAddress> existingAddress) {
        List<String> addressList = new ArrayList<>();
        for (BillingAddress address : existingAddress) {
            String data = address.getFirstName() + " " + address.getLastName() + "," + address.getAddress1() + "," +
                    address.getCity() + "," + address.getCountryName();
            addressList.add(data);
        }
        addressList.add(getString(R.string.new_address));
        return addressList;
    }

    private void populateDatainCountrySpinner(List<String> CountryListName, final List<AvailableCountry> availableCountries) {
        setAdapter(countrySpinner, CountryListName);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    StateProvinceCode = "";
                    countryCode = availableCountries.get(position).getValue();
                    callStateRetrievalResponseApi();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    protected void callStateRetrievalResponseApi()
    {
        RetroClient.getApi().getStates(countryCode).enqueue(new CustomCB<StateListResponse>());

    }

    private void setAdapter(Spinner spinner, List<String> stringList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item_black_color, stringList);
//        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.simple_spinner_item_black_color, stringList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private List<String> getCountryList(List<AvailableCountry> availableCountries) {
        List<String> CountryListName = new ArrayList<>();
        for (AvailableCountry country : availableCountries)
            CountryListName.add(country.getText());
        return CountryListName;

    }

    public void onEvent(final StateListResponse stateListResponse) {
        setAdapter(stateSpinner, getStatelist(stateListResponse.getData()));
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    StateProvinceCode = "" + stateListResponse.getData().get(position - 1).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public List<String> getStatelist(List<AvailableState> states) {
        List<String> stateList = new ArrayList<>();
        stateList.add("استان");
        for (AvailableState availableState : states)
            stateList.add(availableState.getName());
        return stateList;
    }

    @Override
    public void onClick(View v) {
        int resourceId = v.getId();
        if (resourceId == R.id.btn_continue)
            saveAddress();
    }


    private void saveAddress() {
        if (shippingCheckBox.isChecked()){
            saveStoreData();
        }else{
            if (addressID == 0)
                validateForm();
            else
                callSaveAddressFromAddressApi();
        }

    }

    protected void saveStoreData(){

    }


    private void validateForm() {
        boolean isValid;
        FormViews.view = getView();
        FormViews.isFormValid = true;
        FormViews.isValidWithMark(R.id.et_firstName);
        FormViews.isValidWithMark(R.id.et_lastName);
        FormViews.isValidWithMark(R.id.et_phone_number);
        FormViews.isValidWithMark(R.id.et_city);
        FormViews.isValidWithMark(R.id.et_email);
        FormViews.isValidWithMark(R.id.et_address1, getString(R.string.street_address));
        FormViews.isValidWithMark(R.id.et_zip_code);
        isValid = FormViews.isFormValid;
        if (countryCode.isEmpty()) {
            Snackbar.make(getView(), R.string.select_a_country, Snackbar.LENGTH_SHORT).show();
            isValid = false;
        } else if (StateProvinceCode.isEmpty()) {
            Snackbar.make(getView(), R.string.select_state, Snackbar.LENGTH_SHORT).show();
            isValid = false;
        }
        if (isValid) {
            List<KeyValuePair> keyValuePairs = FormViews.getForMFieldValue(adddressParentLinearLayout, keyPrefixTag);
            keyValuePairs.add(new KeyValuePair(keyPrefixTag + "CountryId", countryCode));
            keyValuePairs.add(new KeyValuePair(keyPrefixTag + "StateProvinceId", StateProvinceCode));
            callSaveAddressByFormApi(keyValuePairs);
        }

    }


    public void callSaveAddressByFormApi(List<KeyValuePair> keyValuePairs) {
        RetroClient.getApi().saveBillingAddress(keyValuePairs)
                .enqueue(new CustomCB<BillingAddressSaveResponse>(getView()));
    }

    public void callSaveAddressFromAddressApi() {
        RetroClient.getApi().saveBillingAddressFromAddress(new ValuePost("" + addressID))
                .enqueue(new CustomCB<BillingAddressSaveResponse>(this.getView()));
    }

    public void onEvent(BillingAddressSaveResponse billingAddressSaveResponse) {
        if (billingAddressSaveResponse.isData() && billingAddressSaveResponse.getStatusCode() == 200) {
            //CheckoutStepFragment.onTabChanged(FragmentClass.ShippingAddress);

            ((CheckoutStepFragment) getParentFragment()).replaceFragment(FragmentClass.BillingAddress);
            callTabContainer();


        }
    }

    public void callTabContainer() {
    }

    public void setValueinFormField(BillingAddress billingAddress) {
        FormViews.setText(R.id.et_firstName, billingAddress.getFirstName(), this.getView());
        FormViews.setText(R.id.et_lastName, billingAddress.getLastName(), this.getView());
        FormViews.setText(R.id.et_email, billingAddress.getEmail(), this.getView());

    }

}
