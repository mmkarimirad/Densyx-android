package com.bs.dental.ui.adapter;

/**
 * Created by Ashraful on 12/4/2015.
 */
import android.content.Context;

import com.bs.dental.model.BaseProductModel;
import com.bs.dental.model.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FeaturedProductAdapter extends HomePageProductAdapter {
    public FeaturedProductAdapter(Context context, List<? extends BaseProductModel> productsList) {
        super(context, productsList);
    }

    @Override
    public void onBindViewHolder(ProductSummaryHolder holder, int position) {
        ProductModel product=(ProductModel)productsList.get(position);
        Picasso.with(context).load(product.getDefaultPictureModel().getImageUrl())
                .fit().centerCrop().into(holder.productImage);
        holder.productName.setText(product.getName());
        holder.productOldPrice.setText(product.getProductPrice().getOldPrice());
        holder.productPrice.setText(product.getProductPrice().getPrice());
    }
}
