package com.bs.dental.ui.fragment;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import com.bs.dental.Constants;
import com.bs.dental.R;
import com.bs.dental.model.BillingAddress;
import com.bs.dental.model.CartProduct;
import com.bs.dental.model.OrderReviewData;
import com.bs.dental.model.PayPal;
import com.bs.dental.model.PaypalTransaction;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.NetworkUtil;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.response.CartProductListResponse;
import com.bs.dental.networking.response.CheckoutConfirmResponse;
import com.bs.dental.networking.response.CheckoutOrderSummaryResponse;
import com.bs.dental.networking.response.OrderTotalResponse;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.activity.AuthorizeDotNetPaymentMethodActivity;
import com.bs.dental.ui.activity.PaypalActivity;
import com.bs.dental.ui.adapter.CheckoutOrderProductAdapter;
import com.bs.dental.ui.customview.CustomLinearLayoutManager;
import com.bs.dental.ui.customview.SimpleDividerItemDecoration;
import com.bs.dental.ui.views.FormViews;
import com.bs.dental.utils.Language;
import com.bs.dental.utils.TextUtils;
import com.google.gson.Gson;
import com.google.inject.Inject;

import java.util.List;

import roboguice.inject.InjectView;

/**
 * Created by Ashraful on 12/7/2015.
 */
public class ConfirmOrderFragment extends BaseFragment implements View.OnClickListener {
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

    @InjectView(R.id.btn_continue)
    Button confirmButton;

    @InjectView(R.id.tr_discount)
    TableRow discountTableRow;

    @InjectView(R.id.rclv_chekoutProductList)
    RecyclerView checkoutProductList;

    @InjectView(R.id.ll_billing_address)
    LinearLayout billingAddressLinearLayout;
    @InjectView(R.id.ll_shipping_address)
    LinearLayout ll_shipping_address;

    @InjectView(R.id.ll_store_address)
    LinearLayout ll_store_address;

    @InjectView(R.id.shippingLayout)
    LinearLayout shippingLayout;

    @InjectView(R.id.storeLayouts)
    LinearLayout storeLayouts;

    @InjectView(R.id.taxKey)
    TextView taxKey;

    @Inject
    PreferenceService preferenceService;

    private CustomLinearLayoutManager layoutManager;
    private CheckoutOrderSummaryResponse checkoutOrderSummaryResponse;
    private boolean isOpenPaymentActivity =false;

//    @InjectView(R.id.tv_name)
//    TextView tv_name;
//
//    @InjectView(R.id.tv_phone_number)
//    TextView tv_phone_number;
//
//
//    @InjectView(R.id.tv_email)
//    TextView tv_email;
//
//
//    @InjectView(R.id.tv_street_address)
//    TextView tv_street_address;
//
//    @InjectView(R.id.tv_address2)
//    TextView tv_address2;
//
//    @InjectView(R.id.tv_city)
//    TextView tv_city;
//    @InjectView(R.id.tv_country)
//    TextView tv_country;

    @InjectView(R.id.tv_addresss)
    TextView tv_addresss;

    @InjectView(R.id.tv_citys)
    TextView tv_citys;

    @InjectView(R.id.tv_countrys)
    TextView tv_countrys;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirm_order, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLayoutManagerofRecyclerList();
        confirmButton.setOnClickListener(this);
        callCheckoutOrderSummaryWebservice();
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            checkoutProductList.setRotationY(180);
        }
    }
    private void callCheckoutOrderSummaryWebservice()
    {
        RetroClient.getApi().getCheckoutOrderSummary().enqueue(new CustomCB<CheckoutOrderSummaryResponse>(this.getView()));
    }

    private void setLayoutManagerofRecyclerList()
    {
        layoutManager=new CustomLinearLayoutManager(getActivity(), CustomLinearLayoutManager.VERTICAL,false);
        checkoutProductList.setHasFixedSize(true);
        checkoutProductList.setLayoutManager(layoutManager);
        checkoutProductList.setNestedScrollingEnabled(false);
    }
    public void onEvent(CheckoutOrderSummaryResponse checkoutOrderSummaryResponse) {
        this.checkoutOrderSummaryResponse=checkoutOrderSummaryResponse;
        processCheckoutProductList(checkoutOrderSummaryResponse.getShoppingCartModel());
        processCheckoutOrderTotal(checkoutOrderSummaryResponse.getOrderTotalModel());
        processCheckoutBillingAddress(checkoutOrderSummaryResponse.getShoppingCartModel().getOrderReviewData().getBillingAddress());
        processCheckoutShippingAddress(checkoutOrderSummaryResponse.getShoppingCartModel().getOrderReviewData());

    }


    public void processCheckoutProductList(CartProductListResponse cartProductListResponse) {
        if (cartProductListResponse != null && cartProductListResponse.getItems() != null) {
            if (cartProductListResponse.getItems().size() > 0) {

                populatedDatainAdapter(cartProductListResponse.getItems());
            } else {
                getFragmentManager().popBackStack();
                Snackbar.make(getView(), R.string.not_item_to_process, Snackbar.LENGTH_SHORT).show();
            }
        }

    }

    private void populatedDatainAdapter(List<CartProduct> cartProductList) {
        CheckoutOrderProductAdapter checkoutOrderProductAdapter = new CheckoutOrderProductAdapter(getActivity(), cartProductList, this,preferenceService);
        checkoutProductList.setAdapter(checkoutOrderProductAdapter);
        checkoutProductList.addItemDecoration(new
                SimpleDividerItemDecoration(getActivity()));
    }

    private void processCheckoutOrderTotal(OrderTotalResponse orderTotalModel) {

        if (orderTotalModel.getStatusCode() == 200) {
            populateDataInOrderTotalLayout(orderTotalModel);
        }
    }

    private void populateDataInOrderTotalLayout(OrderTotalResponse orderTotalRespons) {

        subTotalTextView.setText(orderTotalRespons.getSubTotal());
        shippingTextView.setText(orderTotalRespons.getShipping());
        taxTextView.setText(orderTotalRespons.getTax());
        totalAmountTextView.setText(orderTotalRespons.getOrderTotal());

        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            subTotalTextView.setGravity(Gravity.LEFT);
            shippingTextView.setGravity(Gravity.LEFT);
            taxTextView.setGravity(Gravity.LEFT);
            discountTextView.setGravity(Gravity.LEFT);
            totalAmountTextView.setGravity(Gravity.LEFT);
            taxKey.setGravity(Gravity.RIGHT);
        }

        if (orderTotalRespons.getOrderTotalDiscount() != null) {
            discountTableRow.setVisibility(View.VISIBLE);
            discountTextView.setText(orderTotalRespons.getOrderTotalDiscount());
            discountTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else
            discountTableRow.setVisibility(View.GONE);

    }

    private void processCheckoutBillingAddress(BillingAddress billingAddress) {
        populateAddres(billingAddress, billingAddressLinearLayout);
    }

    private void processCheckoutShippingAddress(OrderReviewData orderReviewData) {
        if (orderReviewData.isSelectedPickUpInStore()){
            storeLayouts.setVisibility(View.VISIBLE);
            shippingLayout.setVisibility(View.GONE);
            populateStoreAddres(orderReviewData.getPickupAddress(), ll_store_address);
        }else {
            storeLayouts.setVisibility(View.GONE);
            shippingLayout.setVisibility(View.VISIBLE);
            ll_shipping_address.setGravity(Gravity.RIGHT);
            populateAddres(orderReviewData.getShippingAddress(), ll_shipping_address);
        }


    }

    private void populateStoreAddres(BillingAddress billingAddress, LinearLayout layout) {
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            tv_addresss.setGravity(Gravity.RIGHT);
            tv_citys.setGravity(Gravity.RIGHT);
            tv_countrys.setGravity(Gravity.RIGHT);
        }
        FormViews.setText(tv_addresss.getId(),""+ TextUtils.getNullSafeString(billingAddress.getAddress1()), layout);
        FormViews.setText(tv_citys.getId(), ""+ TextUtils.getNullSafeString(billingAddress.getCity()+", "+billingAddress.getZipPostalCode()), layout);

        FormViews.setText(tv_countrys.getId(), ""+ TextUtils.getNullSafeString(billingAddress.getCountryName()), layout);

    }

    private void populateAddres(BillingAddress billingAddress, LinearLayout layout) {

        TextView tv_name=(TextView)getActivity().findViewById(R.id.tv_name);
        TextView tv_phone_number=(TextView)getActivity().findViewById(R.id.tv_phone_number);
        TextView tv_email=(TextView)getActivity().findViewById(R.id.tv_email);
        TextView tv_street_address=(TextView)getActivity().findViewById(R.id.tv_street_address);
        TextView tv_address2=(TextView)getActivity().findViewById(R.id.tv_address2);
        TextView tv_city=(TextView)getActivity().findViewById(R.id.tv_city);
        TextView tv_country=(TextView)getActivity().findViewById(R.id.tv_country);

        FormViews.setText(tv_name.getId(), TextUtils.getNullSafeString(billingAddress.getFirstName()) + " " + TextUtils.getNullSafeString(billingAddress.getLastName()), layout);

        FormViews.setText(tv_phone_number.getId(), TextUtils.getNullSafeString(billingAddress.getPhoneNumber()), layout);

        FormViews.setText(tv_email.getId(), TextUtils.getNullSafeString(billingAddress.getEmail()), layout);

        FormViews.setText(tv_street_address.getId(), TextUtils.getNullSafeString(billingAddress.getAddress1()), layout);

        FormViews.setText(tv_address2.getId(), TextUtils.getNullSafeString(billingAddress.getAddress2()), layout);

        if( billingAddress.getStateProvinceName()!=null)
        FormViews.setText(tv_city.getId(), TextUtils.getNullSafeString(billingAddress.getCity()) + "," + TextUtils.getNullSafeString(billingAddress.getStateProvinceName()),
                layout);
        else
            FormViews.setText(tv_city.getId(), TextUtils.getNullSafeString(billingAddress.getCity()),layout);

        FormViews.setText(tv_country.getId(), TextUtils.getNullSafeString(billingAddress.getCountryName()), layout);
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            tv_name.setGravity(Gravity.RIGHT);
            tv_phone_number.setGravity(Gravity.RIGHT);
            tv_email.setGravity(Gravity.RIGHT);
            tv_street_address.setGravity(Gravity.RIGHT);
            tv_address2.setGravity(Gravity.RIGHT);
            tv_city.setGravity(Gravity.RIGHT);
            tv_country.setGravity(Gravity.RIGHT);
        }
    }

    @Override
    public void onClick(View v) {
        RetroClient.getApi().confirmCheckout().enqueue(new CustomCB<CheckoutConfirmResponse>(this.getView()));

    }

    public void onEvent(CheckoutConfirmResponse confirmResponse)
    {
        Log.d("checkoutResponse", String.valueOf(new Gson().toJson(confirmResponse)));

        if(confirmResponse.getStatusCode() == 400){
            String errors = "";
            if (confirmResponse.getErrorList().length > 0) {
                for (int i = 0; i < confirmResponse.getErrorList().length; i++) {
                    errors += "  " + (i + 1) + ": " + confirmResponse.getErrorList()[i] + " \n";
                }
               showToast(errors);
            }
        }
        else if(confirmResponse.getStatusCode()==200 && confirmResponse.getOrderId()!=0) {
            if (confirmResponse.getPaymentType() == 1) {
                showConfirmationDialogBox(confirmResponse.getOrderId());
                Utility.setCartCounter(0);
            } else if (confirmResponse.getPaymentType() == 2 && confirmResponse.getPayPal() != null) {
                Intent paypalIntent = new Intent(getActivity(), PaypalActivity.class);
                paypalIntent.putExtra(Utility.paypalKey,
                        getPaypalTransactionObject(confirmResponse.getPayPal(), "" + confirmResponse.getOrderId()));
                isOpenPaymentActivity = true;
                startActivityForResult(paypalIntent, 100);

            } else if (confirmResponse.getPaymentType() == 3) {
                Intent authDotNetIntent = new Intent(getActivity(), AuthorizeDotNetPaymentMethodActivity.class);
                authDotNetIntent.putExtra(Utility.orderIdKey, ""+confirmResponse.getOrderId());
                isOpenPaymentActivity = true;
                startActivityForResult(authDotNetIntent, 101);
            }
            else if (confirmResponse.getPaymentType() == 4) {
                isOpenPaymentActivity = true;
                /*Intent authDotNetIntent = new Intent(getActivity(), CyberSourceActivity.class);
                startActivityForResult(authDotNetIntent, 102);*/

                goToWebsite();
            }
        }

    }

    public PaypalTransaction getPaypalTransactionObject(PayPal payPal, String orderId)
    {
        PaypalTransaction paypalTransaction=new PaypalTransaction();
        String total=checkoutOrderSummaryResponse.getOrderTotalModel().getOrderTotal().substring(1);
        if (total.contains(",")){
            total= total.replace(",","");
        }
        paypalTransaction.setAmount(total);
        paypalTransaction.setClientId(payPal.getClientId());
        paypalTransaction.setCurrencyCode("usd");
        paypalTransaction.setOrderId(orderId);
        return paypalTransaction;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isOpenPaymentActivity) {
            isOpenPaymentActivity = false;
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    private void showConfirmationDialogBox(long orderId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(getString(R.string.order_number_is)+" " + orderId)
                .setTitle(getString(R.string.your_order_is_confirm));

        builder.setNeutralButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void goToWebsite() {
        try {
            String url= Constants.BASE_URL+"/checkout/OpcCompleteRedirectionPayment";
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

            NetworkUtil.getHeaders();

            Bundle bundle = new Bundle();
            bundle.putString("NST", NetworkUtil.getHeaders().get("NST"));
            bundle.putString("DeviceId", NetworkUtil.getHeaders().get("DeviceId"));
            bundle.putString("Token", NetworkUtil.getHeaders().get("Token"));
            myIntent.putExtra(Browser.EXTRA_HEADERS, bundle);

            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "لطفا یک مرورگر نصب کنید",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}
