package com.bs.dental.ui.customview;

import com.bs.dental.model.ImageModel;
import com.bs.dental.networking.response.HomePageBannerResponse;
import com.bs.dental.ui.fragment.Utility;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashraful on 11/9/2015.
 */
public class ImageSlider {

    public static void addSliderItem(List<ImageModel>models, SliderLayout sliderContainer)
    {
        sliderContainer.removeAllSliders();
        sliderContainer.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        for (ImageModel pictureModel : models) {
            DefaultSliderView textSliderView = new DefaultSliderView(Utility.getActivity());
            textSliderView.image(pictureModel.getImageUrl())
                    .setScaleType(BaseSliderView.ScaleType.Fit);
          //  sliderClickListener(textSliderView,details);

            sliderContainer.addSlider(textSliderView);
        }
    }
    public static void addSliderItem(HomePageBannerResponse bannerResponse, SliderLayout sliderLayout)
    {

        List<ImageModel>models=new ArrayList<>();

        for(int index=0;index<bannerResponse.getData().size();index++)
        {
            if(bannerResponse.getData().get(index).getImageUrl()!=null)
            {
                ImageModel model=new ImageModel();
                model.setImageUrl(bannerResponse.getData().get(index).getImageUrl());
                models.add(model);
            }
        }
        addSliderItem(models,sliderLayout);
    }
}
