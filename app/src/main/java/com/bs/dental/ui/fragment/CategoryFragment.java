package com.bs.dental.ui.fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bs.dental.Constants;
import com.bs.dental.R;
import com.bs.dental.model.Category;
import com.bs.dental.model.ExpandableListCategory;
import com.bs.dental.model.ProductService;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.response.CategoryResponse;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.adapter.ExpandableListAdapter;
import com.bs.dental.utils.Language;
import com.commonsware.cwac.merge.MergeAdapter;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;

/**
 * Created by Ashraful on 9/17/2015.
 */
public class CategoryFragment extends BaseFragment {
    List<Category> maincategoryList = new ArrayList<>();
    List<Category> parentCategories = new ArrayList<>();
    List<ExpandableListCategory> expandableListCategoryList;
    /*  @InjectView(R.id.Categorycontainer)
      LinearLayout container;*/
    @InjectView(R.id.list)
    ListView listView;
    @InjectView(R.id.ll_rootLayout)
    RelativeLayout RootViewLinearLayout;
    MergeAdapter mergeAdapter;
    @Inject
    PreferenceService preferenceService;

    private   View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView= inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Utility.setActivity(getActivity());
        getBaseUrl();
        RetroClient.getApi().getCategory().enqueue(new CustomCB<CategoryResponse>(RootViewLinearLayout));
    }

    public void getBaseUrl() {
        if (preferenceService.GetPreferenceBooleanValue(PreferenceService.DO_USE_NEW_URL)) {
            Constants.BASE_URL = preferenceService
                    .GetPreferenceValue(PreferenceService.URL_PREFER_KEY);
        }
    }

    public void onEvent(CategoryResponse response) {
        preferenceService.SetPreferenceValue(PreferenceService.taxShow,response.isDisplayTaxInOrderSummary());
        preferenceService.SetPreferenceValue(PreferenceService.discuntShow,response.isShowDiscountBox());
        List<Category> categories = response.getData();
        expandableListCategoryList = new ArrayList<>();
        maincategoryList = categories;
        if (response.getCount() > 0) {
            Utility.setCartCounter(response.getCount());
        }
        for (Category category : categories) {
            if (category.getParentCategoryId() == 0) {
                ExpandableListCategory expandableListCategory = new ExpandableListCategory();
                expandableListCategory.setParentCategory(category);
                expandableListCategory.setChildCategory(getCategoryList(category.getId()));
                expandableListCategoryList.add(expandableListCategory);
                parentCategories.add(category);
            }
        }
        System.out.print("size " + expandableListCategoryList.size());

        mergeAdapter = new MergeAdapter();

        populateCategory();
        listView.setAdapter(mergeAdapter);


    }

    public void populateCategory() {
        for (ExpandableListCategory expandableListCategory : expandableListCategoryList) {
            if (expandableListCategory.getChildCategory().size() > 0) {
                populateSubcategory(expandableListCategory);
            } else {
                //noChild
                populateStandAloneCategory(expandableListCategory.getParentCategory());
            }
        }
    }

    private void populateSubcategory(ExpandableListCategory expandableListCategory) {
        TextView categoryTextView = (TextView) getActivity().getLayoutInflater().
                inflate(R.layout.textview_parent_category, null);
        categoryTextView.setText(expandableListCategory.getParentCategory().getName());
        categoryTextView.setTag(expandableListCategory.getParentCategory().getId());
        categoryItemClickListener(expandableListCategory.getParentCategory(), categoryTextView);
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            categoryTextView.setGravity(Gravity.RIGHT);
        }
        mergeAdapter.addView(categoryTextView);
        // RootViewLinearLayout.addView(categoryTextView);
        ExpandableListView expandableListView = (ExpandableListView) getActivity()
                .getLayoutInflater()
                .inflate(R.layout.expandable_list_category, null, false);

        intializeExpandableList(expandableListView, expandableListCategory.getChildCategory());
    }

    private void intializeExpandableList(ExpandableListView exListCategory, List<Category> parentCategories) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            exListCategory.setIndicatorBounds(exListCategory.getLeft(), exListCategory.getRight());
        } else {
            exListCategory.setIndicatorBoundsRelative(exListCategory.getLeft(), exListCategory
                    .getRight());
        }
        ExpandableListAdapter adapter = new
                ExpandableListAdapter(getActivity(), parentCategories, maincategoryList, this,preferenceService);
        exListCategory.setAdapter(adapter);
        //  mergeAdapter.addAdapter(exListCategory.getAdapter());
        setListViewHeight(exListCategory);
        exListCategory.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int position, long id) {
                setListViewHeight(parent, position);
                return false;
            }
        });
        mergeAdapter.addView(exListCategory);
        //   mergeAdapter.addAdapter(adapter);
        // container.addView(exListCategory);
    }

    private void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    private void setListViewHeight(ExpandableListView listView, int group) {
        android.widget.ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();
                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10) {
            height = 200;
        }
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    private void populateStandAloneCategory(Category category) {
        TextView categoryTextView = (TextView) getActivity().getLayoutInflater().
                inflate(R.layout.textview_standalone_category, null);
        categoryTextView.setText(category.getName());
        categoryTextView.setTag(category.getId());
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            categoryTextView.setGravity(Gravity.RIGHT);
        }
        // RootViewLinearLayout.addView(categoryTextView);
        categoryItemClickListener(category, categoryTextView);
        mergeAdapter.addView(categoryTextView);
    }

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }


    private List<Category> getCategoryList(int id) {
        List<Category> categories = new ArrayList<>();
        for (Category category : maincategoryList) {
            if (category.getParentCategoryId() == id) {
                categories.add(category);
            }
        }
        return categories;
    }

    private void categoryItemClickListener(final Category category, View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductService.productId = category.getId();
                Utility.closeLeftDrawer();
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                getFragmentManager().beginTransaction()
                        .replace(R.id.container, ProductListFragmentFor3_8.newInstance(category.getName(), category.getId()))
                        .addToBackStack(null)
                        .commit();
            }
        });

    }


}
