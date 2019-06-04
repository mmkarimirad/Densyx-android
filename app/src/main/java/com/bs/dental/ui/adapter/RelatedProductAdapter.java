package com.bs.dental.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bs.dental.R;
import com.bs.dental.model.BaseProductModel;

import java.util.List;

/**
 * Created by Ashraful on 11/27/2015.
 */
public class RelatedProductAdapter extends ProductAdapter {
    public RelatedProductAdapter(Context context, List<? extends BaseProductModel> productsList) {
        super(context, productsList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products_grid_related,parent, false);

        return new ProductSummaryHolder(itemView);
    }
}
