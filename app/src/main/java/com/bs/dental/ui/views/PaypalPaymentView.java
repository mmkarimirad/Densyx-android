package com.bs.dental.ui.views;

import android.app.Activity;
import android.content.Intent;

import com.bs.dental.model.PaypalTransaction;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;

/**
 * Created by Ashraful on 12/17/2015.
 */
public class PaypalPaymentView {
    public Activity activity;
    public PaypalTransaction paypalTransaction;

    public PaypalPaymentView(Activity activity, PaypalTransaction paypalTransaction) {
        this.activity = activity;
        this.paypalTransaction = paypalTransaction;

    }

    public void startPaypalPayment() {

    }

    public void startPaypalService() {
        Intent intent = new Intent(activity, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        activity.startService(intent);
    }


    private PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)

            .clientId(paypalTransaction.getAmount());


    public void onBuyPressed() {


        PayPalPayment payment = new PayPalPayment(new BigDecimal(paypalTransaction.getAmount()), "USD", "Amount",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(activity, PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        activity.startActivityForResult(intent, 0);
    }
}

   /* @Override
    public void onDestroy() {
        activity.stopService(new Intent(activity, PayPalService.class));
        super.onDestroy();
    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));
                    Log.i("paymentInfo",""+confirm.toJSONObject().toString());

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
    }
}*/


