package com.bs.dental.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.model.Category;
import com.bs.dental.model.Category2;
import com.bs.dental.model.ProductService;
import com.bs.dental.model.ViewType;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.activity.GalleryActivity;
import com.bs.dental.ui.fragment.HomePageFragment;
import com.bs.dental.ui.fragment.ProductListFragmentForHomePage;
import com.bs.dental.ui.fragment.Utility;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.bs.dental.ui.fragment.Utility.getActivity;

/**
 * Created by employee on 4/9/2018.
 */

public class CategoryHomePageAdapter extends RecyclerView.Adapter {
    private List<Category2> productList2, categoryList2;
    private Context context;
    private List<Category> productList,categoryList;
    private Fragment fragment;
    private OnItemClickListener productClickListener;
    private PreferenceService preferenceService;

    public CategoryHomePageAdapter(Context context, List<Category> productList, PreferenceService preferenceService, List<Category> categoryList, Fragment fragment) {
        this.productList = productList;
        this.categoryList = categoryList;
        this.context = context;
        this.fragment = fragment;
        this.preferenceService = preferenceService;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Category category);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_custom_home_page, parent, false);
        return new ProductSummaryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder bindViewHolder, int position) {
        Category productModel = productList.get(position);
        ProductSummaryHolder holder = (ProductSummaryHolder) bindViewHolder;
        holder.subName.setText(productModel.getName().toUpperCase());

        if (productModel.getImagePath()!=null){
            Picasso.with(context).load(productModel.getImagePath()).into(holder.img);
        }else if (productModel.getImageUrl()!=null){
            Picasso.with(context).load(productModel.getImageUrl()).into(holder.img);
        }
//        holder.customList.setAdapter(new SubCategoryListAdapter(context, productModel.getChildren(), preferenceService, fragment));
        holder.itemView.setOnClickListener(new CategoryonClicklistener(productModel));

    }

    @Override
    public int getItemViewType(int position) {
        return ViewType.GRID;
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }


    private class ProductSummaryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView subName;
        ExpandableListView customList;
        ImageView img;
        public View itemView;

        ProductSummaryHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            img = (ImageView) itemView.findViewById(R.id.img_productImage);

            // ================================== تغییر رنگ آیکون ها، اگر از HPFragment آمده باشد ================================
            /*if(fragment instanceof HomePageFragment){
                img.setColorFilter(Color.rgb(58,154,217) , PorterDuff.Mode.SRC_ATOP);
            }*/

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
            Log.e("cat", "onClick: " + category.getId());

            // if we choose gallery in first page, it should show a webview

            if (category.getId() == 213 || category.getId() == 214 ||
                    category.getId() == 255 || category.getId() == 256 ||
                    category.getId() == 257 || category.getId() == 258 || category.getId() == 196  ){

                Utility.closeLeftDrawer();

                Intent intent = new Intent(context , GalleryActivity.class);
                intent.putExtra("categoryId", category.getId());
                (getActivity()).startActivity(intent);

            }else
            gotoProductListPage(category);
        }
    }


    private void gotoProductListPage(Category category) {
        Log.e("cat", "gotoProductListPage: "+"child nadare" );
        Utility.closeLeftDrawer();

        //fragment.getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        fragment.getFragmentManager().beginTransaction()
                .replace(R.id.container, ProductListFragmentForHomePage.newInstance(category.getName(), category.getId()))
                .addToBackStack(null)
                .commit();
    }

}
