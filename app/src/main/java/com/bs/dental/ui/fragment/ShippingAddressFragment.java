package com.bs.dental.ui.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.model.KeyValuePair;
import com.bs.dental.model.StoreDM;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.postrequest.ValuePost;
import com.bs.dental.networking.response.ShippingAddressSaveResponse;
import com.bs.dental.networking.response.StoreAddressResponse;
import com.bs.dental.networking.response.StoreSaveResponse;
import com.bs.dental.ui.adapter.FragmentClass;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;

/**
 * Created by Ashraful on 12/7/2015.
 */
public class ShippingAddressFragment extends BillingAddressFragment {
    @InjectView(R.id.tab_shipping)
    TextView shippingTab;
    @InjectView(R.id.tab_billing)
    TextView billingTab;
    private StoreAddressResponse storeAddressResponse;
    private long storeId = 0;


    @Override
    public void callSaveAddressByFormApi(List<KeyValuePair> keyValuePairs) {
        RetroClient.getApi().saveShippingAddressByForm(keyValuePairs)
                .enqueue(new CustomCB<ShippingAddressSaveResponse>());

    }

    public void callSaveAddressFromAddressApi() {
        RetroClient.getApi().saveShippingAddressFromAddress(new ValuePost("" + addressID))
                .enqueue(new CustomCB<ShippingAddressSaveResponse>(this.getView()));
    }

    @Override
    public void setTagName() {
        storeLayout.setVisibility(View.VISIBLE);
        keyPrefixTag = "ShippingNewAddress.";
        billingTab.setBackgroundResource(R.color.primary);
        shippingTab.setBackgroundResource(R.drawable.border_bottom_primary);
        final int sizeInPixel = getActivity().getResources().
                getDimensionPixelSize(R.dimen.addresssubItempadding);
        shippingTab.setPadding(sizeInPixel, sizeInPixel, sizeInPixel, sizeInPixel);

        billingTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CheckoutStepFragment) getParentFragment()).replaceFragment(-1);
                shippingTab.setBackgroundResource(R.color.primary);
                billingTab.setBackgroundResource(R.drawable.border_bottom_primary);

                billingTab.setPadding(sizeInPixel, sizeInPixel, sizeInPixel, sizeInPixel);
            }
        });
        shippingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    generateStoreDropdownList(storeAddressResponse.getPickupPoints());
                    adddressParentLinearLayout.setVisibility(View.GONE);
                } else {
                    adddressParentLinearLayout.setVisibility(View.GONE);
                    generateDropdownList(billingAddressResponse.getExistingAddresses());
                }
            }
        });
        RetroClient.getApi().getStoreAddress().enqueue(new CustomCB<StoreAddressResponse>(this.getView()));

    }

    @Override
    protected void saveStoreData() {
        RetroClient.getApi().saveStoreAddress("" + storeId).enqueue(new CustomCB<StoreSaveResponse>(this.getView()));
    }

    public void onEvent(StoreAddressResponse storeAddressResponse) {
        this.storeAddressResponse = storeAddressResponse;
    }

    protected void generateStoreDropdownList(List<StoreDM> existingAddress) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(), R.layout.simple_spinner_item_black_color, getDropDownStoreListData(existingAddress));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        addressSelectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                storeId = storeAddressResponse.getPickupPoints().get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        addressSelectionSpinner.setAdapter(adapter);
    }

    protected List<String> getDropDownStoreListData(List<StoreDM> storeDMList) {
        List<String> addressList = new ArrayList<>();
        for (StoreDM storeDM : storeDMList) {
            String data = storeDM.getName() + " " + storeDM.getAddress() + "," + storeDM.getCity() + "," +
                    storeDM.getCountryName() + "," + storeDM.getPickupFee();
            addressList.add(data);
        }
        return addressList;
    }

    public void onEvent(ShippingAddressSaveResponse billingAddressSaveResponse) {
        if (billingAddressSaveResponse.isData() && billingAddressSaveResponse.getStatusCode() == 200) {
            callNextTab();

        }
    }

    public void onEvent(StoreSaveResponse billingAddressSaveResponse) {
        if (billingAddressSaveResponse.isData()&&billingAddressSaveResponse.getStatusCode() == 200) {
            callNextTab();

        }
    }

    public void callNextTab() {

        //======================================= convert ShippingAddress to ShippingMethod ====================================

        ((CheckoutStepFragment) getParentFragment()).replaceFragment(FragmentClass.ShippingAddress);
        //((CheckoutStepFragment) getParentFragment()).replaceFragment(FragmentClass.ShippingMethod);
    }
}
