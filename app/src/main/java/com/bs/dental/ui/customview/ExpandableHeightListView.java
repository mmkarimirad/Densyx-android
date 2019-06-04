package com.bs.dental.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

/**
 * Created by Ashraful on 11/25/2015.
 */
public class ExpandableHeightListView extends ExpandableListView {

    boolean expanded = false;

    public ExpandableHeightListView(Context context, AttributeSet attrs, int defaultStyle) {
        super(context, attrs, defaultStyle);
    }
    public ExpandableHeightListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // HACK!  TAKE THAT ANDROID!
        if (isExpanded())
        {

            int expandSpec = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);

            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = getMeasuredHeight();
            }
        else
        {
           super.onMeasure(widthMeasureSpec, heightMeasureSpec);
           }
        }


}