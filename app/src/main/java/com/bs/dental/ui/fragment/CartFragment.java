package com.bs.dental.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bs.dental.R;
import com.bs.dental.model.CartProduct;
import com.bs.dental.model.IsGuestCheckoutResponse;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.postrequest.DiscountCouponRequest;
import com.bs.dental.networking.response.CartProductListResponse;
import com.bs.dental.networking.response.DiscountCouponApplyResponse;
import com.bs.dental.networking.response.OrderTotalResponse;
import com.bs.dental.networking.response.ShoppingCartCheckoutAttributeApplyResponse;
import com.bs.dental.service.PreferenceService;
import com.bs.dental.ui.adapter.CartAdapter;
import com.bs.dental.ui.customview.CustomLinearLayoutManager;
import com.bs.dental.ui.views.CheckoutAttributeView;
import com.bs.dental.utils.Language;
import com.google.inject.Inject;

import java.util.List;

import roboguice.fragment.RoboDialogFragment;
import roboguice.inject.InjectView;

/**
 * Created by Ashraful on 11/30/2015.
 */
public class CartFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.rclv_cartList)
    RecyclerView cartproductRecyclerList;
    @InjectView(R.id.btn_proceed_to_Checkout)
    Button checkoutBtn;
    @InjectView(R.id.btn_apply_coupon)
    Button applyCouponBtn;

    @InjectView(R.id.tv_subtotal)
    TextView subTotalTextView;
    @InjectView(R.id.tv_shipping)
    TextView shippingTextView;
    @InjectView(R.id.tv_tax)
    TextView taxTextView;
    @InjectView(R.id.tv_Total)
    TextView totalAmountTextView;
    @InjectView(R.id.tv_discount)
    TextView discountTextView;

    @InjectView(R.id.tr_discount)
    TableRow discountTableRow;

    @InjectView(R.id.taxRow)
    TableRow taxRow;

    @InjectView(R.id.dynamicAttributeLayout)
    protected LinearLayout dynamicAttributeLayout;
    @InjectView(R.id.et_coupon_code)
    EditText couponCodeEditText;

    @InjectView(R.id.cv_product_attribute)
    CardView productAttributeCardView;

    /*@InjectView(R.id.table_orderTotal)
    TableLayout orderSummaryRelativeLayout;*/

    @InjectView(R.id.ll_cartInfoLayout)
    LinearLayout CartInfoLinearLayout;

    @InjectView(R.id.discountlayout)
    CardView discountlayout;

    protected CheckoutAttributeView checkoutAttributeView;

    String checkoutTabTag = "CheckoutTab";
    CustomLinearLayoutManager layoutManager;
    private static boolean isGuestCheckout;

    @InjectView(R.id.table_orderTotal)
    TableLayout table_orderTotal;

    @InjectView(R.id.coupon_header)
    TextView coupon_header;

    @Inject
    PreferenceService preferenceService;

    @InjectView(R.id.linearDiscount)
    LinearLayout linearDiscount;

    @InjectView(R.id.taxKey)
    TextView taxKey;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // hide imageview in first fragment
        ImageView iv_toolbar = (ImageView) getActivity().findViewById(R.id.iv_toolbar);
        iv_toolbar.setVisibility(View.GONE);

        getActivity().setTitle(getString(R.string.shopping_cart));
        checkEventBusRegistration();
        setLayoutManagerofRecyclerList();
        setClickListenerOnView();
        callWebservice();
        callCheckIsGuestCheckoutWebService();
        if (preferenceService.GetPreferenceBooleanValue(PreferenceService.taxShow)) {
            taxRow.setVisibility(View.VISIBLE);
        } else {
            taxRow.setVisibility(View.GONE);
        }

        if (preferenceService.GetPreferenceBooleanValue(PreferenceService.discuntShow)) {
            discountlayout.setVisibility(View.VISIBLE);
        } else {
            discountlayout.setVisibility(View.GONE);
        }
        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)) {
            table_orderTotal.setGravity(Gravity.RIGHT);
            cartproductRecyclerList.setRotationY(180);
        }
    }

    private void callCheckIsGuestCheckoutWebService() {
        RetroClient.getApi().getIsGuestCheckout().enqueue(new CustomCB<IsGuestCheckoutResponse>());
    }

    public void onEvent(IsGuestCheckoutResponse response) {
        if (response.getStatusCode() == 200) {
            this.isGuestCheckout = response.isData();
        }
    }

    private void setLayoutManagerofRecyclerList() {
        layoutManager = new CustomLinearLayoutManager(getActivity(), CustomLinearLayoutManager.VERTICAL, false);
        cartproductRecyclerList.setHasFixedSize(true);
        cartproductRecyclerList.setLayoutManager(layoutManager);
    }

    private void setClickListenerOnView() {
        applyCouponBtn.setOnClickListener(this);
        checkoutBtn.setOnClickListener(this);
    }

    public void callWebservice() {
        RetroClient.getApi().getShoppingCart().enqueue(new CustomCB<CartProductListResponse>(this.getView()));

//        RestClient.get().getOrderTotal(new CustomCallback<OrderTotalResponse>(this.getView()));
    }

    public void onEvent(CartProductListResponse cartProductListResponse) {
        if (cartProductListResponse != null && cartProductListResponse.getItems() != null) {
            if (cartProductListResponse.getItems().size() > 0) {
                Utility.setCartCounter(cartProductListResponse.getCount());
                CartInfoLinearLayout.setVisibility(View.VISIBLE);
                //RestClient.get().getOrderTotal(new CustomCallback<OrderTotalResponse>(getView()));

                populatedDatainAdapter(cartProductListResponse.getItems());
                populateViewOfDynamicAttributeLayout(cartProductListResponse);

                populateDataInOrderTotalLayout(cartProductListResponse.getOrderTotalResponseModel());

            } else {
                Utility.setCartCounter(0);
                Snackbar.make(getView(), R.string.cart_is_empty, Snackbar.LENGTH_SHORT).show();
                getFragmentManager().popBackStackImmediate();

            }
        }

    }

    public void populatedDatainAdapter(List<CartProduct> cartProductList) {
        CartAdapter cartAdapter = new CartAdapter(getActivity(), cartProductList, this, preferenceService);
        cartproductRecyclerList.setAdapter(cartAdapter);
        makeActionOnCartItemClick(cartAdapter);
    }


    protected void populateViewOfDynamicAttributeLayout(CartProductListResponse cartProductListResponse) {
        if (cartProductListResponse.getCheckoutAttributes().size() > 0) {
            productAttributeCardView.setVisibility(View.VISIBLE);
            dynamicAttributeLayout.setVisibility(View.VISIBLE);
        } else
            productAttributeCardView.setVisibility(View.GONE);
        checkoutAttributeView =
                new CheckoutAttributeView(getActivity(), cartProductListResponse.getCheckoutAttributes(), dynamicAttributeLayout);
    }

    public void onEvent(OrderTotalResponse orderTotalResponse) {
        if (orderTotalResponse.getStatusCode() == 200) {
            populateDataInOrderTotalLayout(orderTotalResponse);
        }
    }

    private void populateDataInOrderTotalLayout(OrderTotalResponse orderTotalRespons) {

        subTotalTextView.setText(orderTotalRespons.getSubTotal());
        shippingTextView.setText(orderTotalRespons.getShipping());
        taxTextView.setText(orderTotalRespons.getTax());
        totalAmountTextView.setText(orderTotalRespons.getOrderTotal());

        if (preferenceService.GetPreferenceValue(PreferenceService.CURRENT_LANGUAGE).equalsIgnoreCase(Language.ARABIC)) {
            subTotalTextView.setGravity(Gravity.LEFT);
            shippingTextView.setGravity(Gravity.LEFT);
            taxTextView.setGravity(Gravity.LEFT);
            totalAmountTextView.setGravity(Gravity.LEFT);
            taxKey.setGravity(Gravity.RIGHT);
        }
        if (orderTotalRespons.getOrderTotalDiscount() != null) {
            discountTableRow.setVisibility(View.VISIBLE);
            discountTextView.setText(orderTotalRespons.getOrderTotalDiscount());
        } else
            discountTableRow.setVisibility(View.GONE);

        if (orderTotalRespons.getOrderTotal() == null) {
            totalAmountTextView.setText(R.string.calculated_during_checkout);
            totalAmountTextView.setTextColor(Color.RED);
        }
        if (orderTotalRespons.getShipping() == null) {
            shippingTextView.setText(R.string.calculated_during_checkout);
            shippingTextView.setTextColor(Color.RED);
        }

    }


    private void makeActionOnCartItemClick(final CartAdapter cartAdapter) {
        cartAdapter.SetOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CartItemEditFragment.cartProduct = cartAdapter.products.get(position);
                getFragmentManager().beginTransaction().replace
                        (R.id.container, new CartItemEditFragment()).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int resourceId = v.getId();
        if (resourceId == R.id.btn_proceed_to_Checkout) {
            if (!preferenceService.GetPreferenceBooleanValue(PreferenceService.LOGGED_PREFER_KEY)) {
                showCheckOutOptionsDialogFragment();
            } else {
                getFragmentManager().beginTransaction().replace(R.id.container,
                        new CheckoutStepFragment())
                        .addToBackStack(null).commit();
            }
        } else if (resourceId == R.id.btn_apply_coupon) {
            applyCouponApi();
        }

    }

    private void applyCouponApi() {
        DiscountCouponRequest request = new DiscountCouponRequest();
        request.setValue(couponCodeEditText.getText().toString().trim());
        RetroClient.getApi().applyDiscountCoupon(request)
                .enqueue(new CustomCB<DiscountCouponApplyResponse>(this.getView()));
    }

    public void onEvent(DiscountCouponApplyResponse discountCouponApplyResponse) {
        if (discountCouponApplyResponse.getStatusCode() == 200) {
            Toast.makeText(getActivity(), R.string.discount_success_msg, Toast.LENGTH_SHORT).show();
            //RestClient.get().getOrderTotal(new CustomCallback<OrderTotalResponse>(getView()));
            populateDataInOrderTotalLayout(discountCouponApplyResponse.getOrderTotalResponseModel());
        }
    }

    public void onEvent(ShoppingCartCheckoutAttributeApplyResponse applyResponse) {
        if (applyResponse.getStatusCode() == 200) {
            //RestClient.get().getOrderTotal(new CustomCallback<OrderTotalResponse>(getView()));
            // populateDataInOrderTotalLayout(applyResponse.getOrderTotalResponseModel());
        }
    }

    private void showCheckOutOptionsDialogFragment() {
        DialogFragment newFragment = new GuestCheckoutFragment();
        newFragment.show(getFragmentManager(), "dialog");
    }

    public static class GuestCheckoutFragment extends RoboDialogFragment implements View.OnClickListener {

        @InjectView(R.id.guest_checkout_layout)
        View guestCheckoutLayout;

        @InjectView(R.id.new_customer_checkout_layout)
        View newCustomerCheckoutLayout;

        @InjectView(R.id.guest_checkout_button)
        Button gusetCheckoutButton;
        @InjectView(R.id.login_button)
        Button loginButton;
        @InjectView(R.id.register_button)
        Button registerButton;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.guest_checkout_dialog_fragment, container, false);
            return v;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (isGuestCheckout) {
                newCustomerCheckoutLayout.setVisibility(View.GONE);
            } else {
                guestCheckoutLayout.setVisibility(View.GONE);
            }

            gusetCheckoutButton.setOnClickListener(this);
            registerButton.setOnClickListener(this);
            loginButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Fragment fragment = new LoginFragment();
            switch (v.getId()) {
                case R.id.login_button:
                    break;
                case R.id.register_button:
                    fragment = new RegisterFragment();
                    break;
                case R.id.guest_checkout_button:
                    fragment = new CheckoutStepFragment();
                    break;
            }

            getFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null).commit();

            dismiss();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        String SCREEN_NAME = "Cart";
        //pushAnalyticsEvent(SCREEN_NAME);

    }

    public boolean isLoggedIn() {
        return preferenceService.GetPreferenceBooleanValue(PreferenceService.LOGGED_PREFER_KEY);
    }


}