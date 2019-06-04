package com.bs.dental.networking;

import com.bs.dental.model.AddAddressResponse;
import com.bs.dental.model.AppStartRequest;
import com.bs.dental.model.AppThemeResponse;
import com.bs.dental.model.AuthorizePayment;
import com.bs.dental.model.CategoryFeaturedProductAndSubcategoryResponse;
import com.bs.dental.model.ChangePasswordModel;
import com.bs.dental.model.ChangePasswordResponse;
import com.bs.dental.model.ConfirmAutorizeDotNetCheckoutResponse;
import com.bs.dental.model.ConfirmPayPalCheckoutResponse;
import com.bs.dental.model.CustomerAddressResponse;
import com.bs.dental.model.DoctorCustomer;
import com.bs.dental.model.CustomerInfo;
import com.bs.dental.model.CustomerOrdersResponse;
import com.bs.dental.model.CustomerRegistrationInfo;
import com.bs.dental.model.EditAddressResponse;
import com.bs.dental.model.ForgetData;
import com.bs.dental.model.ForgetResponse;
import com.bs.dental.model.IsGuestCheckoutResponse;
import com.bs.dental.model.KeyValuePair;
import com.bs.dental.model.LoginData;
import com.bs.dental.model.LoginDataTurn;
import com.bs.dental.model.LoginResponse;
import com.bs.dental.model.LoginResponseTurn;
import com.bs.dental.model.OrderDetailsResponse;
import com.bs.dental.model.PatientTurn;
import com.bs.dental.model.PatientTurnChartItem;
import com.bs.dental.model.PatientTurnObject;
import com.bs.dental.model.ProductChartObject;
import com.bs.dental.model.ProductTurnObject;
import com.bs.dental.model.RegistrationResponse;
import com.bs.dental.model.RemoveCustomerAddressResponse;
import com.bs.dental.model.Search;
import com.bs.dental.model.WishistUpdateResponse;
import com.bs.dental.networking.postrequest.DiscountCouponRequest;
import com.bs.dental.networking.postrequest.FacebookLogin;
import com.bs.dental.networking.postrequest.PaypalCheckoutRequest;
import com.bs.dental.networking.postrequest.ValuePost;
import com.bs.dental.networking.response.AddtoCartResponse;
import com.bs.dental.networking.response.AdvanceSearchSpinnerOptionResponse;
import com.bs.dental.networking.response.BillingAddressResponse;
import com.bs.dental.networking.response.BillingAddressSaveResponse;
import com.bs.dental.networking.response.CartProductListResponse;
import com.bs.dental.networking.response.CategoryNewResponse;
import com.bs.dental.networking.response.CategoryResponse;
import com.bs.dental.networking.response.CheckoutConfirmResponse;
import com.bs.dental.networking.response.CheckoutOrderSummaryResponse;
import com.bs.dental.networking.response.DiscountCouponApplyResponse;
import com.bs.dental.networking.response.FeaturedCategoryResponse;
import com.bs.dental.networking.response.HomePageBannerResponse;
import com.bs.dental.networking.response.HomePageCategoryResponse;
import com.bs.dental.networking.response.HomePageManufacturerResponse;
import com.bs.dental.networking.response.HomePageProductResponse;
import com.bs.dental.networking.response.OrderTotalResponse;
import com.bs.dental.networking.response.PaymentMethodRetrievalResponse;
import com.bs.dental.networking.response.PaymentMethodSaveResponse;
import com.bs.dental.networking.response.PriceResponse;
import com.bs.dental.networking.response.ProductDetailResponse;
import com.bs.dental.networking.response.ProductsResponse;
import com.bs.dental.networking.response.ReOrderResponse;
import com.bs.dental.networking.response.RelatedProductResponse;
import com.bs.dental.networking.response.ShippingAddressSaveResponse;
import com.bs.dental.networking.response.ShippingMethodRetrievalResponse;
import com.bs.dental.networking.response.ShippingMethodSelttingResponse;
import com.bs.dental.networking.response.ShoppingCartCheckoutAttributeApplyResponse;
import com.bs.dental.networking.response.StateListResponse;
import com.bs.dental.networking.response.StoreAddressResponse;
import com.bs.dental.networking.response.StoreSaveResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by bs156 on 09-Dec-16.
 */

public interface Api {
    String imageSize = "600";
    String manufactureImageSize = "800";
    String queryString = "thumbPictureSize";
    String qs_price = "price";
    String qs_page_number = "pagenumber";
    String qs_spec = "specs";
    int shoppingCartTypeCart = 1;
    int shoppingCartTypeWishlist = 2;
    long cartProductId = 0;

    @POST("AppStart")
    Call<AppThemeResponse> initApp(@Body AppStartRequest appStartRequest);

    @GET("v1/categories")
    Call<CategoryNewResponse> getNewCategory();

    @GET("categories")
    Call<CategoryResponse> getCategory();

    @GET("homepagebanner")
    Call<HomePageBannerResponse> getHomePageBanner(@Query(queryString) String query);

    @GET("homepagecategories")
    Call<HomePageCategoryResponse> getHomePageCategories(@Query(queryString) String query);

    @GET("homepageproducts")
    Call<HomePageProductResponse> getHomePageProducts(@Query(queryString) String query);

    @GET("catalog/homepagecategorieswithproduct")
    Call<FeaturedCategoryResponse> getHomePageCategoriesWithProduct();

    @GET("homepagemanufacture")
    Call<HomePageManufacturerResponse> getHomePageManufacturer(@Query(queryString) String query);

    @GET("Category/{id}")
    Call<ProductsResponse> getProductList(@Path("id") long id, @QueryMap Map<String, String> options);

    @GET("Manufacturer/{manufacturerId}")
    Call<ProductsResponse> getProductListByManufacturer(@Path("manufacturerId") long id, @QueryMap Map<String, String> options);

    @GET("categoryfeaturedproductandsubcategory/{id}")
    Call<CategoryFeaturedProductAndSubcategoryResponse> getCategoryFeaturedProductAndSubcategory(@Path("id") long id);

    @GET("productdetails/{id}")
    Call<ProductDetailResponse> getProductDetails(@Path("id") long id);

    @GET("relatedproducts/{id}?thumbPictureSize=320")
    Call<RelatedProductResponse> getRelatedProducts(@Path("id") long id);

    @POST("ProductDetailsPagePrice/{productId}")
    Call<PriceResponse> getUpdatedPrice(@Path("productId") long id, @Body List<KeyValuePair> list);

    // Get shopping cart
    @POST("AddProductToCart/{productId}/{shoppingCartTypeId}")
    Call<AddtoCartResponse> addProductIntoCart(@Path("productId") long id, @Path("shoppingCartTypeId") long shoppingCartTypeId, @Body List<KeyValuePair> list);

    @GET("ShoppingCart")
    Call<CartProductListResponse> getShoppingCart();

    @POST("ShoppingCart/UpdateCart")
    Call<CartProductListResponse> updateCartProductList(@Body List<KeyValuePair> list);

    @GET("productdetails/{id}")
    Call<ProductDetailResponse> getCartItemProductDetailResponse(@Path("id") long id, @QueryMap Map<String, String> options);

    @POST("ShoppingCart/ApplyDiscountCoupon")
    Call<DiscountCouponApplyResponse> applyDiscountCoupon(@Body DiscountCouponRequest request);

    @GET("ShoppingCart/RemoveDiscountCoupon")
    Call<BaseResponse> removeDiscountCoupon();

    @GET("ShoppingCart/OrderTotal")
    Call<OrderTotalResponse> getOrderTotal();

    @POST("ShoppingCart/applycheckoutattribute")
    Call<ShoppingCartCheckoutAttributeApplyResponse> applyCheckoutAttribute(@Body List<KeyValuePair> list);

    @GET("checkout/billingform")
    Call<BillingAddressResponse> getBillingAddress();

    @GET("country/getstatesbycountryid/{countryId}")
    Call<StateListResponse> getStates(@Path("countryId") String id);

    @POST("checkout/checkoutsaveadress/1")
    Call<BillingAddressSaveResponse> saveBillingAddress(@Body List<KeyValuePair> list);

    @POST("checkout/checkoutsaveadressid/1")
    Call<BillingAddressSaveResponse> saveBillingAddressFromAddress(@Body ValuePost valuePost);

    @GET("checkout/savecheckoutpickuppoint")
    Call<StoreSaveResponse> saveStoreAddress(@Query("pickupPointId") String pickupPointId);

    @GET("checkout/getcheckoutpickuppoints")
    Call<StoreAddressResponse> getStoreAddress();

    @POST("checkout/checkoutsaveadressid/2")
    Call<ShippingAddressSaveResponse> saveShippingAddressFromAddress(@Body ValuePost valuePost);

    @POST("checkout/checkoutsaveadress/2")
    Call<ShippingAddressSaveResponse> saveShippingAddressByForm(@Body List<KeyValuePair> list);

    @GET("checkout/checkoutgetshippingmethods")
    Call<ShippingMethodRetrievalResponse> getShippingMethod();

    @POST("checkout/checkoutsetshippingmethod")
    Call<ShippingMethodSelttingResponse> setShippingMethod(@Body ValuePost valuePost);

    @GET("checkout/checkoutgetpaymentmethod")
    Call<PaymentMethodRetrievalResponse> getPaymentMethod();

    @POST("checkout/checkoutsavepaymentmethod")
    Call<PaymentMethodSaveResponse> saveCheckoutPaymentMethod(@Body ValuePost valuePost);

    // Customer
    @GET("customer/info")
    Call<CustomerInfo> getCustomerInfo();

    @POST("customer/info")
    Call<CustomerInfo> saveCustomerInfo(@Body CustomerInfo customerInfo);

    @POST("login")
    Call<LoginResponse> performLogin(@Body LoginData loginData);


    @POST("customer/passwordrecovery")
    Call<ForgetResponse> forgetPassword(@Body ForgetData forgetData);

    @POST("customer/register")
    Call<RegistrationResponse> preformRegistration(@Body CustomerRegistrationInfo customerRegistrationInfo);

    @GET("shoppingcart/checkoutorderinformation")
    Call<CheckoutOrderSummaryResponse> getCheckoutOrderSummary();

    @GET("checkout/checkoutcomplete")
    Call<CheckoutConfirmResponse> confirmCheckout();

    @POST("checkout/checkpaypalaccount")
    Call<ConfirmPayPalCheckoutResponse> confirmPayPalCheckout(@Body PaypalCheckoutRequest request);

    @GET("customer/addresses")
    Call<CustomerAddressResponse> getCustomerAddresses();

    @POST("customer/address/edit/{id}")
    Call<EditAddressResponse> editAddress(@Path("id") int id, @Body List<KeyValuePair> list);

    @POST("customer/address/add")
    Call<AddAddressResponse> addAddress(@Body List<KeyValuePair> keyValuePairs);

    @GET("customer/address/remove/{id}")
    Call<RemoveCustomerAddressResponse> removeCustomerAddresses(@Path("id") int id);

    @GET("order/customerorders")
    Call<CustomerOrdersResponse> getCustomerOrders();

    @GET("order/details/{id}")
    Call<OrderDetailsResponse> getOrderDetails(@Path("id") int id);

    @GET("order/reorder/{id}")
    Call<ReOrderResponse> getReOrder(@Path("id") int id);

    @POST("customer/changepass")
    Call<ChangePasswordResponse> changePassword(@Body ChangePasswordModel changePassword);

    @GET("checkout/opccheckoutforguest")
    Call<IsGuestCheckoutResponse> getIsGuestCheckout();

    @GET("shoppingCart/wishlist")
    Call<CartProductListResponse> getWishList();

    @POST("facebookLogin")
    Call<LoginResponse> loginUsingFaceBook(@Body FacebookLogin facebookLogin);

    @POST("ShoppingCart/UpdateWishlist")
    Call<WishistUpdateResponse> updateWishList(@Body List<KeyValuePair> keyValuePairs);

    @POST("ShoppingCart/AddItemsToCartFromWishlist")
    Call<CartProductListResponse> addItemsToCartFromWishList(@Body List<KeyValuePair> keyValuePairs);

    @POST("ShoppingCart/AddItemsToCartFromWishlist")
    Call<CartProductListResponse> addAllItemsToCartFromWishList(@Body List<KeyValuePair> keyValuePairs);

    @POST("checkout/checkauthorizepayment")
    Call<ConfirmAutorizeDotNetCheckoutResponse> checkAuthorizePayment(@Body AuthorizePayment authorizePayment);

    @POST("catalog/search")
    Call<ProductsResponse> searchProduct(@Body Search q);

    @GET("categoriesNmanufactures/search")
    Call<AdvanceSearchSpinnerOptionResponse> getAdvanceSearchOptions();

    //*** added by mmkr

    @POST("AccountApi/Login")
    Call<LoginResponseTurn> performLoginTurn(@Body LoginDataTurn body);

    @GET("PatientTurnApi/GetPatientTurns")
    Call<PatientTurnObject> getPatientTurns(@Header("cookie") String cookie);

    @GET("ProductApi/GetProducts")
    Call<ProductTurnObject> getProductsTurn(@Header("cookie") String cookie);

    @GET("PatientTurnApi/GetPatientTurnsChart")
    Call<List<PatientTurnChartItem>> getPatientTurnsChart(@Header("cookie") String cookie);

    @GET("ProductApi/GetCountProductsChart")
    Call<List<ProductChartObject>> getCountProductsChart(@Header("cookie") String cookie);
}
