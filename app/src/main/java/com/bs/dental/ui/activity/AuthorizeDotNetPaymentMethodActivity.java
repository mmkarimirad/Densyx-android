package com.bs.dental.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bs.dental.R;
import com.bs.dental.model.AuthorizePayment;
import com.bs.dental.model.ConfirmAutorizeDotNetCheckoutResponse;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.ui.adapter.SpinnerAdapter;
import com.bs.dental.ui.fragment.Utility;
import com.bs.dental.utils.EditTextUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.greenrobot.event.EventBus;
import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by bs-110 on 1/7/2016.
 */
public class AuthorizeDotNetPaymentMethodActivity extends RoboActionBarActivity implements View.OnClickListener{

    AuthorizePayment authorizePayment;

    @InjectView(R.id.spinner_credit_card)
    Spinner creditCardTypeSpinner;
    @InjectView(R.id.spinner_month)
    Spinner monthSpinner;
    @InjectView(R.id.spinner_year)
    Spinner yearSpinner;
    @InjectView(R.id.et_card_holder_name)
    EditText nameEditText;
    @InjectView(R.id.et_card_number)
    EditText cardNumberEditText;
    @InjectView(R.id.et_card_code)
    EditText cardCodeEditText;
    @InjectView(R.id.btn_continue)
    Button continueBtn;

    List<String> months = new ArrayList<>();
    List<String> years = new ArrayList<>();
    List<String> cards = new ArrayList<>();

    int orderId = 0;

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorized_dot_net_form_activity);

        try {
            orderId = Integer.parseInt(getIntent().getStringExtra(Utility.orderIdKey));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        setMonthSpinner();
        continueBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_continue:
                if(orderId>0) {
                    performSubmit();
                }
                break;
        }
    }

    private void performSubmit() {
        getFormData();
        callWebService();
    }

    public void getFormData() {
        EditTextUtils eu = new EditTextUtils();
        authorizePayment = new AuthorizePayment();
        if(eu.isValidString(nameEditText)) {
            authorizePayment.setCardHolderName(eu.getString(nameEditText));
        }
        authorizePayment.setCreditCardNumber(eu.getString(cardNumberEditText));
        authorizePayment.setCreditCardCvv2(eu.getString(cardCodeEditText));
        authorizePayment.setCreditCardExpireMonth(Integer.parseInt((String) monthSpinner.getSelectedItem()));
        authorizePayment.setCreditCardExpireYear(Integer.parseInt((String) yearSpinner.getSelectedItem()));
        authorizePayment.setOrderId(orderId);

        Log.d("asutData", new Gson().toJson(authorizePayment));

    }

    public void callWebService(){
        RetroClient.getApi().checkAuthorizePayment(authorizePayment)
                .enqueue(new CustomCB<ConfirmAutorizeDotNetCheckoutResponse>());
    }

    private void setMonthSpinner(){

        cards.add("Visa");
        cards.add("Master Card");
        cards.add("Discover");
        cards.add("Amex");
        creditCardTypeSpinner.setAdapter(new SpinnerAdapter(this, android.R.layout.simple_spinner_item, cards));

        for(int i=1;i<=12;i++){
            months.add(i+"");
        }
        monthSpinner.setAdapter(new SpinnerAdapter(this, android.R.layout.simple_spinner_item, months));


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        for(int i=year;i<year+15;i++){
            years.add(i+"");
        }

        yearSpinner.setAdapter(new SpinnerAdapter(this, android.R.layout.simple_spinner_item, years));
    }


    public void onEvent(ConfirmAutorizeDotNetCheckoutResponse response) {
        Log.d("Error in payment", new Gson().toJson(response.toString()));
        if(response.getStatusCode() == 400){
            String errors = "Error in payment:\n";
            if (response.getErrorList().length > 0) {
                for (int i = 0; i < response.getErrorList().length; i++) {
                    errors += "  " + (i + 1) + ": " + response.getErrorList()[i] + " \n";
                }
                Toast.makeText(AuthorizeDotNetPaymentMethodActivity.this, errors, Toast.LENGTH_LONG).show();
            }
        }
        else {
            showConfirmationDialogBox(response.getOrderId());
        }
    }

    private void showConfirmationDialogBox(long orderId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AuthorizeDotNetPaymentMethodActivity.this);

        builder.setMessage(getString(R.string.order_number_is) + orderId)
                .setTitle(R.string.your_order_is_confirm);

        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
