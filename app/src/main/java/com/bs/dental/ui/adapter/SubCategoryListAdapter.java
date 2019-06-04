package com.bs.dental.ui.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.model.ProductService;
import com.bs.dental.model.SecondSubCategory;
import com.bs.dental.model.SubCategory;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.fragment.ProductListFragmentFor3_8;
import com.bs.dental.ui.fragment.Utility;
import com.bs.dental.utils.Language;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by BS-182 on 8/8/2017.
 */

public class SubCategoryListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<SubCategory> categories;
    private PreferenceService preferenceService;
    private Fragment fragment;

    public SubCategoryListAdapter(Context context, List<SubCategory> categories, PreferenceService preferenceService, Fragment fragment) {
        this.context = context;
        this.categories = categories;
        this.preferenceService = preferenceService;
        this.fragment = fragment;
    }

    @Override
    public SecondSubCategory getChild(int groupPosition, int childPosition) {
        return categories.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SecondSubCategory subCategory = getChild(groupPosition, childPosition);
        convertView = (LayoutInflater.from(context)).inflate(R.layout.item_expandable_list_child, parent, false);
        TextView textView_name = (TextView) convertView.findViewById(R.id.textView_name);

        // ================================= text of secondsubcaregory (سس و چاشنی و  ...) ===================================

        textView_name.setText(subCategory.getName());
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            textView_name.setGravity(Gravity.RIGHT);
        }
        convertView.setOnClickListener(new CategoryonClicklistener(subCategory.getId(),subCategory.getName()));
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return categories.get(groupPosition).getChildren().size();
    }

    @Override
    public SubCategory getGroup(int groupPosition) {
        return categories.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return categories.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_expandable_list_group, null);
        AppCompatImageView iv_icon = (AppCompatImageView) convertView.findViewById(R.id.iv_icon);
        AppCompatImageView expandableIcon = (AppCompatImageView) convertView.findViewById(R.id.expandableIcon);
        TextView text = (TextView) convertView.findViewById(R.id.textView_name);
        SubCategory subCategory = getGroup(groupPosition);

        // ====================================== text of subcaregory (شکلات و  ...) =======================================

        text.setText(subCategory.getName());

        // ==================== if children count are less than 1, don't show icon <> =====================

        if (getChildrenCount(groupPosition) < 1) {
            expandableIcon.setVisibility(View.INVISIBLE);
            convertView.setOnClickListener(new CategoryonClicklistener(subCategory.getId(),subCategory.getName()));
        } else {
            expandableIcon.setVisibility(View.VISIBLE);
            if (isExpanded)
                expandableIcon.setImageResource(R.drawable.ic_chevron_up);
            else
                expandableIcon.setImageResource(R.drawable.ic_chevron_down);
        }
        Picasso.with(context).load(subCategory.getIconPath()).fit().centerInside().into(iv_icon);
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)) {
            text.setGravity(Gravity.RIGHT);
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    private class CategoryonClicklistener implements View.OnClickListener {
        private int id;
        private String name;
        public CategoryonClicklistener(int id,String name) {
            this.id = id;
            this.name=name;
        }

        @Override
        public void onClick(View v) {
            ProductService.productId = id;
            Utility.closeLeftDrawer();
            fragment.getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragment.getFragmentManager().beginTransaction()
                    .replace(R.id.container, ProductListFragmentFor3_8.newInstance(name, id))
                    .addToBackStack(null)
                    .commit();
        }
    }
}