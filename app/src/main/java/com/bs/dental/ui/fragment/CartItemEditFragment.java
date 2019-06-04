package com.bs.dental.ui.fragment;

import com.bs.dental.R;
import com.bs.dental.model.CartProduct;
import com.bs.dental.model.KeyValuePair;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.response.AddtoCartResponse;
import com.bs.dental.networking.response.ProductDetailResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ashraful on 12/3/2015.
 */
public class CartItemEditFragment extends ProductDetailFragment {

   public static CartProduct cartProduct;

    private void setNamePrice()
    {
        productNameTextview.setText(cartProduct.getProductName());
        productPriceTextview.setText(cartProduct.getUnitPrice());
        addtoCartBtn.setText(R.string.update_cart_item);
    }

    @Override
    public  void initializeView()
    {
        setNamePrice();
        initailizeExpandCollapseResource();
        RelatedProductList.setLayoutManager(getLinearLayoutManager());
    }

    @Override
    public void callApiOfAddingProductIntoCart(int cartTypeId) {
        KeyValuePair keyValuePair =new KeyValuePair();
        keyValuePair.setKey("addtocart_"+cartProduct.getProductId()+".UpdatedShoppingCartItemId");
        keyValuePair.setValue(makeString(cartProduct.getId()));
        List<KeyValuePair> productAttributes = productAttributeViews.getProductAttribute();

        productAttributes.add(keyValuePair);
        RetroClient.getApi()
                .addProductIntoCart(productModel.getId(), cartTypeId, productAttributes)
                .enqueue(new CustomCB<AddtoCartResponse>(this.getView()));
    }





    @Override
    protected void callWebService() {
        // @QueryMap Map<String, String> options
        Map<String, String> options=new HashMap<>();
        options.put("updatecartitemid",""+cartProduct.getId());
        RetroClient.getApi().getCartItemProductDetailResponse(cartProduct.getProductId(),options)
                .enqueue(new CustomCB<ProductDetailResponse>(this.getView()));
    }
    private String makeString(long value)
    {
        return  ""+value;
    }

}
