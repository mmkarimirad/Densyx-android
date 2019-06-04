package com.bs.dental.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by BS-182 on 8/8/2017.
 */


public class CustomList extends ExpandableListView {

    public CustomList(Context context) {
            super(context);
    }
    public CustomList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            //999999 is a size in pixels. ExpandableListView requires a maximum height in order to do measurement calculations.
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(999999, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
}
