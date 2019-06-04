package com.bs.dental.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.model.BaseProductModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashraful on 11/10/2015.
 */
public class HomePageProductAdapter extends RecyclerView.Adapter<HomePageProductAdapter.ProductSummaryHolder>{
    public List<BaseProductModel> productsList ;

    protected Context context;
    protected OnItemClickListener mItemClickListener;


    public HomePageProductAdapter( Context context,List<? extends BaseProductModel> productsList)
    {
        try {
            this.productsList = new ArrayList<>();
            this.productsList.addAll(productsList);
            this.context = context;
            Log.d("adapterSize", "" + productsList.size());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public ProductSummaryHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layout;
       layout= R.layout.item_homepage_product;


        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(layout,parent,false);

        return new ProductSummaryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductSummaryHolder holder, final int position) {
        BaseProductModel product=productsList.get(position);
        Picasso.with(context).load(product.getDefaultPictureModel().getImageUrl())
                .fit().centerInside().into(holder.productImage);
        holder.productName.setText(product.getName());
        holder.productPrice.setVisibility(View.GONE);
        holder.productOldPrice.setVisibility(View.GONE);

    }



    @Override
    public int getItemCount() {
        if(productsList==null)
            return 0;
        return productsList.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public  class ProductSummaryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView productImage;
        protected TextView productPrice;
        protected TextView productName;
        protected TextView productOldPrice;


        public ProductSummaryHolder(View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.img_productImage);
            productPrice = (TextView) itemView.findViewById(R.id.tv_productPrice);
            productName = (TextView) itemView.findViewById(R.id.tv_productName);
            productOldPrice = (TextView) itemView.findViewById(R.id.tv_productOldPrice);
            productOldPrice.setPaintFlags(productOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }


        }

    }
}

