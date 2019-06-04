package com.bs.dental.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.model.FilterItem;
import com.bs.dental.model.PriceRange;
import com.bs.dental.ui.customview.FlowRadioGroup;

import org.apmem.tools.layouts.FlowLayout;
import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import roboguice.inject.InjectView;

/**
 * Created by Ashraful on 11/11/2015.
 * This filter is for nopCommerce version 3.7 and earlier
 */
public class FilterFragment extends BaseFragment implements View.OnClickListener {
    static HashMap<String, List<FilterItem>> filterNameItemsMap;
    static HashMap<String, List<FilterItem>> alreadyFilterNameItemsMap;

    static List<FilterItem> filterItemList;
    static List<FilterItem> alreadyFilterItemList = new ArrayList<>();

    static PriceRange priceRange;

    String productSpecification = "";
    String alreadyAppliedProductSpecification = "";

    @InjectView(R.id.ll_rootLayout) LinearLayout rootLinearLayout;
    @InjectView(R.id.tv_max_price) TextView maxPriceTextView;
    @InjectView(R.id.tv_min_price) TextView minPriceTextView;
    @InjectView(R.id.rangeSeekbar_price) RangeSeekBar priceRangeSeekBar;

    LinearLayout attributeContainerLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootLinearLayout = (LinearLayout) view.findViewById(R.id.ll_rootLayout);
    }

    public void generateFilterView(List<FilterItem> filterItems) {
        filterNameItemsMap = new HashMap<>();
        alreadyFilterNameItemsMap = new HashMap<>();
        filterItemList = filterItems;
        setKeyValueinHashMap(filterItemList, filterNameItemsMap);
        generateView();

    }

    public void clearAllSpecificationItem() {
        filterItemList = new ArrayList<>();
        filterNameItemsMap = new HashMap<>();

        alreadyFilterItemList = new ArrayList<>();
        alreadyFilterNameItemsMap = new HashMap<>();
        rootLinearLayout.removeAllViews();
    }

    @SuppressWarnings("unchecked")
    public void setPriceFilter(PriceRange priceRange) {
        priceRangeSeekBar.setRangeValues(priceRange.getFrom(), priceRange.getTo());
        priceRangeSeekBar.setSelectedMinValue(priceRange.getFrom());
        priceRangeSeekBar.setSelectedMaxValue(priceRange.getTo());

        setMaxMinPriceInTextView();

        priceRangeSeekBar.setNotifyWhileDragging(true);

        priceRangeSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    applyFilter();
                    return true;
                }
                return false;
            }
        });

        priceRangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar rangeSeekBar, Object o, Object t1) {
                setMaxMinPriceInTextView();
            }
        });
    }

    private  void setMaxMinPriceInTextView() {
        maxPriceTextView.setText(priceRangeSeekBar.getSelectedMaxValue().toString());
        minPriceTextView.setText(priceRangeSeekBar.getSelectedMinValue().toString());
    }


    public void generateView() {
        //rootLinearLayout.removeAllViews();

        if(filterNameItemsMap !=null)
        for (String key : filterNameItemsMap.keySet()) {
            generateSpecificationAtrributContainerView(key);
            generateViewLabel(key);
            generateSpecificationAtrributView(key);

        }
    }


    public void generateAlreadyFilteredView(List<FilterItem> filterItems) {
        alreadyFilterNameItemsMap = new HashMap<>();
        alreadyFilterItemList = filterItems;
        setKeyValueinHashMap(alreadyFilterItemList, alreadyFilterNameItemsMap);
        generateAlreadyFilteredView();
    }

    public void generateAlreadyFilteredView() {

        FlowLayout flowLayout = (FlowLayout) getLayoutInflater().inflate(R.layout.flow_layout, rootLinearLayout, false);
        Button removeFilterBtn = (Button) getLayoutInflater().inflate(R.layout.btn_remove, rootLinearLayout, false);
        for (FilterItem filterItem : alreadyFilterItemList) {
            alreadyAppliedProductSpecification = alreadyAppliedProductSpecification + "," + filterItem.getFilterId();
            generateFilteredTag(flowLayout, filterItem);

        }
        rootLinearLayout.addView(flowLayout);
        rootLinearLayout.addView(removeFilterBtn);
        removeFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productSpecification = "";
                alreadyAppliedProductSpecification = "";
                applyFilter();
            }
        });


    }

    public void generateFilteredTag(FlowLayout flowLayout, FilterItem filterItem) {
        android.widget.CheckedTextView checkedTextView = (android.widget.CheckedTextView) getLayoutInflater().inflate(R.layout.chipview, flowLayout, false);
        checkedTextView.setText(filterItem.getSpecificationAttributeName() + " " + filterItem.getSpecificationAttributeOptionName());
        flowLayout.addView(checkedTextView);
    }

    public void generateSpecificationAtrributContainerView(String key) {
        attributeContainerLayout = (LinearLayout) getLayoutInflater().inflate
                (R.layout.separate_layout_each_attribute_product_details, rootLinearLayout, false);
    }

    public void generateSpecificationAtrributView(String key) {
        List<FilterItem> list = filterNameItemsMap.get(key);

        FlowRadioGroup flowRadioGroup = (FlowRadioGroup) getLayoutInflater().inflate
                (R.layout.flow_layout_radiogroup, rootLinearLayout, false);

        for (FilterItem filterItem : list) {
            flowRadioGroup.addView(generateRadioButton(filterItem, flowRadioGroup));
        }

        flowRadioGroup.setOnCheckedChangeListener(new FlowRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(FlowRadioGroup group, int checkedId) {
                productSpecification = checkedId + alreadyAppliedProductSpecification;
                applyFilter();

            }
        });

        attributeContainerLayout.addView(flowRadioGroup);
        rootLinearLayout.addView(attributeContainerLayout);

    }

    public RadioButton generateRadioButton(FilterItem filterItem, FlowRadioGroup flowRadioGroup) {
        RadioButton button = (RadioButton) getLayoutInflater().inflate
                (R.layout.radiobutton_group_product_selection, flowRadioGroup, false);
        button.setText(filterItem.getSpecificationAttributeOptionName());
        button.setId(filterItem.getFilterId());
        return button;
    }

    private void generateViewLabel(String label) {
        TextView textView = (TextView) getLayoutInflater().inflate(R.layout.text_view, rootLinearLayout, false);
        textView.setText(label);
        attributeContainerLayout.addView(textView);
        // addViewintLayout(textView);
    }

    public void actionOnNoitemFilter() {
        rootLinearLayout.removeAllViews();
        filterNameItemsMap = new HashMap<>();
        alreadyFilterNameItemsMap=new HashMap<>();

    }

    public void setKeyValueinHashMap(List<FilterItem> filterItemList, HashMap<String, List<FilterItem>> FilterNameItemsMap) {

        for (final FilterItem filterItem : filterItemList) {

            List<FilterItem> list = FilterNameItemsMap.get(filterItem.getSpecificationAttributeName());
            if (list == null) {
                list = new ArrayList<FilterItem>();
                FilterNameItemsMap.put(filterItem.getSpecificationAttributeName(), list);
            }
            list.add(filterItem);
            FilterNameItemsMap.put(filterItem.getSpecificationAttributeName(), list);

        }
    }

    @Override
    public void onClick(View v) {
        int resourceId = v.getId();
    }

    private void applyFilter() {
        // getParentProductListFargement().getQueryMap();
        //getParentProductListFargement().isFilterApplied = true;

        if (productSpecification.isEmpty()) {
            getParentListFragment().queryMapping.remove("specs");
        } else
            getParentListFragment().queryMapping.put("specs", productSpecification);

        getParentListFragment().queryMapping.put("price", getPriceRange());
        getParentListFragment().drawerLayout.closeDrawers();
        getParentListFragment().callWebService();
    }

    public String  getPriceRange() {
        return priceRangeSeekBar.getSelectedMinValue() + "-" + priceRangeSeekBar.getSelectedMaxValue();
    }


    public ProductListFragment getParentListFragment() {
        return ((ProductListFragment) getParentFragment());
    }
}
