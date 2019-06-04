package com.bs.dental.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.model.CartProduct;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.utils.Language;
import com.daimajia.swipe.SwipeLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ashraful on 12/11/2015.
 */
public class CheckoutOrderProductAdapter extends CartAdapter {
    boolean isProductRemovable;
    private PreferenceService preferenceService;

    public CheckoutOrderProductAdapter(Context context, List productsList, Fragment fragment,PreferenceService preferenceService) {
        super(context, productsList, fragment,preferenceService);
        this.preferenceService=preferenceService;
    }

    public CheckoutOrderProductAdapter(Context context, List productsList, Fragment fragment, boolean isProductRemovable,PreferenceService preferenceService) {
        super(context, productsList, fragment,preferenceService);
        this.isProductRemovable = isProductRemovable;
        this.preferenceService=preferenceService;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layout = 0;

        layout = R.layout.item_checkout_product;
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(layout, parent, false);
        return new ProductSummaryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder bindViewHolder, final int position) {
        try {
            if (bindViewHolder instanceof ProductSummaryHolder) {
                CartProduct productModel = products.get(position);
                ProductSummaryHolder holder = (ProductSummaryHolder) bindViewHolder;
                holder.productName.setText(productModel.getProductName());
               // holder.productPrice.setText("Price: " +productModel.getUnitPrice());
                addMultiColoredTextInView(context.getString(R.string.price)+": ", productModel.getUnitPrice(),
                        holder.productPrice, R.color.priceColor);
                if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
                    holder.productName.setRotationY(180);
                }
              //  holder.productTotalPrice.setText("Total: "+productModel.getSubTotal());
                addMultiColoredTextInView(context.getString(R.string.total)+": ", ""+productModel.getSubTotal(), holder.productTotalPrice);

               // holder.productQuantity.setText("Quantity: " + productModel.getQuantity());
                addMultiColoredTextInView(context.getString(R.string.quantity)," "+productModel.getQuantity(), holder.productQuantity);

                Picasso.with(context).load(productModel.getPicture().getImageUrl()).
                        fit().centerInside().into(holder.productImage);

                if (!isProductRemovable) {
                    holder.swipeLayout.setSwipeEnabled(false);
                }
                holder.fav.setTag(new Integer(position));
            }


        } catch (ClassCastException ex) {

        }


    }

    private void addMultiColoredTextInView(String firstWord,String secondWord,TextView textView)
    {
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            textView.setRotationY(180);
        }
        Spannable word = new SpannableString(firstWord);

        word.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.textPrimaryColor)), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(word);
        Spannable wordTwo = new SpannableString(secondWord);

        wordTwo.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.textSecondarColor)), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(wordTwo);
    }
    private void addMultiColoredTextInView(String firstWord,String secondWord,
                                          TextView textView,int colorResource)
    {
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            textView.setRotationY(180);
        }
        Spannable word = new SpannableString(firstWord);

        word.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.textPrimaryColor)), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(word);
        Spannable wordTwo = new SpannableString(secondWord);

        wordTwo.setSpan(new ForegroundColorSpan(ContextCompat.
                getColor(context,colorResource)), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(wordTwo);
    }

    public class ProductSummaryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView productImage;
        protected TextView productPrice;
        protected TextView productTotalPrice;
        protected TextView productName;
        protected TextView productQuantity;
        protected SwipeLayout swipeLayout;

        protected CheckBox fav;

        public ProductSummaryHolder(View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.img_productImage);
            productPrice = (TextView) itemView.findViewById(R.id.tv_productPrice);
            productName = (TextView) itemView.findViewById(R.id.tv_productName);
            productTotalPrice = (TextView) itemView.findViewById(R.id.tv_product_total_price);
            productQuantity = (TextView) itemView.findViewById(R.id.tv_product_quantity);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            fav = (CheckBox) itemView.findViewById(R.id.fav);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }


        }

    }
}
