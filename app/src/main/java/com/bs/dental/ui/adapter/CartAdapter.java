package com.bs.dental.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.model.CartProduct;
import com.bs.dental.model.KeyValuePair;
import com.bs.dental.model.ViewType;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.response.CartProductListResponse;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.customview.CircleTransformPicasso;
import com.bs.dental.utils.Language;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashraful on 11/30/2015.
 */
public class CartAdapter extends RecyclerSwipeAdapter {
    public List<CartProduct> products;
    public int ViewFormat = ViewType.LIST;
    protected Context context;
    protected OnItemClickListener mItemClickListener;
    Fragment fragment;
    private PreferenceService preferenceService;
  /*  public CartAdapter( Context context,List<CategoryDetails> products)
    {
        this.products=products;
        this.context=context;
    }*/

    public CartAdapter(Context context, List productsList, Fragment fragment, PreferenceService preferenceService) {
        try {
            this.products = new ArrayList<>();
            this.products.addAll(productsList);
            this.context = context;
            this.fragment = fragment;
            this.preferenceService = preferenceService;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layout = 0;

        layout = R.layout.item_cart_list;
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(layout, parent, false);
        return new ProductSummaryHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;

        //   return ViewFormat;
        //return super.getItemViewType(position);
    }

    private void setTouchListener(final ProductSummaryHolder holder, final CartProduct productModel) {

        final TextView editText = holder.productQuantity;
        if (productModel.getQuantity() == 1) {
            holder.qunatityDownImageView.getDrawable().mutate().setAlpha(70);
            holder.qunatityDownImageView.invalidate();
        }

        holder.qunatityUpImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuntity(1, editText, productModel);

            }
        });

        holder.qunatityDownImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuntity(-1, editText, productModel);

            }
        });
    }

    private void updateQuntity(int quantity, TextView textView,
                               CartProduct productModel) {
        int Previousqunatity = Integer.parseInt(textView.getText().toString());
        int totalQuntity = Previousqunatity + quantity;
        if (totalQuntity > 0) {
            updateCartItem("itemquantity" + productModel.getId(), "" + totalQuntity);

        }

    }

    private void updateCartItem(String key, String value) {
        List<KeyValuePair> keyValuePairs = new ArrayList<>();
        KeyValuePair keyValuePair = new KeyValuePair();
        keyValuePair.setKey(key);
        keyValuePair.setValue(value);
        keyValuePairs.add(keyValuePair);
        RetroClient.getApi().updateCartProductList(keyValuePairs)
                .enqueue(new CustomCB<CartProductListResponse>(fragment.getView()));


    }

    ProductSummaryHolder holder;// = (ProductSummaryHolder) bindViewHolder;
    CartProduct productModel;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder bindViewHolder, final int position) {
        try {
            if (bindViewHolder instanceof ProductSummaryHolder) {
                productModel = products.get(position);
                holder = (ProductSummaryHolder) bindViewHolder;
                holder.productName.setText(productModel.getProductName());

                holder.productPrice.setText(productModel.getUnitPrice());

                holder.productShortdescription.setVisibility(View.GONE);
                holder.productQuantity.setText("" + productModel.getQuantity());
                Picasso.with(context).load(productModel.getPicture().getImageUrl())
                        .transform(new CircleTransformPicasso())
                        .fit().centerInside().into(holder.productImage);
                holder.fav.setTag(new Integer(position));
                OntrashClicked(holder.removeItem, position);
                setTouchListener(holder, productModel);
                if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)) {
                    holder.productName.setRotationY(180);
                    holder.productPrice.setRotationY(180);
                    holder.productQuantity.setRotationY(180);
                    holder.productName.setGravity(Gravity.RIGHT);
                }
            }


        } catch (ClassCastException ex) {

        }


    }

    private void disableTextBoxSelection(TextView productQuantity) {
        productQuantity.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
    }

    private void OntrashClicked(ImageView itemview, final int position) {
        itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCartItem("removefromcart", "" + products.get(position).getId());
            }
        });
    }


    @Override
    public int getItemCount() {
        if (products == null)
            return 0;
        return products.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.swipe;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public class ProductSummaryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView productImage;
        protected TextView productPrice;
        protected TextView productName;
        protected TextView productShortdescription;
        protected TextView productQuantity;
        //protected SwipeLayout swipeLayout;
        protected ImageView removeItem;
        protected ImageView trash;
        protected ImageView qunatityUpImageView;
        protected ImageView qunatityDownImageView;


        protected CheckBox fav;

        public ProductSummaryHolder(View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.img_productImage);
            productPrice = (TextView) itemView.findViewById(R.id.tv_productPrice);
            productName = (TextView) itemView.findViewById(R.id.tv_productName);
            productShortdescription = (TextView) itemView.findViewById(R.id.tv_product_short_descrption);
            productQuantity = (TextView) itemView.findViewById(R.id.et_quantity);
            fav = (CheckBox) itemView.findViewById(R.id.fav);
            removeItem = (ImageView) itemView.findViewById(R.id.btn_remove);
            //swipeLayout=(SwipeLayout)itemView.findViewById(R.id.swipe);
            trash = (ImageView) itemView.findViewById(R.id.trash);
            qunatityUpImageView = (ImageView) itemView.findViewById(R.id.iv_up);
            qunatityDownImageView = (ImageView) itemView.findViewById(R.id.iv_down);

            removeItem.setOnClickListener(this);
            //swipeLayout.getSurfaceView().setOnClickListener(this);

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
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

    public boolean isLoggedIn() {
        return preferenceService.GetPreferenceBooleanValue(PreferenceService.LOGGED_PREFER_KEY);
    }
}


