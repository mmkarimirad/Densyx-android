package com.bs.dental.ui.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.model.Category;

import java.util.List;

/**
 * Created by Ashraful on 11/6/2015.
 */
public class SubCategoryAdapter extends BaseAdapter {

    int Resource;
    private int lastPosition=-1;
    List<Category> categories;
    Context context;

    public SubCategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;

    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Category getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categories.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        View row = inflater.inflate(R.layout.item_category,parent,false);
        TextView title = (TextView) row.findViewById(R.id.title);
        title.setText(this.categories.get(position).getName());

        return row;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        View row = inflater.inflate(R.layout.item_category,parent,false);
        TextView title = (TextView) row.findViewById(R.id.title);

        title.setText(this.categories.get(position).getName());
        return row;
    }
}

