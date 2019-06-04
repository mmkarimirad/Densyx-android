package com.bs.dental.ui.views;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bs.dental.ui.customview.RadioGridGroupforReyMaterial;
import com.rey.material.widget.RadioButton;

/**
 * Created by Ashraful on 12/9/2015.
 */
public class MethodSelctionProcess {

    public MethodSelctionProcess( RadioGridGroupforReyMaterial radioGridGroup)
    {
        this.radioGridGroup=radioGridGroup;
    }
   public RadioGridGroupforReyMaterial radioGridGroup;

    public   void resetRadioButton(int id) {
        for (int i = 0, count = radioGridGroup.getChildCount(); i < count; ++i) {
            View view = radioGridGroup.getChildAt(i);
            if (view instanceof LinearLayout) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int j = 0, count2 = viewGroup.getChildCount(); j < count2; ++j) {
                    View radiobView = viewGroup.getChildAt(j);
                    if (radiobView instanceof RadioButton) {
                        if (radiobView.getId() != id)
                            ((RadioButton) radiobView).setChecked(false);
                    }
                }
            }
        }

    }
}
