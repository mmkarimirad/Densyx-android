package com.bs.dental.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bs.dental.R;
import com.bs.dental.event.EditAddressEvent;
import com.bs.dental.event.RemoveAddressEvent;
import com.bs.dental.model.CustomerAddress;
import com.bs.dental.model.CustomerAddressResponse;
import com.bs.dental.model.RemoveCustomerAddressResponse;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.adapter.CustomerAddressAdapter;
import com.bs.dental.utils.Language;
import com.google.gson.Gson;
import com.google.inject.Inject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import roboguice.inject.InjectView;

/**
 * Created by bs-110 on 12/15/2015.
 */
public class CustomerAddressesFragment extends BaseFragment implements View.OnClickListener {
    @Inject
    PreferenceService preferenceService;

    @InjectView(R.id.btn_add)
    Button addAddressButton;

    @InjectView(R.id.recycler_view_address)
    RecyclerView mRecyclerView;

    private ArrayList<CustomerAddress> customerAddresses;
    private CustomerAddressAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_addresses, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        getActivity().setTitle(getString(R.string.addresses));
        addAddressButton.setOnClickListener(this);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        customerAddresses = new ArrayList<>();
        mAdapter = new CustomerAddressAdapter(getActivity(),customerAddresses,preferenceService);
        mRecyclerView.setAdapter(mAdapter);

        callGetAddressesWebservice();
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            mRecyclerView.setRotationY(180);
        }
    }

    public void callGetAddressesWebservice() {
        RetroClient.getApi().getCustomerAddresses().enqueue(new CustomCB<CustomerAddressResponse>(this.getView()));
    }

    public void callRemoveAddressWebservice(int addressId) {
        RetroClient.getApi().removeCustomerAddresses(addressId)
                .enqueue(new CustomCB<RemoveCustomerAddressResponse>(this.getView()));
    }

    public void onEvent(CustomerAddressResponse response){
        if(response.getStatusCode() == 200) {
            //Log.d("ADDRESSES", String.valueOf(response.getExistingAddresses()));
            customerAddresses.clear();
            customerAddresses.addAll(response.getExistingAddresses());
            mAdapter.notifyDataSetChanged();

            if(customerAddresses.size() == 0){
                showSnack(getString(R.string.no_address_found));
            }
        }
    }

    public void onEvent(RemoveCustomerAddressResponse response){
        String message = "";
        if(response.getStatusCode() == 200) {
            message = getString(R.string.address_remove_succssfully);
        } else {
            message = getString(R.string.error_removing_address);
        }

        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void onEvent(EditAddressEvent event){

        Fragment editCustomerFragment= new CustomerAddAddressFragment();
        Bundle args = new Bundle();
        args.putInt("index", event.getIndex()+1);
        args.putString("addressJson", new Gson().toJson(event.getAddress()));
        editCustomerFragment.setArguments(args);

        getFragmentManager().beginTransaction().replace(R.id.container,
                editCustomerFragment)
                .addToBackStack(null).commit();
    }

    public void onEvent(RemoveAddressEvent event){
        showRemoveAddressConfirmationDialog(event.getAddress());
    }

    private void showRemoveAddressConfirmationDialog(final CustomerAddress address){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.are_you_sure_delete_address)
                .setTitle(R.string.delete_address);

        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mAdapter.remove(address);
                callRemoveAddressWebservice(Integer.parseInt(address.getId()));
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_add){
            getFragmentManager().beginTransaction().replace(R.id.container,
                    new CustomerAddAddressFragment())
                    .addToBackStack(null).commit();
        }
    }
}
