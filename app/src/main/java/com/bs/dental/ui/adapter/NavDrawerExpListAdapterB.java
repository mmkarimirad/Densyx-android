package com.bs.dental.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bs.dental.R;
import com.bs.dental.model.Category;
import com.bs.dental.model.SubCategory;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by BS62 on 7/19/2016.
 */
public class NavDrawerExpListAdapterB extends BaseExpandableListAdapter {
    private Context context;

    private List<Category> expandableListCategoryList;

    private OnDrawerListItemClicked mOnDrawerListItemClicked;

    int selectedGroupPosition = -1;
    int selectedChildPosition = -1;

    public NavDrawerExpListAdapterB(Context context, List<Category> expandableListCategoryList) {
        this.context = context;
        this.expandableListCategoryList = expandableListCategoryList;
    }

    public interface OnDrawerListItemClicked {
        void onGroupClicked(View v, Category category);
    }

    public OnDrawerListItemClicked getOnDrawerListItemClicked() {
        return mOnDrawerListItemClicked;
    }

    public void setOnDrawerListItemClicked(OnDrawerListItemClicked mOnDrawerListItemClicked) {
        this.mOnDrawerListItemClicked = mOnDrawerListItemClicked;
    }

    @Override
    public int getGroupCount() {
        return expandableListCategoryList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return expandableListCategoryList.get(groupPosition).getChildren().size();
    }

    @Override
    public Category getGroup(int groupPosition) {
        return expandableListCategoryList.get(groupPosition);
    }

    @Override
    public SubCategory getChild(int groupPosition, int childPosition) {
        return expandableListCategoryList.get(groupPosition).getChildren().get(childPosition);
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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        Category groupCategory= getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_expandable_list_group, null);
        }
        TextView textView_name = (TextView) convertView.findViewById(R.id.textView_name);
        ImageView categoryIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
        Picasso.with(context).load(groupCategory.getIconPath()).into(categoryIcon);
        textView_name.setText(groupCategory.getName());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //================================= product list items =============================================
            convertView = inflater.inflate(R.layout.item_expandable_list_child, null);
        }

        SubCategory category=getChild(groupPosition, childPosition);
        TextView childTextView = (TextView) convertView.findViewById(R.id.textView_name);
        childTextView.setText(category.getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"child Click",Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
