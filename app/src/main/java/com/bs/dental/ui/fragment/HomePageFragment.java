package com.bs.dental.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bs.dental.R;
import com.bs.dental.model.Category;
import com.bs.dental.model.DoctorCustomer;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.RetroClientTurn;
import com.bs.dental.networking.response.CategoryNewResponse;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.adapter.CategoryHomePageAdapter;
import com.bs.dental.ui.adapter.ProductListAdapter;
import com.daimajia.slider.library.SliderLayout;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roboguice.inject.InjectView;

/**
 * Created by Ashraful on 11/9/2015.
 */

public class HomePageFragment extends BaseFragment {
    @InjectView(R.id.slider)
    SliderLayout sliderLayout;
    @InjectView(R.id.rootView)
    RelativeLayout rlRootView;
    @InjectView(R.id.vg_featureCategories)
    LinearLayout featureCategoriesLinearLayout;
    @InjectView(R.id.vg_featureManufacturer)
    LinearLayout featureManufacturerLinearLayout;
    @InjectView(R.id.vg_featureProduct)
    LinearLayout featureProductLinearLayout;
    @InjectView(R.id.featuredCategoryContainerLayout)
    LinearLayout featuredCategoryContainerLayout;
    @InjectView(R.id.et_search_item)
    TextView searchItemTextView;

    RecyclerView featureCategoriesRv;
    RecyclerView featureProductRv;
    RecyclerView featureManufacturerRv;

    protected ProductListAdapter productAdapter;

    public ImageView iv_toolbar;

    private List<DoctorCustomer> DoctorCustomerList;


    @InjectView(R.id.expandList)
    private RecyclerView expandableRecylerview;

    View view;

    @Inject
    PreferenceService preferenceService;

    @Override
    public void onResume() {
        super.onResume();
        //getActivity().setTitle(getActivity().getResources().getString(R.string.app_name));
        getActivity().setTitle(null);
        iv_toolbar.setVisibility(View.VISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_homepage, container, false);

            // hide imageview in first fragment
            iv_toolbar = (ImageView) getActivity().findViewById(R.id.iv_toolbar);
            iv_toolbar.setVisibility(View.VISIBLE);


        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        return view;
    }

    //start
    public void onEvent(CategoryNewResponse response) {
        if (response.getCount() > 0) {
            Utility.setCartCounter(response.getCount());
        }
        ArrayList<Category> categories = new ArrayList<>();
        for (int i = 0; i < response.getData().size(); i++) {
            //if (i!=1){
                Category category = new Category();
                category.setName(response.getData().get(i).getName());
                category.setId(response.getData().get(i).getId());
                category.setImagePath(response.getData().get(i).getImagePath());
                category.setImageUrl(null);
                categories.add(category);
            //}
        }

        // ============================== Recyclerview category ===============================

        CategoryHomePageAdapter categoryHomePageAdapter=new CategoryHomePageAdapter(getActivity(),categories,preferenceService,response.getData(),this);
        expandableRecylerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        expandableRecylerview.setAdapter(categoryHomePageAdapter);

        preferenceService.SetPreferenceValue(PreferenceService.taxShow, response.isDisplayTaxInOrderSummary());
        preferenceService.SetPreferenceValue(PreferenceService.discuntShow, response.isShowDiscountBox());
    }
    //end

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkEventBusRegistration();
        setSizeinList();
//        if (!isDataLoaded)
//            callWebservice();


        //start
        RetroClient.getApi().getNewCategory().enqueue(new CustomCB<CategoryNewResponse>(rlRootView));

        // TODO: 5/7/2019  --- mmkr
        /*RetroClient.getApi().getDoctorCustomersFromDate(300,0).enqueue(new Callback<List<DoctorCustomer>>() {
            @Override
            public void onResponse(Call<List<DoctorCustomer>> call, Response<List<DoctorCustomer>> response) {

                if (response.isSuccessful()) {
                    DoctorCustomerList = response.body();
                    Toast.makeText(getActivity(), "DoctorCustomer_TurnService : SUCCESS \n" + DoctorCustomerList.get(0).getFirstName(), Toast.LENGTH_LONG).show();
                    for (int i=0; i<DoctorCustomerList.size();i++){
                        Log.d("RETROFIT", "DOCTOR : " + i + " - " + DoctorCustomerList.get(i).getLastName());
                    }

                } else {
                        Log.d("RETROFIT", "DoctorCustomer_TurnService : ERROR : " + response.errorBody().toString());
                    Toast.makeText(getActivity(), "DoctorCustomer_TurnService : ERROR \n" + response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<DoctorCustomer>> call, Throwable t) {
                Log.d("RETROFIT", "DoctorCustomer_TurnService : FAIL : " + call.toString());
                Toast.makeText(getActivity(), "DoctorCustomer_TurnService : FAIL \n" + call.toString(), Toast.LENGTH_LONG).show();
            }
        });*/
        //***

        searchItemTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new SearchFragment()).
                        addToBackStack(null).commit();
            }
        });
    }


    public void setSizeinList() {
        featureCategoriesRv = (RecyclerView) featureCategoriesLinearLayout.findViewById(R.id.rv_product);
        featureManufacturerRv = (RecyclerView) featureManufacturerLinearLayout.findViewById(R.id.rv_product);
        featureProductRv = (RecyclerView) featureProductLinearLayout.findViewById(R.id.rv_product);


        featureCategoriesRv.setLayoutManager(getLinearLayoutManager());
        featureManufacturerRv.setLayoutManager(getLinearLayoutManager());
        featureProductRv.setLayoutManager(getLinearLayoutManager());

        hideBtns(featureProductLinearLayout);
        hideBtns(featureManufacturerLinearLayout);

        setTitle(getString(R.string.feature_product), featureProductLinearLayout);
        setTitle("Feature Categories", featureCategoriesLinearLayout);
        setTitle(getString(R.string.feature_menufecture), featureManufacturerLinearLayout);

        /*featureProductLinearLayout.setVisibility(View.GONE);
        featureCategoriesLinearLayout.setVisibility(View.GONE);
        featureManufacturerLinearLayout.setVisibility(View.GONE);*/
    }

    public LinearLayoutManager getLinearLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return layoutManager;
    }

    private void setTitle(String title, View view) {
        ((TextView) view.findViewById(R.id.title)).setText(title.toUpperCase());
    }

    private void hideBtns(LinearLayout linearLayout) {
        AppCompatImageButton subCatsBtn = (AppCompatImageButton) linearLayout.findViewById(R.id.btn_menu_sub_cats);
        Button viewAllBtn = (Button) linearLayout.findViewById(R.id.btn_view_all);
        subCatsBtn.setVisibility(View.GONE);
        viewAllBtn.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        String SCREEN_NAME = "Home Page";
        //pushAnalyticsEvent(SCREEN_NAME);
    }
}
