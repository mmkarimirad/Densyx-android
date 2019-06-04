package com.bs.dental.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.event.OrderDetailsEvent;
import com.bs.dental.model.CustomerOrder;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.utils.Language;
import com.bs.dental.utils.TextUtils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.greenrobot.event.EventBus;

/**
 * Created by bs-110 on 12/18/2015.
 */
public class CustomerOrderAdapter extends RecyclerView.Adapter<CustomerOrderAdapter.ViewHolder> {
    private ArrayList<CustomerOrder> mDataset;
    private Context context;
    private PreferenceService preferenceService;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public View row;

        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.tv_title);
            txtFooter = (TextView) v.findViewById(R.id.tv_details);
            row = v.findViewById(R.id.row);
        }
    }

    public void add(int position, CustomerOrder item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(CustomerOrder address) {
        int position = mDataset.indexOf(address);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CustomerOrderAdapter(Context context, ArrayList<CustomerOrder> myDataset,PreferenceService preferenceService) {
        this.context = context;
        mDataset = myDataset;
        this.preferenceService=preferenceService;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CustomerOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final CustomerOrder order = mDataset.get(position);
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            holder.txtFooter.setRotationY(180);
            holder.txtHeader.setRotationY(180);
            holder.txtHeader.setGravity(Gravity.RIGHT);
            holder.txtFooter.setGravity(Gravity.RIGHT);
        }

        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String date = "";
        try {
            Date originalDate = parser.parse(TextUtils.getNullSafeString(order.getCreatedOn()));

            Format dateFormat = android.text.format.DateFormat.getDateFormat(context);
            Format timeFormat = android.text.format.DateFormat.getTimeFormat(context);

            String format = ((SimpleDateFormat) dateFormat).toLocalizedPattern() + " " + ((SimpleDateFormat) timeFormat).toLocalizedPattern();
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());

            date = formatter.format(originalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String detailsText = context.getString(R.string.order_status)+" " + order.getOrderStatus() + " \n"
                +context.getString(R.string.order_date)+ " " + date + " \n"
                + context.getString(R.string.order_total)+" " + order.getOrderTotal();

        holder.txtHeader.setText(TextUtils.getNullSafeString(context.getString(R.string.order_number)+" " + order.getId()));

        holder.txtFooter.setText(detailsText);

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new OrderDetailsEvent(order.getId()));
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}