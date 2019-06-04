package com.bs.dental.ui.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bs.dental.MainActivity;
import com.bs.dental.R;
import com.bs.dental.model.AdvanceSearchSpinnerOption;
import com.bs.dental.model.ProductModel;
import com.bs.dental.model.Search;
import com.bs.dental.model.ViewType;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.response.AdvanceSearchSpinnerOptionResponse;
import com.bs.dental.networking.response.ProductsResponse;
import com.bs.dental.ui.adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;

/**
 * Created by bs156 on 16-Feb-17.
 */

public class SearchFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {

    @InjectView(R.id.accb_advance_search)
    AppCompatCheckBox advanceSearchCheckBox;

    @InjectView(R.id.rv_product_list)
    RecyclerView productListRecyclerView;

    @InjectView(R.id.rl_rootLayout)
    RelativeLayout rootViewRelativeLayout;

    @InjectView(R.id.layout_advance_search)
    View advanceSearchLayout;

    @InjectView(R.id.spinner_category)
    AppCompatSpinner categorySpinner;

    @InjectView(R.id.accb_search_in_subcategory)
    AppCompatCheckBox searchInSubCategory;

    @InjectView(R.id.spinner_manufacturer)
    AppCompatSpinner manufacturerSpinner;

    @InjectView(R.id.et_price_from)
    EditText priceFromEditText;

    @InjectView(R.id.et_price_to)
    EditText priceToEditText;

    @InjectView(R.id.spinner_search_in_description)
    AppCompatCheckBox searchDescription;

    @InjectView(R.id.btn_search)
    AppCompatButton searchButton;

    GridLayoutManager layoutManager;
    SearchAdapter productAdapter;
    private int newSpanCount = 1;
    int itemViewType = ViewType.GRID;
    private SearchView searchView;

    private ArrayAdapter categorySpinnerAdapter;
    private List<AdvanceSearchSpinnerOption> categoryList;

    private ArrayAdapter manufacturerSpinAdapter;
    private List<AdvanceSearchSpinnerOption> manufacturerList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkEventBusRegistration();

        setupSearchCategorySpinner();
        setupSearchManufacturerSpinner();
        getAdvanceSearchOptions();


        advanceSearchCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    advanceSearchLayout.setVisibility(View.VISIBLE);
                } else {
                    advanceSearchLayout.setVisibility(View.GONE);
                }
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchProduct();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
        inflater.inflate(R.menu.menu_product_search_fragment, menu);

        MenuItem item = menu.findItem(R.id.action_search);

        try {
            searchView = new SearchView(((MainActivity) getActivity())
                    .getSupportActionBar().getThemedContext());

            MenuItemCompat.setShowAsAction(
                    item,
                    MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW
                            | MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
            MenuItemCompat.setActionView(item, searchView);

            if (productAdapter == null) {
                searchView.setQueryHint(getString(R.string.search_product));
            }

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchProduct();
                    getActivity().setTitle(query);
                    searchView.clearFocus();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

            searchView.setIconified(false);
            item.expandActionView();

            MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    return false;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    getFragmentManager().popBackStack();
                    return true;
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setupSearchCategorySpinner() {
        categoryList = new ArrayList<>();
        categoryList.add(new AdvanceSearchSpinnerOption(0, getString(R.string.all)));
        categorySpinner.setOnItemSelectedListener(this);
        categorySpinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.simple_spinner_item_black_color, categoryList);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categorySpinnerAdapter);
    }

    private void setupSearchManufacturerSpinner() {
        manufacturerList = new ArrayList<>();
        manufacturerList.add(new AdvanceSearchSpinnerOption(0, getString(R.string.all)));
        manufacturerSpinner.setOnItemSelectedListener(this);

        manufacturerSpinAdapter = new ArrayAdapter<>(getContext(), R.layout.simple_spinner_item_black_color, manufacturerList);
        manufacturerSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        manufacturerSpinner.setAdapter(manufacturerSpinAdapter);
    }

    private void getAdvanceSearchOptions() {
        RetroClient.getApi().getAdvanceSearchOptions()
                .enqueue(new CustomCB<AdvanceSearchSpinnerOptionResponse>());
    }

    public void onEvent(AdvanceSearchSpinnerOptionResponse spinnerOptionResponse) {
        if (spinnerOptionResponse != null && spinnerOptionResponse.getStatusCode() == 200) {
            if (spinnerOptionResponse.getCategories() != null && spinnerOptionResponse.getCategories().size() > 0) {
                if (categoryList == null) {
                    categoryList = new ArrayList<>();
                } else {
                    categoryList.clear();
                }

                categoryList.add(new AdvanceSearchSpinnerOption(0, getString(R.string.all)));
                categoryList.addAll(spinnerOptionResponse.getCategories());

                if (categorySpinnerAdapter != null) {
                    categorySpinnerAdapter.notifyDataSetChanged();
                }
            }

            if (spinnerOptionResponse.getManufacturer() != null && spinnerOptionResponse.getManufacturer().size() > 0) {
                if (manufacturerList == null) {
                    manufacturerList = new ArrayList<>();
                } else {
                    manufacturerList.clear();
                }

                manufacturerList.add(new AdvanceSearchSpinnerOption(0, getString(R.string.all)));
                manufacturerList.addAll(spinnerOptionResponse.getManufacturer());

                if (manufacturerSpinAdapter != null) {
                    manufacturerSpinAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void calculateAutomaticGridColumn() {
        layoutManager = new GridLayoutManager(getActivity(), 1);


        productListRecyclerView.setLayoutManager(layoutManager);

        productListRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT < 16) {
                            productListRecyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            productListRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                        int viewWidth = productListRecyclerView.getMeasuredWidth();
                        float cardViewWidth =
                                getActivity().getResources().getDimension(R.dimen.cardviewWidth);
                        newSpanCount = (int) Math.floor(viewWidth / cardViewWidth);
                        if (itemViewType == ViewType.GRID) {
                            updateColumnInPerRow(newSpanCount);
                        }
                    }
                });
    }

    private void updateColumnInPerRow(int numberOfColumnPerRow) {
        layoutManager.setSpanCount(numberOfColumnPerRow);
        layoutManager.requestLayout();
    }

    public void searchProduct() {
        if (productAdapter != null) {
            productAdapter.clearList();
        }

        if (searchView != null) {
            String query = searchView.getQuery().toString();

            if (query.length() > 2) {
                RetroClient.getApi().searchProduct(getSearchObject())
                        .enqueue(new CustomCB<ProductsResponse>(rootViewRelativeLayout));
            } else {
                Toast.makeText(getContext(), R.string.search_limit, Toast.LENGTH_LONG).show();
            }
        }
    }

    private Search getSearchObject() {
        Search search = new Search();
        if (searchView != null) {
            search.setQuery(searchView.getQuery().toString());
        }

        if (advanceSearchCheckBox.isChecked()) {
            search.setAdvanceSearchSelected(true);

            if (categorySpinnerAdapter != null) {
                AdvanceSearchSpinnerOption option = (AdvanceSearchSpinnerOption) categorySpinner.getSelectedItem();
                search.setCategoryId(option.getId() + "");
            } else {
                search.setCategoryId("0");
            }

            search.setSearchInSubcategory(searchInSubCategory.isChecked());

            if (manufacturerSpinAdapter != null) {
                AdvanceSearchSpinnerOption option = (AdvanceSearchSpinnerOption) manufacturerSpinner.getSelectedItem();
                search.setManufacturerId(option.getId() + "");
            } else {
                search.setManufacturerId("0");
            }

            search.setPriceFrom(priceFromEditText.getText().toString().trim());
            search.setPriceTo(priceToEditText.getText().toString().trim());

            search.setSearchInDescription(searchDescription.isChecked());
        }

        return search;
    }

    public void onEvent(ProductsResponse productsResponse) {
        if (productsResponse != null) {
            List<ProductModel> responseProducts = productsResponse.getProducts();

            /*if (productAdapter != null) {
                productAdapter.removeProgress();
            }*/

            if (responseProducts != null && responseProducts.size() > 0) {
                if (productAdapter == null ) {
                    calculateAutomaticGridColumn();
                    /*GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
                    productListRecyclerView.setLayoutManager(gridLayoutManager);*/

                    productAdapter = new SearchAdapter(getContext(), responseProducts, productListRecyclerView, ViewType.GRID);
                    productListRecyclerView.setAdapter(productAdapter);

                    productAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            if (view instanceof CheckBox) {

                            } else {
                                ProductDetailFragment.productModel = productAdapter.getItem(position);
                                getFragmentManager().beginTransaction().replace(R.id.container, new ProductDetailFragment()).addToBackStack(null).commit();

                            }
                        }
                    });

                    /*productAdapter.setOnLoadMoreListener(new SearchAdapter.OnLoadMoreListener() {
                        @Override
                        public void onLoadMore() {
                            productListRecyclerView.post(new Runnable() {
                                @Override
                                public void run() {
                                    productAdapter.addItem(null);
                                    productAdapter.notifyItemInserted(productAdapter.getItemCount() - 1);
                                    callWebServiceMoreLoad();
                                }
                            });
                        }
                    });*/

                } else {
                    int start = productAdapter.getItemCount() -1;
                    int size = responseProducts.size();
                    productAdapter.addListOfItem(responseProducts);
                    productAdapter.notifyItemRangeChanged(start, size);
                }
            } else {
                Toast.makeText(getContext(), getString(R.string.no_product_found), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
