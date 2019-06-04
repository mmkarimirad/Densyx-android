package com.bs.dental.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bs.dental.Constants;
import com.bs.dental.R;
import com.bs.dental.model.Category;
import com.bs.dental.model.ProductService;
import com.bs.dental.model.SecondSubCategory;
import com.bs.dental.model.SubCategory;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.response.CategoryNewResponse;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.activity.BaseActivity;
import com.bs.dental.utils.Language;
import com.google.inject.Inject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;

/**
 * Created by Ashraful on 9/17/2015.
 */
public class CategoryNewFragment extends BaseFragment {
    @InjectView(R.id.ll_rootLayout)
    RelativeLayout RootViewLinearLayout;
    @Inject
    PreferenceService preferenceService;

    private View rootView;

    private LinearLayout mLinearListView;
    boolean isFirstViewClick = false;
    boolean isSecondViewClick = false;
    boolean isFirstResponse = true;
    public List<SubCategory> sub_categories;
    public List<SecondSubCategory> secondsub_categories;
    public ScrollView mScrollView;

    Fragment fragment = this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.category, container, false);

        // TODO: 4/11/2019 mmkr: later most be correct "scroll element"
        //*** mScrollView = getActivity().findViewById(R.id.scroll);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Utility.setActivity(getActivity());
        getBaseUrl();

        RetroClient.getApi().getNewCategory().enqueue(new CustomCB<CategoryNewResponse>(RootViewLinearLayout));
    }

    public void getBaseUrl() {
        if (preferenceService.GetPreferenceBooleanValue(PreferenceService.DO_USE_NEW_URL)) {
            Constants.BASE_URL = preferenceService
                    .GetPreferenceValue(PreferenceService.URL_PREFER_KEY);
        }
    }

    public void onEvent(CategoryNewResponse response) {
        if (response.getCount() > 0) {
            Utility.setCartCounter(response.getCount());
        }

        // ========================== isFirstResponse is used to show just one response =============================

        if (isFirstResponse) {

            isFirstResponse = false;

            String[] category_name = new String[response.getData().size() - 1];

            final ArrayList<Category> categories = new ArrayList<>();

            for (int i = 0; i < response.getData().size() - 1; i++) {

                //if (i != 1 ) {
                Category category = new Category();
                category.setName(response.getData().get(i).getName());
                category.setId(response.getData().get(i).getId());
                category.setImagePath(response.getData().get(i).getImagePath());
                //category.setImageUrl(null);
                category.setChildren(response.getData().get(i).getChildren());
                categories.add(category);
                category_name[i] = category.getName();
                //}

            }


            // =====================================================================================


            mLinearListView = (LinearLayout) getActivity().findViewById(R.id.linear_listview);


            for (int ii = 0; ii < categories.size(); ii++) {

                final int index_i = ii;
                sub_categories = categories.get(ii).getChildren();

                LayoutInflater inflater = null;
                inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mLinearView = inflater.inflate(R.layout.row_first_1, null);

                final TextView mProductName = (TextView) mLinearView.findViewById(R.id.textViewName);
                final RelativeLayout mLinearFirstArrow = (RelativeLayout) mLinearView.findViewById(R.id.linearFirst);
                final ImageView mImageArrowFirst = (ImageView) mLinearView.findViewById(R.id.imageFirstArrow);
                final LinearLayout mLinearScrollSecond = (LinearLayout) mLinearView.findViewById(R.id.linear_scroll);

                mLinearScrollSecond.setTag(index_i);
                //Toast.makeText(getContext(), String.valueOf(mLinearScrollSecond.getTag())+"hi", Toast.LENGTH_SHORT).show();

                if (sub_categories.size() < 1) {
                    mLinearFirstArrow.setOnClickListener(new CategoryonClicklistener(categories.get(index_i).getId(), categories.get(index_i).getName()));
                } else {

                    if (isFirstViewClick == false) {
                        mImageArrowFirst.setBackgroundResource(R.drawable.ic_chevron_down);
                    } else {
                        mImageArrowFirst.setBackgroundResource(R.drawable.ic_chevron_up);
                    }

                    mImageArrowFirst.setVisibility(View.VISIBLE);

                    mLinearFirstArrow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            /*mLinearFirstArrow.getId();
                            mLinearScrollSecond.getId();*/

                            /*Toast.makeText(getContext(), String.valueOf(index_i), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), String.valueOf(mLinearScrollSecond.getTag()), Toast.LENGTH_SHORT).show();*/

                            //mLinearScrollSecond.setVisibility(View.GONE);

                            if (isFirstViewClick == false) {
                                isFirstViewClick = true;
                                mImageArrowFirst.setBackgroundResource(R.drawable.ic_chevron_up);

                                mLinearScrollSecond.setVisibility(View.VISIBLE);

                                /*for (int iii = 0; iii < categories.size(); iii++) {
                                    if (String.valueOf(mLinearScrollSecond.getTag()) == String.valueOf(index_i)) {
                                        Toast.makeText(getContext(), String.valueOf(mLinearScrollSecond.getTag()), Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(getContext(), String.valueOf(index_i), Toast.LENGTH_SHORT).show();
                                        mLinearScrollSecond.setVisibility(View.VISIBLE);
                                    }
                                    else
                                        mLinearScrollSecond.setVisibility(View.GONE);
                                }*/

                            } else {
                                isFirstViewClick = false;
                                mImageArrowFirst.setBackgroundResource(R.drawable.ic_chevron_down);
                                mLinearScrollSecond.setVisibility(View.GONE);
                            }

                        }
                    });
                }

                final String name = categories.get(ii).getName();
                mProductName.setText(name);

                for (int j = 0; j < sub_categories.size(); j++) {

                    final int index_j = j;

                    secondsub_categories = categories.get(ii).getChildren().get(j).getChildren();

                    LayoutInflater inflater2 = null;
                    inflater2 = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View mLinearView2 = inflater2.inflate(R.layout.row_second_1, null);

                    TextView mSubItemName = (TextView) mLinearView2.findViewById(R.id.textViewTitle);
                    final RelativeLayout mLinearSecondArrow = (RelativeLayout) mLinearView2.findViewById(R.id.linearSecond);
                    final ImageView mImageArrowSecond = (ImageView) mLinearView2.findViewById(R.id.imageSecondArrow);
                    final ImageView explist_icon = (ImageView) mLinearView2.findViewById(R.id.explist_icon);
                    final LinearLayout mLinearScrollThird = (LinearLayout) mLinearView2.findViewById(R.id.linear_scroll_third);

                    /*if (isSecondViewClick == false) {
                        mLinearScrollThird.setVisibility(View.GONE);
                    } else {
                        mLinearScrollThird.setVisibility(View.VISIBLE);
                    }*/

                    if (secondsub_categories.size() < 1) {
                        mLinearSecondArrow.setOnClickListener(new CategoryonClicklistener(sub_categories.get(j).getId(), sub_categories.get(j).getName()));

                    } else {


                        if (isFirstViewClick == false) {
                            mImageArrowSecond.setBackgroundResource(R.drawable.ic_chevron_down);
                        } else {
                            mImageArrowSecond.setBackgroundResource(R.drawable.ic_chevron_up);
                        }

                        mImageArrowSecond.setVisibility(View.VISIBLE);

                        mLinearSecondArrow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (isSecondViewClick == false) {
                                    isSecondViewClick = true;
                                    mImageArrowSecond.setBackgroundResource(R.drawable.ic_chevron_up);
                                    mLinearScrollThird.setVisibility(View.VISIBLE);
                                    /*scroll.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            scroll.fullScroll(View.FOCUS_DOWN);
                                        }
                                    });*/

                                } else {
                                    isSecondViewClick = false;
                                    mImageArrowSecond.setBackgroundResource(R.drawable.ic_chevron_down);
                                    mLinearScrollThird.setVisibility(View.GONE);
                                }

                            }
                        });
                    }

                    Picasso.with(getContext()).load(sub_categories.get(j).getIconPath()).fit().centerInside().into(explist_icon);

                    final String catName = sub_categories.get(j).getName();
                    mSubItemName.setText(catName);

                    for (int k = 0; k < secondsub_categories.size(); k++) {

                        final int index_k = k;

                        LayoutInflater inflater3 = null;
                        inflater3 = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View mLinearView3 = inflater3.inflate(R.layout.row_third_1, null);

                        TextView mItemName = (TextView) mLinearView3.findViewById(R.id.textViewItemName);
                        final RelativeLayout mLinearForthArrow = (RelativeLayout) mLinearView3.findViewById(R.id.linearthird);

                        final String itemName = secondsub_categories.get(k).getName();

                        mItemName.setText(itemName);
                        mImageArrowSecond.setVisibility(View.VISIBLE);

                        mLinearForthArrow.setOnClickListener(new CategoryonClicklistener(secondsub_categories.get(k).getId(), secondsub_categories.get(k).getName()));


                        mLinearScrollThird.addView(mLinearView3);
                    }

                    mLinearScrollSecond.addView(mLinearView2);

                }

                mLinearListView.addView(mLinearView);

            }

            // =====================================================================================

        }



        String languageToLoad;
        if (response.getLanguage() != null) {
            if (response.getLanguage().getCurrentLanguageId() == 1) {
                languageToLoad = Language.ENGLISH;
            } else {
                languageToLoad = Language.ARABIC;
            }

            preferenceService.SetPreferenceValue(PreferenceService.CURRENT_LANGUAGE, languageToLoad);
            ((BaseActivity) getActivity()).setLocale(true);
        }
        preferenceService.SetPreferenceValue(PreferenceService.taxShow, response.isDisplayTaxInOrderSummary());
        preferenceService.SetPreferenceValue(PreferenceService.discuntShow, response.isShowDiscountBox());
    }

    private class CategoryonClicklistener implements View.OnClickListener {
        private int id;
        private String name;

        public CategoryonClicklistener(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public void onClick(View v) {
            ProductService.productId = id;
            Utility.closeLeftDrawer();
            fragment.getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragment.getFragmentManager().beginTransaction()
                    .replace(R.id.container, ProductListFragmentFor3_8.newInstance(name, id))
                    .addToBackStack(null)
                    .commit();
        }
    }

}
