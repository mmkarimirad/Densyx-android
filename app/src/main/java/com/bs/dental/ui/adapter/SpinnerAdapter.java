package com.bs.dental.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bs.dental.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashraful on 12/7/2015.
 */
public class SpinnerAdapter extends ArrayAdapter<String> {

    public SpinnerAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }
    public SpinnerAdapter(Context context)
    {
        this(context, R.layout.simple_spinner_item, new ArrayList<String>());
    }
    @Override
    public boolean isEnabled(int position){
        if(position == 0)
        {
            // Disable the first item from Spinner
            // First item will be use for hint
            return false;
        }
        else
        {
            return true;
        }
    }
    public void addHint(String hint)
    {
     add(hint);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = super.getView(position, convertView, parent);
        /*((TextView)v.findViewById(android.R.id.text1)).
               setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.downarrow,0);*/
        if (position ==0) {
            ((TextView)v.findViewById(android.R.id.text1)).setText("");
            ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(0)); //"Hint to be displayed"
        }

        return v;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
