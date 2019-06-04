package com.bs.dental.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.bs.dental.model.AttributeControlValue;
import com.rey.material.widget.RadioButton;

import java.util.List;

/**
 * Created by Ashraful on 11/26/2015.
 */
public class GridRadioGroupAdapter extends CheckBoxAttributeGridAdapter {
    RadioGroup rgp;
    private RadioButton mSelectedRB;
    private int mSelectedPosition = -1;

    public GridRadioGroupAdapter(Context context, int resource, List<AttributeControlValue> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=getLayoutInflater().inflate(resource,null);
        RadioButton radioButton=(RadioButton)convertView;
        radioButton.setText(getItem(position).getName());
        manipulateRadioButton(radioButton,position);
        return  convertView;
    }

    private void manipulateRadioButton(RadioButton radioButton, final int position)
    {
        radioButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if ((position != mSelectedPosition && mSelectedRB != null)) {
                    mSelectedRB.setChecked(false);
                }

                mSelectedPosition = position;
                mSelectedRB = (RadioButton) v;
            }
        });

        if (mSelectedPosition != position) {
            radioButton.setChecked(false);
        } else {
            radioButton.setChecked(true);
            if (mSelectedRB != null && radioButton != mSelectedRB) {
                mSelectedRB = radioButton;
            }
        }
    }
}
