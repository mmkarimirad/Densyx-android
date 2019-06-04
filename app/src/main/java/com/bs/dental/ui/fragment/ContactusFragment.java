package com.bs.dental.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bs.dental.R;
import com.bs.dental.service.PreferenceService;
import com.google.inject.Inject;

/**
 * Created by Ashraful on 2/11/2016.
 */
public class ContactusFragment extends BaseFragment implements View.OnClickListener {

   /* @InjectView(R.id.et_name)
    EditText nameEditText;

    @InjectView(R.id.et_email)
    EditText emailEditText;

    @InjectView(R.id.et_enquiry)
    EditText enquiryEditText;

    @InjectView(R.id.btn_email)
    Button emailBtn;

    @InjectView(R.id.btn_sms)
    Button smsBtn;
    @InjectView(R.id.btn_call)
    Button callBtn;*/

    @Inject
    PreferenceService preferenceService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_contact_us, container, false);
        Log.e("logg", "onCreateView: "+"" );

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getString(R.string.contact_us));

      /*  smsBtn.setOnClickListener(this);
        emailBtn.setOnClickListener(this);
        callBtn.setOnClickListener(this);
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)) {
            enquiryEditText.setGravity(Gravity.RIGHT);
        }*/
    }

    @Override
    public void onClick(View v) {
        int resourceId = v.getId();
      /*  if (resourceId == R.id.btn_email) {
            if (validateForm())
                sendEmail();
        } else if (resourceId == R.id.btn_sms) {
            if (validateForm())
                sendSms();
        } else if (resourceId == R.id.btn_call) {
            callUs();
        }*/
    }


    private boolean validateForm() {
        boolean isValid = true;
       /* if(!FormViews.isValidWithMark(nameEditText,"Name"))
            isValid=false;*/
       /* if(!FormViews.isValidWithMark(emailEditText,"Email"))
            isValid=false;*/
     /*   if (!FormViews.isValidWithMark(enquiryEditText, "Enquiry"))
            isValid = false;*/
        /*if(isValid)
            sendEmail();*/
        return isValid;
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"razib@brainstation-23.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.contact));
//        intent.putExtra(Intent.EXTRA_TEXT, FormViews.getTexBoxFieldValue(enquiryEditText));

        startActivity(Intent.createChooser(intent, getString(R.string.send_emil)));
    }

    private void sendSms() {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", "+8801912055164");
//        smsIntent.putExtra("sms_body", FormViews.getTexBoxFieldValue(enquiryEditText));
        startActivity(smsIntent);
    }

    private void callUs() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:+8801912055164"));
        startActivity(callIntent);
    }
}
