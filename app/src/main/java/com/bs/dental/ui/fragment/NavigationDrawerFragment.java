package com.bs.dental.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bs.dental.R;


/**
 * Created by Ashraful on 11/6/2015.
 */
public class NavigationDrawerFragment  extends CategoryFragment {


    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView= inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        /*NetworkUtilities.context=getActivity();*/

        //   isInMainMenu=true;
        return rootView;
    }


}
