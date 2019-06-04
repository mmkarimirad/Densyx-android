package com.bs.dental.ui.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bs.dental.R;
import com.bs.dental.model.AttributeControlType;
import com.bs.dental.model.AttributeControlValue;
import com.bs.dental.model.KeyValuePair;
import com.bs.dental.model.ProductAttribute;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.response.PriceResponse;
import com.bs.dental.ui.customview.FlowMaterialRadioGroup;
import com.bs.dental.ui.fragment.ProductDetailFragment;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.RadioButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ashraful on 11/26/2015.
 */
public class ProductAttributeViews extends LinearLayout {
    LinearLayout layout;
    List<ProductAttribute> attributes;
    Context context;
    Map<String, String> valueTextPairMap;
    Map<ProductAttribute, EditText> editTextList;
    final String productAttributePrefix = "product_attribute";
    LinearLayout attributeLayout;

    public ProductAttributeViews(Context context) {
        super(context);
    }

    public ProductAttributeViews(Context context, List<ProductAttribute> attributes, LinearLayout layout) {
        super(context);
        this.attributes = attributes;
        this.layout = layout;
        this.context = context;
        editTextList = new HashMap<>();
        valueTextPairMap = new HashMap<>();
        generateView();
        callPriceWebservice();

    }

    private void removeAllExistingViews() {
        layout.removeAllViews();
    }

    private void generateView() {
        removeAllExistingViews();
        for (ProductAttribute productAttribute : attributes) {
            generateattributeLayout();

            generateViewLabel(productAttribute.getName(), productAttribute);

            if (productAttribute.getAttributeControlType() == AttributeControlType.DropdownList) {
                generateDropdownList(productAttribute);
            } else if (productAttribute.getAttributeControlType() == AttributeControlType.TextBox) {
                generateEdittext(productAttribute);
            } else if (productAttribute.getAttributeControlType() == AttributeControlType.RadioList) {
                generateRadioGroup(productAttribute);

            } else if (productAttribute.getAttributeControlType() == AttributeControlType.Checkboxes) {
                generateCheckBoxContainerGridlayout(productAttribute);
            } else if (productAttribute.getAttributeControlType() == AttributeControlType.ReadonlyCheckboxes) {
                generateDiasbaleCheckbox(productAttribute);
            } else if(productAttribute.getAttributeControlType() == AttributeControlType.ColorSquares) {
                generateColorSqauares(productAttribute);
            }

        }
    }


    private void generateEdittext(ProductAttribute productAttribute) {
        EditText editText = (EditText) getLayoutInflater().inflate(R.layout.textbox, null);
        editText.setHint(productAttribute.getDefaultValue());
        editTextList.put(productAttribute, editText);
        addViewintLayout(editText);
    }

    private void generateRadioGroup(ProductAttribute productAttribute) {
        FlowMaterialRadioGroup radioGridGroup = (FlowMaterialRadioGroup) getLayoutInflater().
                inflate(R.layout.custom_flow_type_radio_group, null);
        addButtoninRadioGroup(productAttribute, radioGridGroup);

        addViewintLayout(radioGridGroup);

           /* GridView  gridView=getGridView();
            GridRadioGroupAdapter adapter=new GridRadioGroupAdapter(context,R.layout.radiobutton,values);
            gridView.setAdapter(adapter);
            addViewintLayout(gridView);*/


    }

    private void generateColorSqauares(final ProductAttribute productAttribute) {
        final LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.linear_layout, null);
        for (final AttributeControlValue value : productAttribute.getValues()) {
            //CheckableImageButton checkableImageButton = generateCheckableImageButton(value);

            LayoutParams imageViewParams = new AppBarLayout.LayoutParams(80, 80);
            imageViewParams.setMargins(0, 0, 10, 0);

            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(imageViewParams);
            imageView.setMaxHeight(20);
            imageView.setMaxWidth(20);

            GradientDrawable  shapeDrawable;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                shapeDrawable = (GradientDrawable) getResources().getDrawable(R.drawable.bg_color_square, context.getTheme());
            } else {
                shapeDrawable = (GradientDrawable) getResources().getDrawable(R.drawable.bg_color_square);
            }

            try {
                shapeDrawable.setColor(Color.parseColor(value.getColorSquaresRgb()));
                shapeDrawable.setStroke(5, Color.TRANSPARENT, 10, 10);
            } catch (Exception ex) {
                // who cares
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                imageView.setBackground(shapeDrawable);
            } else {
                imageView.setBackgroundDrawable(shapeDrawable);
            }

            imageView.setClickable(true);
            imageView.setFocusable(true);
            linearLayout.addView(imageView);

            if (value.isPreSelected()) {
                selectColorSquare(imageView, value);
                updateHashmapWithdefaultValue(productAttribute, value, true);
            }

            Picasso.with(context).load(value.getPictureModel().getImageUrl())
                    .fit().centerInside()
                    .into(imageView);

            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    hasElementInyMapofSingleChoice(productAttribute);
                    updateDataInHashMap(productAttribute, value, true);
                    clearSelection(linearLayout);
                    selectColorSquare(v, value);
                }
            });
        }

        addViewintLayout(linearLayout);
    }

    private void selectColorSquare(View view, AttributeControlValue value) {
        GradientDrawable  shapeDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            shapeDrawable = (GradientDrawable) getResources().getDrawable(R.drawable.bg_color_square, context.getTheme());
        } else {
            shapeDrawable = (GradientDrawable) getResources().getDrawable(R.drawable.bg_color_square);
        }

        try {
            shapeDrawable.setColor(Color.parseColor(value.getColorSquaresRgb()));
            shapeDrawable.setStroke(5, Color.parseColor(value.getColorSquaresRgb()), 10, 10);
        } catch (Exception ex) {
            // who cares
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(shapeDrawable);
        } else {
            view.setBackgroundDrawable(shapeDrawable);
        }
    }

    private void clearSelection(LinearLayout layout) {
        for (int i=0; i< layout.getChildCount(); i++) {
            ImageView imageView = (ImageView) layout.getChildAt(i);

            GradientDrawable shapeDrawable = (GradientDrawable) imageView.getBackground();
            try {
                shapeDrawable.setStroke(5, Color.TRANSPARENT, 10, 10);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                imageView.setBackground(shapeDrawable);
            } else {
                imageView.setBackgroundDrawable(shapeDrawable);
            }
        }
    }

    private void addButtoninRadioGroup(final ProductAttribute productAttribute, FlowMaterialRadioGroup radioGridGroup) {
        for (AttributeControlValue value : productAttribute.getValues()) {
            RadioButton radioButton = generateRadioButton(value);
            if (isPreselected(value)) {
                radioButton.setChecked(true);
                updateHashmapWithdefaultValue(productAttribute, value, true);
            }
            radioGridGroup.addView(radioButton);
        }

        radioGridGroup.setOnCheckedChangeListener(new FlowMaterialRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(FlowMaterialRadioGroup group, int checkedId) {
                AttributeControlValue value = getAttributeControlValue(checkedId, productAttribute.getValues());
                hasElementInyMapofSingleChoice(productAttribute);

                updateDataInHashMap(productAttribute, value, true);
            }
        });

    }


    private GridView getGridView() {
        GridView gridView = (GridView) getLayoutInflater().inflate(R.layout.gridview, null);
        return gridView;
    }

    private void generateCheckBoxContainerGridlayout(ProductAttribute productAttribute) {
        GridLayout gridLayout = generateGridLayout();
        addCheckBoxInGridLayout(productAttribute, gridLayout);
        addViewintLayout(gridLayout);
           /* GridView  gridView=getGridView();
            CheckBoxAttributeGridAdapter adapter=new CheckBoxAttributeGridAdapter(context,R.layout.checkbox,values);
            gridView.setAdapter(adapter);
            addViewintLayout(gridView);*/

    }


    private void generateDiasbaleCheckbox(ProductAttribute values) {
        GridLayout gridLayout = generateGridLayout();
        addCheckBoxInGridLayout(values, gridLayout);
        makeCheckboxReadOnly(gridLayout);
        addViewintLayout(gridLayout);

    }


    private void generateDropdownList(final ProductAttribute productAttribute) {

        if(productAttribute.getValues().size()>0)

        {

            AppCompatSpinner spinner=new AppCompatSpinner(context);
//            Spinner spinner = (Spinner) getLayoutInflater().inflate(R.layout.dropdownlist, layout, false);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (context, R.layout.simple_spinner_item_black_color, getDropDownListData(productAttribute.getValues()));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            int preSelectedPosition = getPreSelectedposition(productAttribute) >= 0 ? getPreSelectedposition(productAttribute) : 0;
            /*if(preSelectedPosition>=0)
                spinner.setSelection(preSelectedPosition);
            else*/
            spinner.setSelection(preSelectedPosition);
            updateHashmapWithdefaultValue(productAttribute, productAttribute.getValues().get(preSelectedPosition), true);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    AttributeControlValue value = productAttribute.getValues().get(position);
                    hasElementInyMapofSingleChoice(productAttribute);
                    updateDataInHashMap(productAttribute, value, true);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
//            spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(Spinner spinner, View view, int position, long l) {
//
//                }
//            });
            spinner.setAdapter(adapter);

            addViewintLayout(spinner);
        }
        else
        {
            String key = getKey(productAttribute);
            valueTextPairMap.put("" + productAttribute.getId(), key);
        }
    }

    private int getPreSelectedposition(ProductAttribute productAttribute) {
        int index = -1, position = -1;
        for (AttributeControlValue value : productAttribute.getValues()) {
            ++index;
            if (isPreselected(value)) {
                position = index;
                break;
            }
        }

        return position;
    }

    private boolean isPreselected(AttributeControlValue value) {
        if (value.isPreSelected())
            return true;
        else
            return false;
    }

    private ArrayList<String> getDropDownListData(List<AttributeControlValue> values) {
        ArrayList<String> dataList = new ArrayList<>();
        for (AttributeControlValue value : values) {
            dataList.add(value.getName() + getAtributeValueText(value));
        }
        return dataList;

    }

    private String getAtributeValueText(AttributeControlValue value) {
        String text;
        if (value.getPriceAdjustment() != null && !value.getPriceAdjustment().isEmpty())
            text = "[" + value.getPriceAdjustment() + "]";
        else
            text = "";
        return text;

    }

    private RadioButton generateRadioButton(AttributeControlValue value) {
        RadioButton radioButton = (RadioButton) getLayoutInflater().inflate(R.layout.radiobutton, null);

        radioButton.setText(value.getName() + getAtributeValueText(value));

        radioButton.setId(value.getId());
        return radioButton;
    }

    /*private CheckableImageButton generateCheckableImageButton(AttributeControlValue value) {
        CheckableImageButton checkableImageButton = getLayoutInflater().inflate(R.layout.checkable_image_button, null);
        return  checkableImageButton;
    }*/

    private CheckBox generateCheckBox(final ProductAttribute productAttribute, final AttributeControlValue value) {
        CheckBox checkBox = (CheckBox) getLayoutInflater().inflate(R.layout.checkbox, null);
        checkBox.setText(value.getName() + getAtributeValueText(value));
        checkBox.setId(value.getId());
        if (isPreselected(value)) {
            checkBox.setChecked(true);
            updateHashmapWithdefaultValue(productAttribute, value, true);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateDataInHashMap(productAttribute, value, isChecked);
            }
        });
        return checkBox;
    }

    private GridLayout generateGridLayout() {
        return (GridLayout) getLayoutInflater().inflate(R.layout.gridlayout, null);

    }


    private void addCheckBoxInGridLayout(ProductAttribute productAttribute, GridLayout gridLayout) {
        for (AttributeControlValue value : productAttribute.getValues()) {
            gridLayout.addView(generateCheckBox(productAttribute, value));
        }
    }

    private void makeCheckboxReadOnly(GridLayout gridLayout) {
        for (int index = 0; index < gridLayout.getChildCount(); index++) {
            CheckBox child = (CheckBox) gridLayout.getChildAt(index);
            child.setChecked(true);
            child.setEnabled(false);

        }
    }

    private void generateViewLabel(String label, ProductAttribute productAttribute) {
        TextView textView = (TextView) getLayoutInflater().inflate(R.layout.text_view, layout, false);
        textView.setText(label);
        if (productAttribute.isRequired())
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_star_formular, 0);
        attributeLayout.addView(textView);
        // addViewintLayout(textView);
    }

    private void generateattributeLayout() {
        attributeLayout = (LinearLayout) getLayoutInflater().
                inflate(R.layout.separate_layout_each_attribute_product_details, layout, false);

    }

    private LayoutInflater getLayoutInflater() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater;
    }

    private void addViewintLayout(View view) {
        attributeLayout.addView(view);
        layout.addView(attributeLayout);
    }


    private void updateDataInHashMap(ProductAttribute productAttribute, AttributeControlValue value, boolean ischecked) {
        updateHashmapWithdefaultValue(productAttribute, value, ischecked);


        callPriceWebservice();
    }

    private void updateHashmapWithdefaultValue(ProductAttribute productAttribute, AttributeControlValue value, boolean ischecked) {
        if (ischecked) {
            String key = getKey(productAttribute);
            valueTextPairMap.put("" + value.getId(), key);
            //    Log.v("MMMMMkey,Value:",key+","+value.getId());


        } else {
            valueTextPairMap.remove("" + value.getId());
        }
        putEdittextValueInMap();
    }

    public void putEdittextValueInMap() {
        for (ProductAttribute productAttribute : editTextList.keySet()) {
            hasElementInyMapofSingleChoice(productAttribute);
            String key = getKey(productAttribute);
            valueTextPairMap.put(FormViews.getTexBoxFieldValue(editTextList.get(productAttribute)), key);
        }

    }

    public String getKey(ProductAttribute productAttribute) {
        String key = String.format("%s_%d_%d_%d", productAttributePrefix, productAttribute.getProductId()
                , productAttribute.getProductAttributeId(), productAttribute.getId());
        return key;
    }


    public void callPriceWebservice() {
        RetroClient.getApi().getUpdatedPrice(ProductDetailFragment.productModel.getId(), getProductAttribute())
                .enqueue(new CustomCB<PriceResponse>(ProductDetailFragment.self.getView()));
    }


    public List<KeyValuePair> getProductAttribute() {
        List<KeyValuePair> keyValuePairs = new ArrayList<>();
        for (String value : valueTextPairMap.keySet()) {
            KeyValuePair keyValuePair = new KeyValuePair();
            keyValuePair.setKey(valueTextPairMap.get(value));
            keyValuePair.setValue(value);
            keyValuePairs.add(keyValuePair);
            Log.v("key,Value:", valueTextPairMap.get(value) + "," + value);
        }
        return keyValuePairs;
    }


    private void removeDataInHashMap(String desiredValue) {
        for (Map.Entry<String, String> entry : valueTextPairMap.entrySet())
            if (entry.getValue().equals(desiredValue)) {
                valueTextPairMap.remove(entry.getKey());
                break;
            }


    }

    private void hasElementInyMapofSingleChoice(ProductAttribute productAttribute) {
        String key = getKey(productAttribute);
        if (valueTextPairMap.containsValue(key))
            removeDataInHashMap(key);
    }

    public AttributeControlValue getAttributeControlValue(int id, List<AttributeControlValue> values) {
        for (AttributeControlValue value : values) {
            if (value.getId() == id)
                return value;
        }
        return null;

    }


}
