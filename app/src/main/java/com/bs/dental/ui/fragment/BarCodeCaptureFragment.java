package com.bs.dental.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.barcode.BarcodeCaptureActivity;
import com.bs.dental.model.ProductModel;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import roboguice.inject.InjectView;

/**
 * Created by Ashraful on 3/24/2016.
 */
public class BarCodeCaptureFragment extends BaseFragment {

    @InjectView(R.id.cb_auto_focus)
    private CompoundButton autoFocus;

    @InjectView(R.id.cb_use_flash)
    private CompoundButton useFlash;

    @InjectView(R.id.tv_barcode_value)
    private TextView barcodeValue;

    @InjectView(R.id.tv_status_message)
    private TextView statusMessage;

    @InjectView(R.id.read_barcode)
    Button readBarcodeBtn;

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_barcode_capture,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        readBarcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReading();
            }
        });
    }

    public void startReading()
    {
        Intent intent = new Intent(getActivity(), BarcodeCaptureActivity.class);
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, autoFocus.isChecked());
        intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());

        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == RC_BARCODE_CAPTURE) {
                if (resultCode == CommonStatusCodes.SUCCESS) {
                    if (data != null) {
                        Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                        statusMessage.setText(R.string.barcode_success);
                        barcodeValue.setText(barcode.displayValue);
                        ProductModel productModel=new ProductModel();
                        productModel.setId(1);
                        BarcodeProductDetailsFragment.productModel=productModel;
                        gotoNewFragment(new BarcodeProductDetailsFragment());
                      //  Log.d(TAG, "Barcode read: " + barcode.displayValue);
                    } else {
                        statusMessage.setText(R.string.barcode_failure);
                     //   Log.d(TAG, "No barcode captured, intent data is null");
                    }
                } else {
                    statusMessage.setText(String.format(getString(R.string.barcode_error),
                            CommonStatusCodes.getStatusCodeString(resultCode)));
                }
            }
            else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }



}
