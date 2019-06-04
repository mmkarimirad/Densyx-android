package com.bs.dental.ui.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.model.Category;
import com.bs.dental.model.ProductService;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.fragment.ProductListFragmentFor3_8;
import com.bs.dental.ui.fragment.Utility;
import com.bs.dental.utils.Language;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashraful on 9/17/2015.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    public List<Category> categories;
    static List<Category> allCategories;
    public Context context;
    Fragment fragment;
    PreferenceService preferenceService;


    public ExpandableListAdapter(Context context, List<Category> categories, List<Category> allCategories, Fragment fragment,PreferenceService preferenceService)
    {
        this.categories=categories;
        this.allCategories = allCategories;
        this.fragment=fragment;
        this.context=context;
        this.preferenceService=preferenceService;
    }
    public ExpandableListAdapter(Context context, List<Category> categories)
    {
        this.categories=categories;

        this.context=context;
    }
    @Override
    public int getGroupCount() {
        return  categories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.v("childSize",""+getCategoryList(categories.get(groupPosition).getId()).size());
        return getCategoryList(categories.get(groupPosition).getId()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return categories.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Log.v("childS",""+getCategoryList(categories.get(groupPosition).getId()).get(childPosition));

        return getCategoryList(categories.get(groupPosition).getId()).get(childPosition);

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = (LayoutInflater.from(context)).inflate(R.layout.item_expandable_list_group, parent, false);
        TextView textView_catName = (TextView)convertView.findViewById(R.id.textView_name);
        AppCompatImageView imageView=(AppCompatImageView)convertView.findViewById(R.id.expandableIcon);
        AppCompatImageView iconImageView = (AppCompatImageView) convertView.findViewById(R.id.iv_icon);
        Category current = categories.get(groupPosition);
        textView_catName.setText("" + current.getName());
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            textView_catName.setGravity(Gravity.RIGHT);
        }

        if(getChildrenCount(groupPosition)<1)
        {
            imageView.setVisibility(View.INVISIBLE);
            textView_catName.setOnClickListener(new CategoryonClicklistener(current));
        }
        else {
            imageView.setVisibility(View.VISIBLE);
             if (isExpanded)
                imageView.setImageResource(R.drawable.ic_chevron_up);
            else
                imageView.setImageResource(R.drawable.ic_chevron_down);
        }
        //Log.d("icon", current.getIconPath());
        Picasso.with(context)
                .load(current.getIconPath())
                .fit().centerInside()
                .into(iconImageView);

        // textView_catName.setCompoundDrawables(null,null, ContextCompat.getDrawable(context,R.drawable.category_expand),null);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = (LayoutInflater.from(context)).inflate(R.layout.item_expandable_list_child, parent, false);
        TextView textView_catName = (TextView)convertView.findViewById(R.id.textView_name);
        Category current = (Category)getChild(groupPosition, childPosition);
        textView_catName.setText(current.getName());
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            textView_catName.setGravity(Gravity.RIGHT);
        }
        textView_catName.setOnClickListener(new CategoryonClicklistener(current));
        /*if (childPosition == getChildrenCount(groupPosition)-1) {
            convertView.setPadding(convertDptoPx(46), convertDptoPx(14), 0,  convertDptoPx(14));
        }*/

        return convertView;
    }

    protected  int convertDptoPx(int dpValue)
    {
        float density = context.getResources().getDisplayMetrics().density;
        int px = (int)(dpValue * density);
        return px;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }
    public List<Category> getCategoryList(int id)
    {
        List<Category> categories=new ArrayList<>();
        for(Category category: allCategories)
        {
            if(category.getParentCategoryId()==id)
            {


                categories.add(category);
            }
        }
        return  categories;
    }

    private class CategoryonClicklistener implements View.OnClickListener
    {
        Category category;
      public  CategoryonClicklistener(Category category)
      {
        this.category=category;
      }
        @Override
        public void onClick(View v) {
            ProductService.productId=category.getId();

            gotoProductListPage(category);
        }
    };

    private void gotoProductListPage(Category category) {
        Utility.closeLeftDrawer();

        fragment.getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        fragment.getFragmentManager().beginTransaction()
                .replace(R.id.container, ProductListFragmentFor3_8.newInstance(category.getName(), category.getId()))
                .addToBackStack(null)
                .commit();
    }

}
