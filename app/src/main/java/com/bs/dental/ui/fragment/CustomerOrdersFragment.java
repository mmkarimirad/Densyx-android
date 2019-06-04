package com.bs.dental.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bs.dental.R;
import com.bs.dental.event.OrderDetailsEvent;
import com.bs.dental.model.CustomerOrder;
import com.bs.dental.model.CustomerOrdersResponse;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.adapter.CustomerOrderAdapter;
import com.bs.dental.utils.Language;
import com.google.inject.Inject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import roboguice.inject.InjectView;

/**
 * Created by bs-110 on 12/15/2015.
 */
public class CustomerOrdersFragment extends BaseFragment {
    @Inject
    PreferenceService preferenceService;


    @InjectView(R.id.recycler_view_orders)
    RecyclerView mRecyclerView;

    private ArrayList<CustomerOrder> customerOrders;
    private CustomerOrderAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_orders, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        getActivity().setTitle(getString(R.string.orders));

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        customerOrders = new ArrayList<>();
        mAdapter = new CustomerOrderAdapter(getContext(), customerOrders,preferenceService);
        mRecyclerView.setAdapter(mAdapter);
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            mRecyclerView.setRotationY(180);
        }
        callGetCustomerOrdersWebservice();
    }

    public void callGetCustomerOrdersWebservice() {
        RetroClient.getApi().getCustomerOrders().enqueue(new CustomCB<CustomerOrdersResponse>(this.getView()));
    }


    public void onEvent(CustomerOrdersResponse response){
        if(response.getStatusCode() == 200) {
            customerOrders.clear();
            customerOrders.addAll(response.getOrders());
            mAdapter.notifyDataSetChanged();
            if(customerOrders.size() == 0){
                showSnack(getString(R.string.no_orders_found));
            }
        }
    }

    public void onEvent(OrderDetailsEvent event){
        CustomerOrderDetailsFragment fragment = new CustomerOrderDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("orderId", event.getId());
        fragment.setArguments(args);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null).commit();
    }

}
