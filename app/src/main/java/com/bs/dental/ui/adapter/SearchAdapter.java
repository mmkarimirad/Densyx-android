package com.bs.dental.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
 * Created by bs156 on 16-Feb-17.
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<ProductModel> mDataSet;
    Context context;

    public int viewFormat = ViewType.GRID;

    private int visibleThreshold = 2;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;

    private OnLoadMoreListener mLoadMoreListener;
    private OnItemClickListener mItemClickListener;

    public SearchAdapter(Context context, List<ProductModel> mDataSet, RecyclerView recyclerView, int viewType) {
        this.context = context;
        this.viewFormat = viewType;

        this.mDataSet = new ArrayList<>();
        this.mDataSet.addAll(mDataSet);

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = layoutManager.getItemCount();
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (mLoadMoreListener != null) {
                            mLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_products_grid, parent, false);
            viewHolder = new ItemViewHolder(view);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_progress, parent, false);

            viewHolder = new ProgressViewHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder bindViewHolder, int position) {
        if(bindViewHolder instanceof ItemViewHolder) {
            ProductModel productModel = mDataSet.get(position);
            ItemViewHolder holder = (ItemViewHolder) bindViewHolder;

            holder.productName.setText(productModel.getName());
            // System.out.println(holder.productName.getText().toString() + "," + productModel.getName());
            holder.productPrice.setText(productModel.getProductPrice().getPrice());
            holder.productOldPrice.setText(productModel.getProductPrice().getOldPrice());
            Picasso.with(context).load(productModel.getDefaultPictureModel().getImageUrl()).
                    fit().centerInside().into(holder.productImage);
            holder.fav.setTag(position);
        } else {
            ((ProgressViewHolder) bindViewHolder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    public ProductModel getItem(int position) {
        try {
            return mDataSet.get(position);
        } catch (IndexOutOfBoundsException ibe) {
            return null;
        }
    }

    public void addItem(ProductModel productModel) {
        if (mDataSet != null) {
            mDataSet.add(productModel);
        }
    }

    public void addListOfItem(List<ProductModel> productModels) {
        if (mDataSet != null) {
            mDataSet.addAll(productModels);
        }
    }

    public void removeProgress() {
        if (mDataSet != null ) {
            mDataSet.remove(mDataSet.size() - 1);
            notifyItemRemoved(mDataSet.size());
            setLoaded();
        }
    }

    public void clearList() {
        if (mDataSet != null) {
            mDataSet.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.mLoadMoreListener = loadMoreListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView productImage;
        TextView productPrice;
        TextView productOldPrice;
        TextView productName;

        CheckBox fav;
        ItemViewHolder(View itemView) {
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

    private class ProgressViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }
}
