package com.bs.dental.ui.views;

import android.content.Context;
import android.widget.LinearLayout;

import com.bs.dental.model.ProductAttribute;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.response.ShoppingCartCheckoutAttributeApplyResponse;

import java.util.List;

/**
 * Created by Ashraful on 12/10/2015.
 */
public class CheckoutAttributeView extends ProductAttributeViews {
    final String checkoutAttributePrefix="checkout_attribute";
    public CheckoutAttributeView(Context context, List<ProductAttribute> attributes, LinearLayout layout) {
        super(context, attributes, layout);
    }
    @Override
    public String getKey(ProductAttribute productAttribute)
    {
        String key=String.format("%s_%s",checkoutAttributePrefix,productAttribute.getId());
        return key;
    }
    @Override
    public void callPriceWebservice()
    {
        RetroClient.getApi().applyCheckoutAttribute(getProductAttribute())
                .enqueue(new CustomCB<ShoppingCartCheckoutAttributeApplyResponse>());

    }
}
