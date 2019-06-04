package com.bs.dental.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bs.dental.R;
import com.bs.dental.model.ConfirmPayPalCheckoutResponse;
import com.bs.dental.model.PaypalTransaction;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.postrequest.PaypalCheckoutRequest;
import com.bs.dental.ui.fragment.Utility;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Ashraful on 12/17/2015.
 */
public class PaypalActivity extends BaseActivity {

    PaypalTransaction paypalTransaction;
    private  PayPalConfiguration config;

    private static final String TAG = "PaypalActivity";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paypalTransaction= (PaypalTransaction)getIntent().getSerializableExtra(Utility.paypalKey);
        setConfig();
        startPaypalService();
        onBuyPressed();
    }

    public void startPaypalService()
    {
        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);
    }


    private void setConfig()
    {
        config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)

                .clientId(paypalTransaction.getClientId());
    }

    public void onBuyPressed() {

        Log.e(TAG, "onBuyPressed: "+"buy press" );
        PayPalPayment payment = new PayPalPayment(new BigDecimal(paypalTransaction.getAmount()), "USD", "Amount",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, 0);
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));
                    String payid=confirm.getProofOfPayment().getPaymentId();
                   // Toast.makeText(this, "Your Order is Confirmed", Toast.LENGTH_LONG).show();

                    PaypalCheckoutRequest paypalCheckoutRequest=new PaypalCheckoutRequest();
                    paypalCheckoutRequest.setOrderId(paypalTransaction.getOrderId());
                    paypalCheckoutRequest.setPaymentId(payid);

                    RetroClient.getApi().confirmPayPalCheckout(paypalCheckoutRequest)
                            .enqueue(new CustomCB<ConfirmPayPalCheckoutResponse>());

                    Utility.setCartCounter(0);
                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }

        ///finish();
    }
    public void onevent()
    {

    }

    public void onEvent(ConfirmPayPalCheckoutResponse response) {
        if(response!=null && response.getOrderId() > 0){
            showConfirmationDialogBox(response.getOrderId());
        }
    }

    private void showConfirmationDialogBox(long orderId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PaypalActivity.this);

        builder.setMessage(getString(R.string.order_number_is) + orderId)
                .setTitle(R.string.your_order_is_confirm);

        builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}


