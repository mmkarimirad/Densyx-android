package com.bs.dental.networking.response;

import com.bs.dental.model.AvailableSortOption;
import com.bs.dental.model.ProductFilterItem;
import com.bs.dental.model.FilterItem;
import com.bs.dental.model.ProductModel;
import com.bs.dental.networking.BaseResponse;
import com.bs.dental.model.PriceRange;
import com.bs.dental.utils.AppUtils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashraful on 11/11/2015.
 */
public class ProductsResponse extends BaseResponse {
    @SerializedName("Name") private String name;
    @SerializedName("PriceRange") private PriceRange priceRange;
    @SerializedName("Products") private List<ProductModel> products;
    @SerializedName("NotFilteredItems") private List<FilterItem> notFilteredItems;
    @SerializedName("AlreadyFilteredItems") private List<FilterItem> alreadyFilteredItems;
    @SerializedName("AvailableSortOptions") private List<AvailableSortOption> availableSortOptions;
    @SerializedName("TotalPages") private int totalPages;
    @SerializedName("FilterItems") private List<ProductFilterItem> filterItems;

    public String getName() {
        return AppUtils.getNullSafeString(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public PriceRange getPriceRange() {
        if (priceRange == null) {
            priceRange = new PriceRange();
        }
        return priceRange;
    }

    public void setPriceRange(PriceRange priceRange) {
        this.priceRange = priceRange;
    }

    public List<ProductModel> getProducts() {
        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

    public List<FilterItem> getNotFilteredItems() {
        return notFilteredItems;
    }

    public void setNotFilteredItems(List<FilterItem> notFilteredItems) {
        this.notFilteredItems = notFilteredItems;
    }

    public List<FilterItem> getAlreadyFilteredItems() {
        return alreadyFilteredItems;
    }

    public void setAlreadyFilteredItems(List<FilterItem> alreadyFilteredItems) {
        this.alreadyFilteredItems = alreadyFilteredItems;
    }

    public List<AvailableSortOption> getAvailableSortOptions() {
        return availableSortOptions;
    }

    public void setAvailableSortOptions(List<AvailableSortOption> availableSortOptions) {
        this.availableSortOptions = availableSortOptions;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ProductFilterItem> getFilterItems() {
        return filterItems;
    }

    public void setFilterItems(List<ProductFilterItem> filterItems) {
        this.filterItems = filterItems;
    }
}
