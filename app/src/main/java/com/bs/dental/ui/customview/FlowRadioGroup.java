package com.bs.dental.ui.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;

/**
 * Created by Ashraful on 12/15/2015.
 */
public class FlowRadioGroup extends org.apmem.tools.layouts.FlowLayout{
    private int mCheckedId = -1;
private CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;
private boolean mProtectFromCheckedChange = false;
private OnCheckedChangeListener mOnCheckedChangeListener;
private PassThroughHierarchyChangeListener mPassThroughListener;

        public FlowRadioGroup(Context context) {
            super(context);
            init();
        }

        public FlowRadioGroup(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            mChildOnCheckedChangeListener = new CheckedStateTracker();
            mPassThroughListener = new PassThroughHierarchyChangeListener();
            super.setOnHierarchyChangeListener(mPassThroughListener);
        }

        @Override
        public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
            mPassThroughListener.mOnHierarchyChangeListener = listener;
        }

        @Override
        protected void onFinishInflate() {
            super.onFinishInflate();

            if (mCheckedId != -1) {
                mProtectFromCheckedChange = true;
                setCheckedStateForView(mCheckedId, true);
                mProtectFromCheckedChange = false;
                setCheckedId(mCheckedId);
            }
        }

        @Override
        public void addView(@NonNull View child, int index, ViewGroup.LayoutParams params) {
            if (child instanceof AppCompatRadioButton) {
                final AppCompatRadioButton button = (AppCompatRadioButton) child;
                if (button.isChecked()) {
                    mProtectFromCheckedChange = true;
                    if (mCheckedId != -1) {
                        setCheckedStateForView(mCheckedId, false);
                    }
                    mProtectFromCheckedChange = false;
                    setCheckedId(button.getId());
                }
            }

            super.addView(child, index, params);
        }

        public void check(int id) {
            if (id != -1 && (id == mCheckedId)) {
                return;
            }

            if (mCheckedId != -1) {
                setCheckedStateForView(mCheckedId, false);
            }

            if (id != -1) {
                setCheckedStateForView(id, true);
            }

            setCheckedId(id);
        }

        private void setCheckedId(int id) {
            mCheckedId = id;
            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChanged(this, mCheckedId);
            }
        }

        private void setCheckedStateForView(int viewId, boolean checked) {
            View checkedView = findViewById(viewId);
            if (checkedView != null && checkedView instanceof AppCompatRadioButton) {
                ((AppCompatRadioButton) checkedView).setChecked(checked);
            }
        }

        public int getCheckedAppCompatRadioButtonId() {
            return mCheckedId;
        }

        public void clearCheck() {
            check(-1);
        }

        public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
            mOnCheckedChangeListener = listener;
        }

        @Override
        public void onInitializeAccessibilityEvent(@NonNull AccessibilityEvent event) {
            super.onInitializeAccessibilityEvent(event);
            event.setClassName(FlowRadioGroup.class.getName());
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo info) {
            super.onInitializeAccessibilityNodeInfo(info);
            info.setClassName(FlowRadioGroup.class.getName());
        }

public interface OnCheckedChangeListener {
    void onCheckedChanged(FlowRadioGroup group, int checkedId);
}

private class CheckedStateTracker implements CompoundButton.OnCheckedChangeListener {
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mProtectFromCheckedChange) {
            return;
        }

        mProtectFromCheckedChange = true;
        if (mCheckedId != -1) {
            setCheckedStateForView(mCheckedId, false);
        }
        mProtectFromCheckedChange = false;

        int id = buttonView.getId();
        setCheckedId(id);
    }
}

private class PassThroughHierarchyChangeListener implements
        OnHierarchyChangeListener {
    private OnHierarchyChangeListener mOnHierarchyChangeListener;

    public void onChildViewAdded(View parent, View child) {
        if (parent == FlowRadioGroup.this && child instanceof AppCompatRadioButton) {
            int id = child.getId();
            // generates an id if it's missing
            if (id == View.NO_ID) {
                Log.d("need Id", "");
                //     id = ViewUtils.generateViewId();
                child.setId(id);
            }
            ((AppCompatRadioButton) child).setOnCheckedChangeListener(
                    mChildOnCheckedChangeListener);
        }

        if (mOnHierarchyChangeListener != null) {
            mOnHierarchyChangeListener.onChildViewAdded(parent, child);
        }
    }

    public void onChildViewRemoved(View parent, View child) {
        if (parent == FlowRadioGroup.this && child instanceof AppCompatRadioButton) {
            ((AppCompatRadioButton) child).setOnCheckedChangeListener(null);
        }

        if (mOnHierarchyChangeListener != null) {
            mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
        }
    }
}
}