package com.bs.dental.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.event.RemoveWishlistItemEvent;
import com.bs.dental.model.KeyValuePair;
import com.bs.dental.model.WishistUpdateResponse;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.response.CartProductListResponse;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.customview.CircleTransformPicasso;
import com.bs.dental.utils.Language;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by bs-110 on 12/24/2015.
 */
public class WishlistAdapter extends CartAdapter {

    private PreferenceService preferenceService;
    public WishlistAdapter(Context context, List productsList, Fragment fragment,PreferenceService preferenceService) {
        super(context, productsList, fragment,preferenceService);
        this.preferenceService=preferenceService;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layout = 0;

        layout = R.layout.item_wish_list;
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(layout, parent, false);
        return new ProductSummaryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder bindViewHolder, final int position) {
        productModel = products.get(position);
        ProductSummaryHolder  holder = (ProductSummaryHolder) bindViewHolder;
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            holder.productName.setRotationY(180);
            holder.productPrice.setRotationY(180);
            holder.productPrice.setGravity(Gravity.RIGHT);
            holder.productName.setGravity(Gravity.RIGHT);
            holder.addToCartBtn.setRotationY(180);

        }

        holder.productName.setText(productModel.getProductName());
//        holder.productName.setEllipsize(TextUtils.TruncateAt.START);
        holder.productPrice.setText(productModel.getUnitPrice());

        Picasso.with(context).load(productModel.getPicture().getImageUrl())
                .transform(new CircleTransformPicasso())
                .fit().centerInside().into(holder.productImage);

        onTrashClicked(holder.removeItem, position);
        onAddtoCartButtonClicked(holder.addToCartBtn, position);

    }

    private void onAddtoCartButtonClicked(Button addToCartBtn, final int position) {

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAddToCartWebService(position);
            }
        });
    }

    private void callAddToCartWebService(int position) {
        List<KeyValuePair> keyValuePairs = new ArrayList<>();

        KeyValuePair keyValuePair = new KeyValuePair();
        keyValuePair.setKey("addtocart");
        keyValuePair.setValue(products.get(position).getId() + "");
        keyValuePairs.add(keyValuePair);

        RetroClient.getApi().addItemsToCartFromWishList(keyValuePairs).enqueue(new CustomCB<CartProductListResponse>());

    }

    private void onTrashClicked(ImageView itemview, final int position) {
        itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position < products.size()) {
                    updateCartItem("removefromcart", "" + products.get(position).getId());
                    //  products.remove(position);
                    //notifyDataSetChanged();
                    /*if (getItemCount() == 0) {
                        EventBus.getDefault().post(new RemoveWishlistItemEvent(0));
                    }*/
                }
            }
        });
    }

    public  void removeItemFromList(int position)
    {
        products.remove(position);
        notifyDataSetChanged();
        if (getItemCount() == 0)
            EventBus.getDefault().post(new RemoveWishlistItemEvent(0));
    }


    public void updateCartItem(String key, String value) {
        List<KeyValuePair> keyValuePairs = new ArrayList<>();
        KeyValuePair keyValuePair = new KeyValuePair();
        keyValuePair.setKey(key);
        keyValuePair.setValue(value);
        keyValuePairs.add(keyValuePair);
        RetroClient.getApi().updateWishList(keyValuePairs).enqueue(new CustomCB<WishistUpdateResponse>(fragment.getView()));
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductSummaryHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        protected ImageView productImage;
        protected TextView productPrice;
        protected TextView productName;
        protected TextView productQuantity;
        protected Button addToCartBtn;
        protected ImageView removeItem;
        protected ImageView trash;
        protected ImageView qunatityUpImageView;
        protected ImageView qunatityDownImageView;



        public ProductSummaryHolder(View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.img_productImage);
            productPrice = (TextView) itemView.findViewById(R.id.tv_productPrice);
            productName = (TextView) itemView.findViewById(R.id.tv_productName);
            productQuantity = (TextView) itemView.findViewById(R.id.et_quantity);
            removeItem = (ImageView) itemView.findViewById(R.id.btn_remove);
            addToCartBtn = (Button) itemView.findViewById(R.id.btn_add_to_cart);
            //swipeLayout=(SwipeLayout)itemView.findViewById(R.id.swipe);
            trash = (ImageView) itemView.findViewById(R.id.trash);
            qunatityUpImageView = (ImageView) itemView.findViewById(R.id.iv_up);
            qunatityDownImageView = (ImageView) itemView.findViewById(R.id.iv_down);

            removeItem.setOnClickListener(this);
            //swipeLayout.getSurfaceView().setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {

            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }


        }

    }

}
