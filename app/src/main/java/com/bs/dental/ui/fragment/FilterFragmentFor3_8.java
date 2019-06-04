package com.bs.dental.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.model.FilterAttribute;
import com.bs.dental.model.PriceRange;
import com.bs.dental.model.ProductFilterItem;

import org.apmem.tools.layouts.FlowLayout;
import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;

/**
 * Created by BS156 on 28-Feb-17.
 * This filter is for nopCommerce version 3.8
 */
public class FilterFragmentFor3_8 extends BaseFragment {
    List<Integer> specs = new ArrayList<>();

    @InjectView(R.id.ll_rootLayout) LinearLayout rootLinearLayout;
    @InjectView(R.id.tv_max_price) TextView maxPriceTextView;
    @InjectView(R.id.tv_min_price) TextView minPriceTextView;
    @InjectView(R.id.rangeSeekbar_price) RangeSeekBar priceRangeSeekBar;

    LinearLayout attributeContainerLayout;
    private boolean whichParentFragment = false;

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

                    Fragment parentFragment = getParentFragment();

                    if(parentFragment instanceof ProductListFragmentForHomePage) {
                        whichParentFragment = true;
                    }else if(parentFragment instanceof ProductListFragmentFor3_8){
                        whichParentFragment = false;
                    }

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

    public void removeSpecificationList() {
        rootLinearLayout.removeAllViews();
        specs = new ArrayList<>();
    }

    public void setSpecificationFilterItem(List<ProductFilterItem> filterItemList) {
        for (ProductFilterItem filterItem : filterItemList) {
            generateAttrSpecView(filterItem);
        }
    }

    private void generateAttrSpecView(ProductFilterItem filterItem) {
        attributeContainerLayout = (LinearLayout) getLayoutInflater()
                .inflate(R.layout.separate_layout_each_attribute_product_details, rootLinearLayout, false);

        // Set label
        TextView textView = (TextView) getLayoutInflater().inflate(R.layout.text_view, rootLinearLayout, false);
        textView.setText(filterItem.getSpecificationName());
        attributeContainerLayout.addView(textView);

        FlowLayout flowLayout = (FlowLayout) getLayoutInflater()
                .inflate(R.layout.flow_layout, rootLinearLayout, false);
        // generate buttons
        for (FilterAttribute attribute : filterItem.getAttributes()) {
            flowLayout.addView(generateRadioButton(attribute, flowLayout));
        }

        attributeContainerLayout.addView(flowLayout);
        rootLinearLayout.addView(attributeContainerLayout);
    }



    private AppCompatCheckBox generateRadioButton(FilterAttribute attribute, FlowLayout flowLayout) {
        final AppCompatCheckBox button = (AppCompatCheckBox) getLayoutInflater()
                .inflate(R.layout.flow_check_box, flowLayout, false);
        button.setText(attribute.getAttributeName());
        button.setId(attribute.getId());
        if (attribute.isSelected()) {
            button.setChecked(true);
            specs.add(button.getId());
        }

        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    specs.add(button.getId());
                } else {
                    specs.remove(Integer.valueOf(button.getId()));
                }

                applyFilter();
            }
        });
        return button;
    }

    private void applyFilter() {
        if (whichParentFragment){
            if (specs.isEmpty()) {
                getParentListFragment().queryMapping.remove("specs");
            } else {
                getParentListFragment().queryMapping.put("specs", TextUtils.join(",", specs));
            }

            getParentListFragment().queryMapping.put("price", getPriceRange());
            getParentListFragment().drawerLayout.closeDrawers();
            getParentListFragment().callWebService();
        }

        else {
            if (specs.isEmpty()) {
                getParentListFragment2().queryMapping.remove("specs");
            } else {
                getParentListFragment2().queryMapping.put("specs", TextUtils.join(",", specs));
            }

            getParentListFragment2().queryMapping.put("price", getPriceRange());
            getParentListFragment2().drawerLayout.closeDrawers();
            getParentListFragment2().callWebService();
        }
    }


    public String  getPriceRange() {
        return priceRangeSeekBar.getSelectedMinValue() + "-" + priceRangeSeekBar.getSelectedMaxValue();
    }

    public ProductListFragmentForHomePage getParentListFragment() {
        return ((ProductListFragmentForHomePage) getParentFragment());
    }

    public ProductListFragmentFor3_8 getParentListFragment2() {
        return ((ProductListFragmentFor3_8) getParentFragment());
    }

}
