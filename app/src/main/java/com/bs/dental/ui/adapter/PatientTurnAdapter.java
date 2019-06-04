package com.bs.dental.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.model.PatientTurn;
import com.bs.dental.model.ProductTurn;
import com.bs.dental.model.ViewType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arif Islam on 23-Feb-17.
 */

public class PatientTurnAdapter extends RecyclerView.Adapter {
    private List<PatientTurn> patientList;
    private Context context;

    private int visibleThreshold = 2;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private int currentPage = 1;
    private int totalPage = 1;

    private OnLoadMoreListener loadMoreListener;
    private OnPatientClickListener patientClickListener;

    public PatientTurnAdapter(Context context, List<PatientTurn> patientTurnList, RecyclerView recyclerView, int totalProductPage) {
        this.patientList = new ArrayList<>();
        this.patientList.addAll(patientTurnList);
        this.context = context;
        this.totalPage = totalProductPage;

        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager layoutManager = (GridLayoutManager) recyclerView
                    .getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = layoutManager.getItemCount();
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold) && currentPage < totalPage) {
                        // End has been reached
                        // Do something
                        currentPage++;
                        if (loadMoreListener != null) {
                            loadMoreListener.onLoadMore(currentPage);
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    public PatientTurnAdapter(Context context, List<PatientTurn> patientTurnList, RecyclerView recyclerView) {
        this.patientList = new ArrayList<>();
        this.patientList.addAll(patientTurnList);
        this.context = context;

        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager layoutManager = (GridLayoutManager) recyclerView
                    .getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = layoutManager.getItemCount();
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold) && currentPage < totalPage) {
                        // End has been reached
                        // Do something
                        currentPage++;
                        if (loadMoreListener != null) {
                            loadMoreListener.onLoadMore(currentPage);
                        }
                        loading = true;
                    }
                }
            });
        }
    }
    public void showLoader() {
        patientList.add(null);
    }

    public void hideLoader() {
        patientList.remove(patientList.size() - 1);
        notifyItemRemoved(patientList.size());
    }

    public void addMoreProducts(List<PatientTurn> pl) {
        if (this.patientList == null) {
            this.patientList = new ArrayList<>();
        }
        this.patientList.addAll(pl);

    }

    public void resetList() {
        if (patientList != null) {
            currentPage = 1;
            totalPage = 1;
            patientList.clear();
            notifyDataSetChanged();
        }
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ViewType.GRID) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_products_turn_grid, parent, false);
            return new ProductSummaryHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_progress, parent, false);
            return new ProgressViewHolder(itemView);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder bindViewHolder, int position) {
        try {
            if (bindViewHolder instanceof ProductSummaryHolder) {
                PatientTurn patientTurn = patientList.get(position);
                ProductSummaryHolder holder = (ProductSummaryHolder) bindViewHolder;
                holder.productName.setText(patientTurn.getFirstName() + " " + patientTurn.getLastName());
                holder.productPrice.setText(patientTurn.getPersianTurnDateTime());
                holder.productOldPrice.setText(patientTurn.getMobileNumber());
                holder.fav.setTag(position);
            } else {
                ((ProgressViewHolder) bindViewHolder).progressBar.setIndeterminate(true);
            }

        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return patientList.get(position) != null ? ViewType.GRID : ViewType.PROGRESS;
    }


    @Override
    public int getItemCount() {
        return patientList == null ? 0 : patientList.size();
    }

    public void setLoaded() {
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public void setOnPatientClickListener(OnPatientClickListener patientClickListener) {
        this.patientClickListener = patientClickListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int currentPage);
    }

    public interface OnPatientClickListener {
        void onPatientClick(View view, PatientTurn patientTurn);
    }

    private class ProductSummaryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView productImage;
        TextView productPrice;
        TextView productOldPrice;
        TextView productName;
        CheckBox fav;

        ProductSummaryHolder(View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.img_productImage);
            productPrice = (TextView) itemView.findViewById(R.id.tv_productPrice);
            productName = (TextView) itemView.findViewById(R.id.tv_productName);
            productOldPrice = (TextView) itemView.findViewById(R.id.tv_productOldPrice);
            productOldPrice
                    .setPaintFlags(productOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            fav = (CheckBox) itemView.findViewById(R.id.fav);
            fav.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
            //fav.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (patientClickListener != null) {
                patientClickListener.onPatientClick(v, patientList.get(getAdapterPosition()));
            }
        }

    }

    private static class ProgressViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

}
