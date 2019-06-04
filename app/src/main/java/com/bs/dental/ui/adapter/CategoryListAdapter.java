package com.bs.dental.ui.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.model.Category;
import com.bs.dental.model.ProductService;
import com.bs.dental.model.ViewType;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.activity.GalleryActivity;
import com.bs.dental.ui.fragment.ProductListFragmentFor3_8;
import com.bs.dental.ui.fragment.Utility;

import java.util.List;

/**
 * Created by Arif Islam on 23-Feb-17.
 */

public class CategoryListAdapter extends RecyclerView.Adapter {
    private List<Category> productList;
    private Context context;
    private OnItemClickListener productClickListener;
    private PreferenceService preferenceService;
    private Fragment fragment;

    public CategoryListAdapter(Context context, List<Category> productList, PreferenceService preferenceService, Fragment fragment) {
        this.context = context;
        this.productList = productList;
        this.preferenceService = preferenceService;
        this.fragment = fragment;
    }

    public CategoryListAdapter( Context context, List<Category> productList,Fragment fragment) {
        this.productList = productList;
        this.context = context;
        this.fragment = fragment;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Category category);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_custom, parent, false);
        return new ProductSummaryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder bindViewHolder, final int position) {

        Category productModel = productList.get(position);
        ProductSummaryHolder holder = (ProductSummaryHolder) bindViewHolder;

        // ================================= text of caregory (محصولات غذایی) ===================================

        holder.subName.setText(productModel.getName().toUpperCase());
        holder.customList.setAdapter(new SubCategoryListAdapter(context, productModel.getChildren(), preferenceService, fragment));
        holder.subName.setOnClickListener(new CategoryonClicklistener(productModel));
        holder.customList.setGroupIndicator(null);

    }

    @Override
    public int getItemViewType(int position) {
        return ViewType.LIST;
    }


    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }


    private class ProductSummaryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView subName;
        ExpandableListView customList;

        ProductSummaryHolder(View itemView) {
            super(itemView);
            subName = (TextView) itemView.findViewById(R.id.subName);
            customList = (ExpandableListView) itemView.findViewById(R.id.customList);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (productClickListener != null) {
                productClickListener.onItemClick(v, productList.get(getAdapterPosition()));
            }
        }
    }

    private class CategoryonClicklistener implements View.OnClickListener {
        Category category;

        public CategoryonClicklistener(Category category) {
            this.category = category;
        }

        @Override
        public void onClick(View v) {
            ProductService.productId = category.getId();
            Log.e("cat", "onClick: "+category.getId());

            gotoProductListPage(category);
        }
    }



    private void gotoProductListPage(Category category) {
        Utility.closeLeftDrawer();

        fragment.getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);


        if (category.getId() == 196) {

            Intent intent = new Intent(context, GalleryActivity.class);
            intent.putExtra("categoryId", category.getId());
            (context).startActivity(intent);

        } else {
            fragment.getFragmentManager().beginTransaction()
                    .replace(R.id.container, ProductListFragmentFor3_8.newInstance(category.getName(), category.getId()))
                    .addToBackStack(null)
                    .commit();
        }

    }


}
