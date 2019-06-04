package com.bs.dental.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bs.dental.R;
import com.bs.dental.event.RemoveWishlistItemEvent;
import com.bs.dental.model.CartProduct;
import com.bs.dental.model.KeyValuePair;
import com.bs.dental.model.WishistUpdateResponse;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.response.CartProductListResponse;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.adapter.WishlistAdapter;
import com.bs.dental.ui.customview.CustomLinearLayoutManager;
import com.bs.dental.utils.Language;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import roboguice.inject.InjectView;

/**
 * Created by bs-110 on 12/24/2015.
 */
public class WishlistFragment extends BaseFragment {

    @InjectView(R.id.btn_add_all_items_to_cart)
    Button addAllItemsToCartBtn;

    @InjectView(R.id.rclv_wish_list)
    RecyclerView wishRecyclerList;

    CustomLinearLayoutManager layoutManager;

    @Inject
    PreferenceService preferenceService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wishlist,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(getString(R.string.wishlist));
        setLayoutManagerofRecyclerList();
        checkEventBusRegistration();
        callWebservice();
        wishRecyclerList.setNestedScrollingEnabled(false);
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)){
            wishRecyclerList.setRotationY(180);
        }
        addAllItemsToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemsToCart();
            }
        });

    }

    public void onEvent(WishistUpdateResponse response){
        if(response.getStatusCode() != 200){
            showSnack(getString(R.string.error_removing_item));
        } else {
            populatedDatainAdapter(response.getItems());
        }
    }

    public void callWebservice() {
        RetroClient.getApi().getWishList().enqueue(new CustomCB<CartProductListResponse>(this.getView()));
    }

    public void onEvent(CartProductListResponse cartProductListResponse)
    {
        if(cartProductListResponse!=null && cartProductListResponse.getItems()!=null) {
            if (cartProductListResponse.getItems().size() > 0) {
                populatedDatainAdapter(cartProductListResponse.getItems());


            } else {
                wishListAdapter=new WishlistAdapter(getActivity(),new ArrayList(),this,preferenceService);
                wishRecyclerList.setAdapter(wishListAdapter);
                addAllItemsToCartBtn.setVisibility(View.GONE);
                Snackbar.make(getView(), R.string.wishlist_empty, Snackbar.LENGTH_SHORT).show();
            }

            if(cartProductListResponse.getCount()>0)
                Utility.setCartCounter(cartProductListResponse.getCount());
        }

    }

    private void addItemsToCart(){
        if(wishListProduct.size() > 0) {
            callWebService();
        }
    }

    private void callWebService(){
        List<KeyValuePair> keyValuePairs = new ArrayList<>();
        for (CartProduct cp: wishListProduct) {
            KeyValuePair keyValuePair = new KeyValuePair();
            keyValuePair.setKey("addtocart");
            keyValuePair.setValue(cp.getId()+"");
            keyValuePairs.add(keyValuePair);
        }

        RetroClient.getApi().addAllItemsToCartFromWishList(keyValuePairs)
                .enqueue(new CustomCB<CartProductListResponse>(getView()));
    }



    List<CartProduct> wishListProduct;
    WishlistAdapter wishListAdapter;
    public void populatedDatainAdapter(List<CartProduct> cartProductList)
    {
        this.wishListProduct = new ArrayList<>();
        this.wishListProduct.addAll(cartProductList);
        wishListAdapter=new WishlistAdapter(getActivity(),this.wishListProduct,this,preferenceService);
        wishRecyclerList.setAdapter(wishListAdapter);

    }

    private void setLayoutManagerofRecyclerList()
    {
        layoutManager=new CustomLinearLayoutManager(getActivity(), CustomLinearLayoutManager.VERTICAL,false);
        wishRecyclerList.setHasFixedSize(true);
        wishRecyclerList.setLayoutManager(layoutManager);
    }

    public void onEvent(RemoveWishlistItemEvent event){
        if(event.getCount() == 0) {
            addAllItemsToCartBtn.setVisibility(View.GONE);
            showSnack(getString(R.string.wishlist_empty));
        }
    }

  /*

    public void onEvent(AddItemsToCartFromWishlistResponse response)
    {
        if(response.getStatusCode() == 200){
            Utility.setCartCounter(response.getCount());
          //  populatedDatainAdapter(new ArrayList<CartProduct>());
           // addAllItemsToCartBtn.setVisibility(View.GONE);
            showSnack("Item added to cart");
        } else {
            showSnack("Something went wrong. Please try again later");
        }

    }

    public  void onEvent(AddAllItemsToCartFromWishlistResponse response)
    {
        if(response.getStatusCode() == 200){

            Utility.setCartCounter(response.getCount());
            showSnack("Items added to cart");
            addAllItemsToCartBtn.setVisibility(View.GONE);
            wishListAdapter=new WishlistAdapter(getActivity(),new ArrayList(),this);
             wishRecyclerList.setAdapter(wishListAdapter);
            Snackbar.make(getView(), "Wishlist is Empty", Snackbar.LENGTH_SHORT).show();


        } else {
            showSnack("Something went wrong. Please try again later");
        }
    }
   @InjectView(R.id.table_orderTotal)
    TableLayout orderSummaryRelativeLayout;

    @InjectView(R.id.cv_orderTotal)
    CardView orderTotalCardView;

    @InjectView(R.id.ll_order_totla)
    LinearLayout orderToalLinearLayout;

    @InjectView(R.id.ll_cartInfoLayout)
    LinearLayout CartInfoLinearLayout;

    @InjectView(R.id.coupon_layout)
    RelativeLayout couponLayout;

    @InjectView(R.id.btn_proceed_to_Checkout)
    Button checkoutBtn;

    @InjectView(R.id.rclv_cartList)
    RecyclerView cartproductRecyclerList;

    @InjectView(R.id.cv_product_attribute)
    CardView productAttributeCardView;

    CustomLinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Wishlist");
        setLayoutManagerofRecyclerList();

        checkEventBusRegistration();
        callWebservice();

        orderTotalCardView.setVisibility(View.GONE);
        couponLayout.setVisibility(View.GONE);
        checkoutBtn.setText("ADD TO CART");

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemsToCart();
            }
        });

    }

    public void onEvent(WishistUpdateResponse response){
        if(response.getStatusCode() != 200){
            showSnack("Error removing item. Check your internet connection.");
        } else {

        }
    }

    public void callWebservice() {
        RestClient.get().getWishlist(new CustomCallback<CartProductListResponse>(this.getView()));
    }

    public void onEvent(CartProductListResponse cartProductListResponse)
    {
        if(cartProductListResponse!=null && cartProductListResponse.getItems()!=null) {
            if (cartProductListResponse.getItems().size() > 0) {
                populatedDatainAdapter(cartProductListResponse.getItems());
                CartInfoLinearLayout.setVisibility(View.VISIBLE);
                couponLayout.setVisibility(View.GONE);
                orderTotalCardView.setVisibility(View.GONE);
                productAttributeCardView.setVisibility(View.GONE);
            } else {
                Utility.setCartCounter(0);
                Snackbar.make(getView(), "Wishlist is Empty", Snackbar.LENGTH_SHORT).show();
            }
        }

    }

    private void addItemsToCart(){
        if(wishListProduct.size() > 0) {
            callWebService();
        }
    }

    private void callWebService(){
        List<KeyValuePair> keyValuePairs = new ArrayList<>();
        for (CartProduct cp: wishListProduct) {
            KeyValuePair keyValuePair = new KeyValuePair();
            keyValuePair.setKey("addtocart");
            keyValuePair.setValue(cp.getId()+"");
            keyValuePairs.add(keyValuePair);
        }
        RestClient.get().addItemsToCartFromWishlist(keyValuePairs, new CustomCallback<AddItemsToCartFromWishlistResponse>(getView()));
    }

    public void onEvent(AddItemsToCartFromWishlistResponse response)
    {
        if(response.getStatusCode() == 200){
            Utility.setCartCounter(response.getCount());
            populatedDatainAdapter(new ArrayList<CartProduct>());
            checkoutBtn.setVisibility(View.GONE);
            showSnack("Items added to cart");
        } else {
            showSnack("Something went wrong. Please try again later");
        }

    }

    List<CartProduct> wishListProduct;
    WishlistAdapter wishListAdapter;
    public void populatedDatainAdapter(List<CartProduct> wishListProduct)
    {
        this.wishListProduct = new ArrayList<>();
        this.wishListProduct.addAll(wishListProduct);
        wishListAdapter=new WishlistAdapter(getActivity(),this.wishListProduct,this);
        cartproductRecyclerList.setAdapter(wishListAdapter);

    }

    private void setLayoutManagerofRecyclerList()
    {
        layoutManager=new CustomLinearLayoutManager(getActivity(), CustomLinearLayoutManager.VERTICAL,false);
        cartproductRecyclerList.setHasFixedSize(true);
        cartproductRecyclerList.setLayoutManager(layoutManager);
    }

    public void onEvent(RemoveWishlistItemEvent event){
        if(event.getCount() == 0) {
            checkoutBtn.setVisibility(View.GONE);
            showSnack("Wishlist is empty");
        }
    }
*/}
