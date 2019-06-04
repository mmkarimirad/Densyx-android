package com.bs.dental.ui.views;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;

import com.bs.dental.R;

/**
 * Created by Ashraful on 11/12/2015.
 */
public class DrawerManipulationFromFragment {
    DrawerLayout drawerLayout;
    public DrawerManipulationFromFragment(DrawerLayout drawerLayout) {
        this.drawerLayout=drawerLayout;
    }

    public  void  DrawerSetup(final Fragment fragment)
    {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                fragment.getActivity(),
                drawerLayout,
                null,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {

           @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                super.onDrawerSlide(drawerView, 0);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

             //   fragment.getActivity().invalidateOptionsMenu();
                syncState();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //super.onDrawerOpened(drawerView);
                super.onDrawerSlide(drawerView, 0);
               // fragment.getActivity().invalidateOptionsMenu();
                syncState();
            }

        };

    //    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

}
