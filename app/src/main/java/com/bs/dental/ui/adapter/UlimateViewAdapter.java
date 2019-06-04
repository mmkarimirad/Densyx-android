package com.bs.dental.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.model.ProductModel;
import com.bs.dental.model.ViewType;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashraful on 11/23/2015.
 */
public class UlimateViewAdapter extends UltimateViewAdapter
{
    private static final int VIEW_PROG =4 ;
    public List<ProductModel> products;
    boolean checkedTracks[];
    public  int ViewFormat= ViewType.GRID;
    protected Context context;
    protected UlimateViewAdapter.OnItemClickListener mItemClickListener;
    protected List<ProductModel>filteredData;

    public UlimateViewAdapter(Context context,List productsList)
    {
        try {
            this.products = new ArrayList<>();
            checkedTracks=new boolean[productsList.size()];
            //this.products.addAll(productsList);
            this.products=productsList;
            this.context = context;
            Log.d("adapterSize", "" + productsList.size());
            filteredData=new ArrayList<>();
            filteredData.addAll(productsList);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void addAll( List<ProductModel>products)
    {
        this.products.addAll(products);
    }


    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        return new ProductSummaryHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        int layout = 0;
        if(ViewFormat== ViewType.GRID)
            layout= R.layout.item_products_grid;
        else if(ViewFormat== ViewType.LIST)
            layout= R.layout.item_product_list;
        else if(ViewFormat== ViewType.SINGLE)
            layout= R.layout.item_product_single;
        else if(ViewFormat== ViewType.HOMEPAGE_VIEW)
            layout= R.layout.item_homepage_product;

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(layout,parent, false);

        return new ProductSummaryHolder(itemView);

    }

    @Override
    public int getItemViewType(int position) {
        return ViewFormat;
    }

    @Override
    public int getAdapterItemCount() {
        if(products==null)
            return 0;
        return products.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder bindViewHolder, int position) {
        try {
            if(bindViewHolder instanceof ProductSummaryHolder) {
                ProductModel productModel = products.get(position);
                ProductSummaryHolder holder = (ProductSummaryHolder) bindViewHolder;
                holder.productName.setText(productModel.getName());
                System.out.println(holder.productName.getText().toString() + "," + productModel.getName());
                holder.productPrice.setText(productModel.getProductPrice().getPrice());
                holder.productOldPrice.setText(productModel.getProductPrice().getOldPrice());
                Picasso.with(context).load(productModel.getDefaultPictureModel().getImageUrl()).
                        fit().centerInside().into(holder.productImage);
                holder.fav.setTag(new Integer(position));
            }
         /* else {
              ((ProgressViewHolder) bindViewHolder).progressBar.setIndeterminate(true);
          }*/


        }catch (ClassCastException ex)
        {

        }


    }


    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public int getItemCount() {
        if(products==null)
            return 0;
        return products.size();
    }
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

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
        protected TextView productOldPrice;
        protected TextView productName;
        protected CheckBox fav;

        public ProductSummaryHolder(View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.img_productImage);
            productPrice = (TextView) itemView.findViewById(R.id.tv_productPrice);
            productName = (TextView) itemView.findViewById(R.id.tv_productName);
            productOldPrice = (TextView) itemView.findViewById(R.id.tv_productOldPrice);
            productOldPrice.setPaintFlags(productOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            fav=(CheckBox)itemView.findViewById(R.id.fav);
            fav.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
            //fav.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }


        }

    }

}


