package com.bs.dental.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bs.dental.model.AttributeControlValue;
import com.rey.material.widget.CheckBox;

import java.util.List;

/**
 * Created by Ashraful on 11/26/2015.
 */
public class CheckBoxAttributeGridAdapter extends ArrayAdapter<AttributeControlValue> {
    Context context;
    List<AttributeControlValue>values;
    int resource;
    public CheckBoxAttributeGridAdapter(Context context, int resource, List<AttributeControlValue> objects) {
        super(context, resource, objects);
        this.context=context;
        this.values=objects;
        this.resource=resource;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public AttributeControlValue getItem(int position) {
     return values.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(pyosition, convertView, parent);
        convertView=getLayoutInflater().inflate(resource,null);
        CheckBox checkBox=(CheckBox)convertView;
        checkBox.setText(getItem(position).getName());
        return convertView;
    }
    public LayoutInflater getLayoutInflater() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater;
    }

}
