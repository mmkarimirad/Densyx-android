package com.bs.dental.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.bs.dental.R;
import com.bs.dental.model.PictureModel;
import com.bs.dental.ui.customview.ExtendedViewPager;
import com.bs.dental.ui.customview.TouchImageView;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Ashraful on 2/11/2016.
 */
@ContentView(R.layout.activity_full_screen_image)
public class FullScreenImageActivity extends MotherActivity {

    public static int sliderPosition = 0;
    @InjectView(R.id.view_pager)
    ExtendedViewPager extendedViewPager;

    @InjectView(R.id.galleryClose)
    ImageButton galleryCloseImageBtn;

    @InjectView(R.id.indicator)
    CirclePageIndicator circlePageIndicator;
    
    public static List<PictureModel>pictureModels;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewPager();
        onGalleryCloseBtnClicked();
    }

    private void onGalleryCloseBtnClicked() {
        galleryCloseImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }

    private void setViewPager()
    {
        extendedViewPager.setAdapter(new TouchImageAdapter());
        circlePageIndicator.setViewPager(extendedViewPager);
        circlePageIndicator.setCurrentItem(sliderPosition);
        circlePageIndicator.setPageColor(ContextCompat.getColor(this, R.color.container));
        circlePageIndicator.setFillColor(ContextCompat.getColor(this, R.color.priceColor));
//        circlePageIndicator.setStrokeColor(getResources().getColor(R.color.appSceondaryColor));
    }
    class TouchImageAdapter extends PagerAdapter {

        // private static int[] images = { R.drawable.nature_1, R.drawable.nature_2, R.drawable.nature_3, R.drawable.nature_4, R.drawable.nature_5 };

        @Override
        public int getCount() {
            return pictureModels.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            TouchImageView img = new TouchImageView(container.getContext());
           /* RadioButton radioButton=new RadioButton(container.getContext());
            group.addView(radioButton);*/
            img.setMaxZoom(4f);
            Picasso.with(FullScreenImageActivity.this).load(pictureModels.get(position).getImageUrl())
                    .into(img);
            container.addView(img, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
