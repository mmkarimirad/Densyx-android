package com.bs.dental.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.event.EditAddressEvent;
import com.bs.dental.event.RemoveAddressEvent;
import com.bs.dental.model.CustomerAddress;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.utils.Language;
import com.bs.dental.utils.TextUtils;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by bs-110 on 12/4/2015.
 */
public class CustomerAddressAdapter extends RecyclerView.Adapter<CustomerAddressAdapter.ViewHolder> {
    private ArrayList<CustomerAddress> mDataset;
    private Context context;
    private PreferenceService preferenceService;
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tv_title;
        public TextView tv_address;
        public ImageView btnRemove;
        public  ImageView btnEdit;

        public ViewHolder(View v) {
            super(v);
            tv_title = (TextView) v.findViewById(R.id.tv_title);
            tv_address = (TextView) v.findViewById(R.id.tv_address);
            btnRemove = (ImageView) v.findViewById(R.id.btn_remove);
            btnEdit = (ImageView) v.findViewById(R.id.btn_edit);
        }
    }

    public void add(int position, CustomerAddress item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(CustomerAddress address) {
        int position = mDataset.indexOf(address);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public CustomerAddressAdapter(Context context,ArrayList<CustomerAddress> myDataset,PreferenceService preferenceService) {
        mDataset = myDataset;
        this.context=context;
        this.preferenceService=preferenceService;
    }

    @Override
    public CustomerAddressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_address, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final CustomerAddress address = mDataset.get(position);
        String fullAddress = context.getString(R.string.email)+": " + TextUtils.getNullSafeString(address.getEmail()) + " \n"
                +context.getString(R.string.phoneNumber)+ ": " + TextUtils.getNullSafeString(address.getPhoneNumber()) + " \n"
                +context.getString(R.string.faxNumber)+ ": " + TextUtils.getNullSafeString(address.getFaxNumber()) + " \n";
        if(TextUtils.getNullSafeString(address.getCompany()).length() > 0)
            fullAddress += address.getCompany() + " \n";
        if(TextUtils.getNullSafeString(address.getAddress1()).length() > 0)
            fullAddress += address.getAddress1() + " \n";
        if(TextUtils.getNullSafeString(address.getAddress2()).length() > 0)
            fullAddress += address. getAddress2() + " \n";
        if(TextUtils.getNullSafeString(address.getCity()).length() > 0)
            fullAddress += address.getCity() + ", ";
        if(TextUtils.getNullSafeString(address.getStateProvinceName()).length() > 0)
            fullAddress += address.getStateProvinceName() + ", ";
        if(TextUtils.getNullSafeString(address.getZipPostalCode()).length() > 0)
            fullAddress += address.getZipPostalCode() + " \n";
        if(TextUtils.getNullSafeString(address.getCountryName()).length() > 0)
            fullAddress += address.getCountryName() + " \n";

        holder.tv_title.setText(TextUtils.getNullSafeString(address.getFirstName()) + " " + TextUtils.getNullSafeString(address.getLastName()));

        holder.tv_address.setText(fullAddress);

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new RemoveAddressEvent(position, address));
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EditAddressEvent(position, address));
            }
        });
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            holder.tv_address.setRotationY(180);
            holder.tv_address.setGravity(Gravity.RIGHT);
            holder.tv_title.setRotationY(180);
            holder.tv_title.setGravity(Gravity.RIGHT);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}