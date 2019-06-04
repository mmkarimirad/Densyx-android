package com.bs.dental.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.model.KeyValuePair;
import com.bs.dental.model.ProductDetail;
import com.bs.dental.model.ProductModel;
import com.bs.dental.model.ProductSpecification;
import com.bs.dental.model.Quantity;
import com.bs.dental.networking.Api;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.response.AddtoCartResponse;
import com.bs.dental.networking.response.PriceResponse;
import com.bs.dental.networking.response.ProductDetailResponse;
import com.bs.dental.networking.response.RelatedProductResponse;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.activity.FullScreenImageActivity;
import com.bs.dental.ui.adapter.DetailsSliderAdapter;
import com.bs.dental.ui.adapter.RelatedProductAdapter;
import com.bs.dental.ui.customview.RadioGridGroup;
import com.bs.dental.ui.views.ProductAttributeViews;
import com.bs.dental.utils.Language;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.inject.Inject;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;
import java.util.regex.Matcher;

import roboguice.inject.InjectView;

/**
 * Created by Ashraful on 11/9/2015.
 */
public class ProductDetailFragment extends BaseFragment implements View.OnClickListener {
    protected int icon_expand;
    protected int icon_collapse;
    protected boolean isDescriptionExpanded = false;
    public static ProductModel productModel;

    @InjectView(R.id.tv_productOldPrice)
    protected TextView productOldPriceTextView;

    @InjectView(R.id.view_pager_slider)
    ViewPager viewPager;
    @InjectView(R.id.circle_indicator)
    CirclePageIndicator circlePageIndicator;

    @InjectView(R.id.tv_productName)
    protected TextView productNameTextview;

    @InjectView(R.id.tv_productPrice)
    TextView productPriceTextview;

    @InjectView(R.id.wv_description)
    protected WebView fullDescriptionView;

    @InjectView(R.id.tv_expand_collaps)
    protected TextView expandCollapseTextView;

    @InjectView(R.id.btn_addtoCart)
    protected Button addtoCartBtn;

    @InjectView(R.id.btn_buyNow)
    protected Button addToWihlistBtn;

    @InjectView(R.id.gridLayout_group_product)
    RadioGridGroup groupProducrgridLayout;

    @InjectView(R.id.dynamicAttributeLayout)
    protected LinearLayout dynamicAttributeLayout;

    @InjectView(R.id.rclv_product_list)
    protected RecyclerView RelatedProductList;

    @InjectView(R.id.cv_category_product_container)
    CardView relatedProductsCardView;

    @InjectView(R.id.expand_dsc)
    ImageView expandDsc;

    @InjectView(R.id.expand_view)
    View expandView;

    @InjectView(R.id.tv_specifications)
    TextView specificationTextView;

    @InjectView(R.id.quantitiyUp)
    ImageView quantitiyUp;

    @InjectView(R.id.quantityDown)
    ImageView quantityDown;

    @InjectView(R.id.textviewQuantity)
    TextView textviewQuantity;

    @InjectView(R.id.quantityLayout)
    LinearLayout quantityLayout;

    String descriptionText;
    protected ProductAttributeViews productAttributeViews;
    public static ProductDetailFragment self;
    boolean isAddedToWishlist = false;
    View view;
    private Rect rect;
    private Handler handler;
    private Runnable mLongPressed;
    private boolean continueIncrementing;
    private int orginalQuantity = 0;
    private Quantity quantity;
    @InjectView(R.id.addtoCartLayout)
    LinearLayout addtoCartLayout;

    @Inject
    private PreferenceService preferenceService;


    @InjectView(R.id.specificelayout)
    LinearLayout specificelayout;

    @InjectView(R.id.detailsScrollview)
    NestedScrollView detailsScrollview;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        self = this;
        view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        return view;
    }

    protected void initailizeExpandCollapseResource() {
        expandView.setOnClickListener(this);
        handler = new Handler();
        quantitiyUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                    mLongPressed = new Runnable() {
                        @Override
                        public void run() {
                            increaseQuantity();
                        }
                    };

                    handler.postDelayed(mLongPressed, 100);
                }

                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())) {
                        stopIncrmenting();
                    }
                }

                if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
                    stopIncrmenting();
                }

                return false;
            }
        });

        quantityDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                    mLongPressed = new Runnable() {
                        @Override
                        public void run() {
                            decreaseQuantity();
                        }
                    };

                    handler.postDelayed(mLongPressed, 100);
                }

                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())) {
                        stopIncrmenting();
                    }
                }

                if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
                    stopIncrmenting();
                }

                return false;
            }
        });

    }

    private void increaseQuantity() {
        int maxValue = 0;
        if (quantity.getOrderMaximumQuantity() > quantity.getStockQuantity()) {
            maxValue = quantity.getOrderMaximumQuantity();
        } else {
            maxValue = quantity.getStockQuantity();
        }
        if (orginalQuantity < maxValue) {
            orginalQuantity++;
            textviewQuantity.setText("" + orginalQuantity);
//            String price=productModel.getProductPrice().getPrice().substring(1);
//            productPriceTextview.setText(""+(orginalQuantity*Integer.parseInt(price)));
        }
    }

    private void decreaseQuantity() {

        if (orginalQuantity > quantity.getOrderMinimumQuantity()) {
            orginalQuantity--;
            textviewQuantity.setText("" + orginalQuantity);
//            String price=productModel.getProductPrice().getPrice().substring(1);
//            productPriceTextview.setText(""+(Integer.parseInt(price)/orginalQuantity));
        }

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addtoCartBtn.setOnClickListener(this);
        addToWihlistBtn.setOnClickListener(this);
        initializeView();
        callWebService();
    }


    private synchronized void stopIncrmenting() {
        handler.removeCallbacks(mLongPressed);
    }


    protected void initializeView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(10, 0, 10, 0);
        addtoCartLayout.setLayoutParams(params);
        setProduceNamePrice();
        initailizeExpandCollapseResource();
        RelatedProductList.setLayoutManager(getLinearLayoutManager());

        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)) {
            expandCollapseTextView.setGravity(Gravity.RIGHT);
            specificelayout.setGravity(Gravity.RIGHT);
            specificationTextView.setGravity(Gravity.RIGHT);
        }

        ExpandorCollapse();

    }

    protected void callWebService() {
        RetroClient.getApi().getProductDetails(productModel.getId())
                .enqueue(new CustomCB<ProductDetailResponse>(this.getView()));
        RetroClient.getApi().getRelatedProducts(productModel.getId())
                .enqueue(new CustomCB<RelatedProductResponse>(this.getView()));
    }

    public void onEvent(ProductDetailResponse detailResponse) {
        detailsScrollview.smoothScrollTo(0, 0);
        productModel = detailResponse.getData();
        descriptionText = detailResponse.getData().getFullDescription();
        quantity = detailResponse.getData().getQuantity();
        orginalQuantity = quantity.getOrderMinimumQuantity();
        textviewQuantity.setText("" + orginalQuantity);
        quantityLayout.setVisibility(View.VISIBLE);
        if (descriptionText != null) {

            //====================================== webview rtl =========================================

            String pish = "<html dir=\"rtl\" lang=\"\"><body>";
            String pas = "</body></html>";
            String myHtmlString = pish + descriptionText + pas;
            fullDescriptionView.loadDataWithBaseURL(null,myHtmlString, "text/html", "UTF-8", null);

            //fullDescriptionView.loadDataWithBaseURL(null, descriptionText, "text/html", "UTF-8", null);

        } else if (detailResponse.getData().getShortDescription() != null) {
            fullDescriptionView.loadDataWithBaseURL("", detailResponse.getData().getShortDescription(), "text/html", "UTF-8", "");
        }
        setProduceNamePrice();
        setProductSpecification(detailResponse.getData().getProductSpecifications());
        setImageInSlider(detailResponse.getData());
        showGroupProducts(detailResponse.getData());
        populateViewOfDynamicAttributeLayout(detailResponse.getData());

    }

    private void setProductSpecification(List<ProductSpecification> productSpecifications) {
        if (productSpecifications != null && productSpecifications.size() > 0) {
            String spec = "";
            specificationTextView.setMovementMethod(LinkMovementMethod.getInstance());
            for (ProductSpecification specification : productSpecifications) {
                if (specification.getName().equalsIgnoreCase("Link")) {
                    Matcher m = Patterns.WEB_URL.matcher(specification.getValue());
                    String url = "";
                    while (m.find()) {
                        url = m.group();
                        Log.d("Url", url);
                    }
                    spec += specification.getName() + " : " + url + "<br>";
                } else {
                    spec += specification.getName() + " : " + specification.getValue() + "<br>";
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                specificationTextView.setText(Html.fromHtml(spec, Html.FROM_HTML_MODE_COMPACT));
            } else {
                specificationTextView.setText(Html.fromHtml(spec));
            }
        }
        specificationTextView.setVisibility(View.VISIBLE);
    }

    public void onEvent(RelatedProductResponse productsResponse) {
        if (productsResponse.getData() != null && productsResponse.getData().size() > 0) {
            relatedProductsCardView.setVisibility(View.VISIBLE);
            // relatedProductTitleTextView.setVisibility(View.VISIBLE);
            RelatedProductAdapter productAdapter = new RelatedProductAdapter(getActivity(), productsResponse.getData());
            RelatedProductList.setAdapter(productAdapter);
            onRelatedProductClicked(productAdapter);
        } else {
            relatedProductsCardView.setVisibility(View.GONE);
        }

    }

    public void onEvent(PriceResponse priceResponse) {

        /*if (priceResponse.getPrice() != null)
            productPriceTextview.setText(priceResponse.getPrice());*/
    }

    public void onEvent(AddtoCartResponse addtoCartResponse) {
        if (addtoCartResponse.isSuccess() && addtoCartResponse.getStatusCode() == 200) {
            if (isAddedToWishlist) {
                Snackbar.make(getView(), R.string.product_add_to_wishlist, Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(getView(), R.string.product_added_to_cart, Snackbar.LENGTH_SHORT).show();
                Utility.setCartCounter(addtoCartResponse.getCount());
            }
        } else if (!addtoCartResponse.getErrorList()[0].isEmpty())
            Snackbar.make(getView(), addtoCartResponse.getErrorList()[0], Snackbar.LENGTH_SHORT).show();

    }

    public void onRelatedProductClicked(final RelatedProductAdapter relatedProductAdapter) {
        relatedProductAdapter.SetOnItemClickListener(new RelatedProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                productModel = relatedProductAdapter.products.get(position);
                dynamicAttributeLayout.setVisibility(View.GONE);
                try {
                    dynamicAttributeLayout.removeAllViews();
                } catch (Exception ex) {

                }
                initializeView();
                callWebService();
            }
        });
    }

    protected void setProduceNamePrice() {
        try {
            getActivity().setTitle(productModel.getName());
            productNameTextview.setText(productModel.getName());

            if (productModel.getProductPrice().getCallForPrice()){
                productPriceTextview.setText("تماس برای قیمت");
            } else {
                productPriceTextview.setText(productModel.getProductPrice().getPrice());
                productOldPriceTextView.setText(productModel.getProductPrice().getOldPrice());
                productOldPriceTextView.setPaintFlags(productOldPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    protected void setImageInSlider(final ProductDetail detail) {
        DetailsSliderAdapter detailsSliderAdapter = new DetailsSliderAdapter(getActivity(), detail.getPictureModels());
        viewPager.setAdapter(detailsSliderAdapter);
        viewPager.setCurrentItem(0);
        circlePageIndicator.setViewPager(viewPager);

        circlePageIndicator.setPageColor(ContextCompat.getColor(getActivity(), R.color.textHintColor));
        circlePageIndicator.setFillColor(ContextCompat.getColor(getActivity(), R.color.accentDark));

        detailsSliderAdapter.setOnSliderClickListener(new DetailsSliderAdapter.OnSliderClickListener() {
            @Override
            public void onSliderClick(View view, int sliderPosition) {

                FullScreenImageActivity.sliderPosition = sliderPosition;
                FullScreenImageActivity.pictureModels = detail.getPictureModels();
                Intent intent = new Intent(getActivity(), FullScreenImageActivity.class);
                startActivity(intent);
            }
        });


        /*viewPager.removeAllSliders();
        productImages.stopAutoCycle();
        for (PictureModel pictureModel : detail.getPictureModels()) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(getActivity());
            defaultSliderView.image(pictureModel.getImageUrl())
                    .setScaleType(BaseSliderView.ScaleType.CenterInside);
            onImageSliderClick(defaultSliderView,detail);

            productImages.addSlider(defaultSliderView);
        }
        productImages.setCustomIndicator((PagerIndicator) view.findViewById(R.id.custom_indicator));*/
    }

    private void onImageSliderClick(DefaultSliderView textSliderView, final ProductDetail details) {
        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                                    @Override
                                                    public void onSliderClick(BaseSliderView baseSliderView) {
                                                        FullScreenImageActivity.pictureModels = details.getPictureModels();
                                                        Intent intent = new Intent(getActivity(), FullScreenImageActivity.class);
                                                        startActivity(intent);

                                                    }
                                                }
        );
    }

    public void showGroupProducts(final ProductDetail details) {

        groupProducrgridLayout.removeAllViews();
        for (ProductDetail detail : details.getAssociatedProducts()) {

            generateViewOfSingleProductSelectorAmongGroupProduct(detail, details.getName());
        }

        groupProducrgridLayout.setOnCheckedChangeListener(new RadioGridGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGridGroup group, int checkedId) {
                productModel = searchProductById(checkedId, details);
                setProduceNamePrice();
            }
        });
        if (details.getAssociatedProducts().size() > 0)
            groupProducrgridLayout.check((int) details.getAssociatedProducts().get(0).getId());


    }

    protected void generateViewOfSingleProductSelectorAmongGroupProduct(ProductDetail associateProduct, String groupProductName) {
        RadioButton button = (RadioButton) getLayoutInflater().inflate
                (R.layout.radiobutton_group_product_selection, null);
        button.setText(associateProduct.getName().
                substring(groupProductName.length()));
        button.setId((int) associateProduct.getId());

        groupProducrgridLayout.addView(button);
    }

    protected void populateViewOfDynamicAttributeLayout(ProductDetail detail) {
        if (detail.getProductAttributes().size() > 0)
            dynamicAttributeLayout.setVisibility(View.VISIBLE);
        productAttributeViews =
                new ProductAttributeViews(getActivity(), detail.getProductAttributes(), dynamicAttributeLayout);
    }


    public ProductDetail searchProductById(int id, ProductDetail details) {
        ProductDetail associatedProducts = null;
        for (ProductDetail product : details.getAssociatedProducts()) {
            if (product.getId() == id) {
                associatedProducts = product;
                return associatedProducts;
            }

        }
        return associatedProducts;
    }

    public LinearLayoutManager getLinearLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return layoutManager;
    }

    @Override
    public void onClick(View v) {
        int resourceId = v.getId();
        if (resourceId == R.id.expand_view) {
            ExpandorCollapse();
        } else if (resourceId == R.id.btn_addtoCart && productAttributeViews != null) {
            isAddedToWishlist = false;
            callApiOfAddingProductIntoCart(Api.shoppingCartTypeCart);
        } else if (resourceId == R.id.btn_buyNow && productAttributeViews != null) {
            isAddedToWishlist = true;
            callApiOfAddingProductIntoCart(Api.shoppingCartTypeWishlist);
        }
    }

    public void callApiOfAddingProductIntoCart(int cartTypeId) {
        productAttributeViews.putEdittextValueInMap();
        List<KeyValuePair> productAttributes = productAttributeViews.getProductAttribute();
        if (orginalQuantity != 0) {
            KeyValuePair keyValuePair = new KeyValuePair();
            keyValuePair.setKey("addtocart_" + productModel.getId() + ".EnteredQuantity");
            keyValuePair.setValue(textviewQuantity.getText().toString());
            productAttributes.add(keyValuePair);
        }

        RetroClient.getApi()
                .addProductIntoCart(productModel.getId(), cartTypeId, productAttributes)
                .enqueue(new CustomCB<AddtoCartResponse>(this.getView()));
    }

    protected void ExpandorCollapse() {
        isDescriptionExpanded = !isDescriptionExpanded;
        fullDescriptionView.setVisibility(isDescriptionExpanded ? View.VISIBLE : View.GONE);
        expandDsc.setImageResource(getExpandCollapseDrawable());
        Drawable myIcon = expandDsc.getDrawable();
        ColorFilter filter = new LightingColorFilter(Color.BLACK, Color.BLACK);
        myIcon.setColorFilter(filter);

    }

    protected int getExpandCollapseDrawable() {
        return isDescriptionExpanded ? R.drawable.ic_minus : R.drawable.ic_plus;
    }

    public void onEvent() {

    }

    public boolean isLoggedIn() {
        return preferenceService.GetPreferenceBooleanValue(PreferenceService.LOGGED_PREFER_KEY);
    }
}

