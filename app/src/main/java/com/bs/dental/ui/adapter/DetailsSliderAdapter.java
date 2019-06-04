package com.bs.dental.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bs.dental.R;
import com.bs.dental.model.PictureModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by BS148 on 11/8/2016.
 */

public class DetailsSliderAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    List<PictureModel> imageUrl;

    OnSliderClickListener sliderClickListener;

    public DetailsSliderAdapter(Context context, List<PictureModel> imageUrl) {
        this.context = context;
        this.imageUrl = imageUrl;
    }

    @Override
    public int getCount() {
        return imageUrl.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==(LinearLayout)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int pos) {
        final int position = pos;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.layout_viewpager_slider,container,false);
        ImageView imageView= (ImageView) view.findViewById(R.id.image_view);
        LinearLayout linearLayout= (LinearLayout) view.findViewById(R.id.layout);
        Picasso.with(context).load(imageUrl.get(position).getImageUrl()).fit().centerInside().into(imageView);
        container.addView(view);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sliderClickListener != null) {
                    sliderClickListener.onSliderClick(v, position);
                }
            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

    public void setOnSliderClickListener(OnSliderClickListener sliderClickListener) {
        this.sliderClickListener = sliderClickListener;
    }

    public interface OnSliderClickListener {
        void onSliderClick(View view, int position);
    }
}
