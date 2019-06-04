package com.bs.dental.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.model.ProductModel;
import com.bs.dental.model.ViewType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashraful on 11/12/2015.
 */
public class ProductAdapter extends RecyclerView.Adapter implements Filterable {
    private static final int VIEW_PROG = 4;
    public List<ProductModel> products;
    boolean checkedTracks[];
    public int ViewFormat = ViewType.GRID;
    protected Context context;
    protected OnItemClickListener mItemClickListener;
    protected List<ProductModel> filteredData;

  /*  public ProductAdapter( Context context,List<CategoryDetails> products)
    {
        this.products=products;
        this.context=context;
    }*/

    public ProductAdapter(Context context, List productsList) {
        try {
            this.products = new ArrayList<>();
            checkedTracks = new boolean[productsList.size()];
            this.products = productsList;
            this.context = context;
            Log.d("adapterSize", "" + productsList.size());
            filteredData = new ArrayList<>();
            filteredData.addAll(productsList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ProductAdapter(Context context, List productsList, int viewType) {
        this(context, productsList);
        ViewFormat = viewType;
    }

    public void addAll(List<ProductModel> products) {
        this.products.addAll(products);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layout = 0;
        if (viewType == ViewType.GRID)
            layout = R.layout.item_products_grid;
        else if (viewType == ViewType.LIST)
            layout = R.layout.item_product_list;
        else if (viewType == ViewType.SINGLE)
            layout = R.layout.item_product_single;
        else if (viewType == ViewType.HOMEPAGE_VIEW)
            layout = R.layout.item_homepage_product;
        else if (viewType == VIEW_PROG) {
            layout = R.layout.item_progress;
            View itemView = LayoutInflater.from(parent.getContext()).
                    inflate(layout, parent, false);
            return new ProgressViewHolder(itemView);

        }


        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(layout, parent, false);

        return new ProductSummaryHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {

        return products.get(position) != null ? ViewFormat : VIEW_PROG;

        //   return ViewFormat;
        //return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder bindViewHolder, final int position) {
        try {
            if (bindViewHolder instanceof ProductSummaryHolder) {
                ProductModel productModel = products.get(position);
                ProductSummaryHolder holder = (ProductSummaryHolder) bindViewHolder;
                holder.productName.setText(productModel.getName());
                // System.out.println(holder.productName.getText().toString() + "," + productModel.getName());
                holder.productPrice.setText(productModel.getProductPrice().getPrice());
                holder.productOldPrice.setText(productModel.getProductPrice().getOldPrice());
                Picasso.with(context).load(productModel.getDefaultPictureModel().getImageUrl()).
                        fit().centerInside().into(holder.productImage);
                holder.fav.setTag(new Integer(position));
            } else {
                ((ProgressViewHolder) bindViewHolder).progressBar.setIndeterminate(true);
            }

        } catch (ClassCastException ex) {

        }


    }


    @Override
    public int getItemCount() {
        if (products == null)
            return 0;
        return products.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();

                // List<Object> filteredResult = getFilteredResults(charSequence);
                if (charSequence == null || charSequence.length() == 0) {
                    // No filter implemented we return all the list
                    results.values = filteredData;
                    results.count = filteredData.size();
                } else if (filteredData != null) {
                    ArrayList<ProductModel> filterResultsData = new ArrayList<>();
                    for (ProductModel data : filteredData) {
                        //   Log.d("data"," "+charSequence);

                        //In this loop, you'll filter through originalData and compare each item to charSequence.
                        //If you find a match, add it to your new ArrayList
                        //I'm not sure how you're going to do comparison, so you'll need to fill out this conditional
                        String price = "" + data.getProductPrice().getPrice();
                        if (data.getName().toLowerCase().contains(charSequence.toString().toLowerCase())
                                || price.contains(charSequence)) {
                            filterResultsData.add(data);
                        }
                    }

                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }
                return results;
            }


            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                products = (List<ProductModel>) filterResults.values;
                notifyDataSetChanged();

            }

        };
    }

    public class ProductSummaryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            fav = (CheckBox) itemView.findViewById(R.id.fav);
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

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }
}

