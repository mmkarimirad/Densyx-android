package com.bs.dental.ui.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.model.CartProduct;
import com.bs.dental.model.CustomerAddress;
import com.bs.dental.model.OrderDetailsResponse;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.response.ReOrderResponse;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.adapter.CheckoutOrderProductAdapter;
import com.bs.dental.ui.customview.CustomLinearLayoutManager;
import com.bs.dental.ui.views.FormViews;
import com.bs.dental.utils.Language;
import com.bs.dental.utils.TextUtils;
import com.bs.dental.utils.UiUtils;
import com.google.inject.Inject;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import roboguice.inject.InjectView;

/**
 * Created by bs-110 on 12/18/2015.
 */
public class CustomerOrderDetailsFragment extends BaseFragment {
    private int _orderId;

    @InjectView(R.id.tv_subtotal)
    TextView subTotalTextView;
    @InjectView(R.id.tv_shipping)
    TextView shippingTextView;
    @InjectView(R.id.tv_tax)
    TextView taxTextView;
    @InjectView(R.id.tv_Total)
    TextView totalAmountTextView;
    @InjectView(R.id.tv_discount)
    TextView discountTextView;

    @InjectView(R.id.tr_discount)
    TableRow discountTableRow;

    @InjectView(R.id.rclv_orderProductList)
    RecyclerView orderProductList;

    @InjectView(R.id.ll_billing_address)
    LinearLayout billingAddressLinearLayout;
    @InjectView(R.id.ll_shipping_address)
    LinearLayout ShippingAddressLinearLayout;

    @InjectView(R.id.tv_order_title)
    TextView orderTitleTextView;
    @InjectView(R.id.tv_order_summery)
    TextView orderSummeryTextView;

    @InjectView(R.id.tv_payment_details)
    TextView paymentDetailsTextView;
    @InjectView(R.id.tv_shipping_details)
    TextView shippingDetailsTextView;
    @InjectView(R.id.tv_checkout_attr_info)
    TextView checkoutAttrInfoTextView;

    @InjectView(R.id.btn_reorder)
    Button btn_reorder;


    private CustomLinearLayoutManager layoutManager;

    @Inject
    PreferenceService preferenceService;

    @InjectView(R.id.ll_store_address)
    LinearLayout ll_store_address;

    @InjectView(R.id.shippingLayout)
    LinearLayout shippingLayout;

    @InjectView(R.id.storeLayouts)
    LinearLayout storeLayouts;

    @InjectView(R.id.taxKey)
    TextView taxKey;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_order_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }


        setLayoutManagerofRecyclerList();
        _orderId = getArguments().getInt("orderId");

        getActivity().setTitle(getString(R.string.order)+_orderId);

        callOrderDetailsWebService();

        btn_reorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callReOrderWebService();
            }
        });

        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            orderProductList.setRotationY(180);
        }
    }

    private void callReOrderWebService() {
        RetroClient.getApi().getReOrder(_orderId).enqueue(new CustomCB<ReOrderResponse>(this.getView()));
    }


    private void callOrderDetailsWebService() {
        RetroClient.getApi().getOrderDetails(_orderId).enqueue(new CustomCB<OrderDetailsResponse>(this.getView()));
    }

    public void onEvent(ReOrderResponse reOrderResponse){

        if (reOrderResponse.getStatusCode()==200){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new CartFragment())
                    .addToBackStack(null).commit();
        }
    }

    public void onEvent(OrderDetailsResponse response){
        String orderSummery = "";
        String paymentDetails = "";
        String shippingDetails="";

        if(response.getStatusCode() == 200){
            Log.d("OrderDetails", String.valueOf(response.getShippingStatus()));

            orderTitleTextView.setText(getString(R.string.order)+response.getId());

            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String date = "";
            try {
                Date originalDate = parser.parse(TextUtils.getNullSafeString(response.getCreatedOn()));

                Format dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
                Format timeFormat = android.text.format.DateFormat.getTimeFormat(getContext());

                String format = ((SimpleDateFormat) dateFormat).toLocalizedPattern() + " " + ((SimpleDateFormat) timeFormat).toLocalizedPattern();
                SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());

                date = formatter.format(originalDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            orderSummery+= getString(R.string.order_date) + " " + date + " \n";
            orderSummery+= getString(R.string.order_status) +" "+ TextUtils.getNullSafeString(response.getOrderStatus()) + " \n";
            orderSummery+= getString(R.string.order_total)  +" "+ TextUtils.getNullSafeString(response.getOrderTotal());

            orderSummeryTextView.setText(orderSummery);
            UiUtils.setColorInTextViewSubText(orderSummeryTextView, orderSummery, response.getOrderTotal(), R.color.primary);

            populateAddress(response.getBillingAddress(), billingAddressLinearLayout);
            if (response.isPickUpInStore()){
                storeLayouts.setVisibility(View.VISIBLE);
                shippingLayout.setVisibility(View.GONE);
                storeAddress(response.getPickupAddress(),ll_store_address);
            }else {
                storeLayouts.setVisibility(View.GONE);
                shippingLayout.setVisibility(View.VISIBLE);
                populateAddress(response.getShippingAddress(), ShippingAddressLinearLayout);
            }

            subTotalTextView.setText(TextUtils.getNullSafeString(response.getOrderSubtotal()));
            shippingTextView.setText(TextUtils.getNullSafeString(response.getOrderShipping()));
            taxTextView.setText(TextUtils.getNullSafeString(response.getTax()));
            discountTextView.setText(TextUtils.getNullSafeString(response.getOrderTotalDiscount()));
            totalAmountTextView.setText(TextUtils.getNullSafeString(response.getOrderTotal()));


            paymentDetails+= getString(R.string.payment_method) +" "+ response.getPaymentMethod() + "\n";
            paymentDetails+= getString(R.string.payment_status) +" "+ response.getPaymentMethodStatus();
            paymentDetailsTextView.setText(paymentDetails);


            shippingDetails+= getString(R.string.shipping_method)+" " + response.getShippingMethod() + "\n";
            shippingDetails+= getString(R.string.shipping_status) +" "+ response.getShippingStatus();
            shippingDetailsTextView.setText(shippingDetails);
            if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
                shippingDetailsTextView.setGravity(Gravity.RIGHT);
                paymentDetailsTextView.setGravity(Gravity.RIGHT);
                subTotalTextView.setGravity(Gravity.LEFT);
                shippingTextView.setGravity(Gravity.LEFT);
                taxTextView.setGravity(Gravity.LEFT);
                discountTextView.setGravity(Gravity.LEFT);
                totalAmountTextView.setGravity(Gravity.LEFT);
                taxKey.setGravity(Gravity.RIGHT);
            }

            if(response.getCheckoutAttributeInfo()!=null && !response.getCheckoutAttributeInfo().isEmpty())
            {
                checkoutAttrInfoTextView.setVisibility(View.VISIBLE);
                checkoutAttrInfoTextView.setText(response.getCheckoutAttributeInfo());

            }

            processCheckoutProductList(response.getItems());

        }
    }

    public void processCheckoutProductList(List<CartProduct> Items) {
        if (Items != null) {
            if (Items.size() > 0) {
                populatedDatainAdapter(Items);
            }
        }

    }

    private void populatedDatainAdapter(List<CartProduct> cartProductList) {
        CheckoutOrderProductAdapter checkoutOrderProductAdapter = new CheckoutOrderProductAdapter(getActivity(), cartProductList ,this, false,preferenceService);
        orderProductList.setAdapter(checkoutOrderProductAdapter);
        cartProductList.add(new CartProduct());
        checkoutOrderProductAdapter.notifyDataSetChanged();
    }

    private void setLayoutManagerofRecyclerList()    {
        layoutManager=new CustomLinearLayoutManager(getActivity(), CustomLinearLayoutManager.VERTICAL,false);
        orderProductList.setHasFixedSize(true);
        orderProductList.setLayoutManager(layoutManager);
    }

    private void storeAddress(CustomerAddress billingAddress, LinearLayout layout) {
        FormViews.setText(R.id.tv_addresss,""+ TextUtils.getNullSafeString(billingAddress.getAddress1()), layout);
        FormViews.setText(R.id.tv_citys, ""+ TextUtils.getNullSafeString(billingAddress.getCity()+", "+billingAddress.getZipPostalCode()), layout);
        FormViews.setText(R.id.tv_countrys, ""+ TextUtils.getNullSafeString(billingAddress.getCountryName()), layout);
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            ((TextView)layout.findViewById(R.id.tv_addresss)).setGravity(Gravity.RIGHT);
            ((TextView)layout.findViewById(R.id.tv_citys)).setGravity(Gravity.RIGHT);
            ((TextView)layout.findViewById(R.id.tv_countrys)).setGravity(Gravity.RIGHT);
        }
    }

    private void populateAddress(CustomerAddress address, LinearLayout layout) {
        ((TextView)layout.findViewById(R.id.tv_name)).setTypeface(null, Typeface.BOLD);
        ((TextView)layout.findViewById(R.id.tv_name)).setTextColor(Color.parseColor("#444444"));
        FormViews.setText(R.id.tv_name, TextUtils.getNullSafeString(address.getFirstName()) + " " + TextUtils.getNullSafeString(address.getLastName()), layout);
        FormViews.setText(R.id.tv_phone_number, TextUtils.getNullSafeString(address.getPhoneNumber()), layout);
        FormViews.setText(R.id.tv_email, TextUtils.getNullSafeString(address.getEmail()), layout);
        FormViews.setTextOrHideIfEmpty(R.id.tv_street_address,TextUtils.getNullSafeString(address.getAddress1()), layout);
        FormViews.setTextOrHideIfEmpty(R.id.tv_address2, TextUtils.getNullSafeString(address.getAddress2()), layout);
        FormViews.setTextOrHideIfEmpty(R.id.tv_city,TextUtils.getNullSafeString(address.getCity()) + "," + TextUtils.getNullSafeString(address.getStateProvinceName())+" ", layout);
        FormViews.setTextOrHideIfEmpty(R.id.tv_country, TextUtils.getNullSafeString(address.getCountryName()), layout);
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            ((TextView)layout.findViewById(R.id.tv_name)).setGravity(Gravity.RIGHT);
            ((TextView)layout.findViewById(R.id.tv_phone_number)).setGravity(Gravity.RIGHT);
            ((TextView)layout.findViewById(R.id.tv_email)).setGravity(Gravity.RIGHT);
            ((TextView)layout.findViewById(R.id.tv_street_address)).setGravity(Gravity.RIGHT);
            ((TextView)layout.findViewById(R.id.tv_address2)).setGravity(Gravity.RIGHT);
            ((TextView)layout.findViewById(R.id.tv_city)).setGravity(Gravity.RIGHT);
            ((TextView)layout.findViewById(R.id.tv_country)).setGravity(Gravity.RIGHT);
        }
    }
}
