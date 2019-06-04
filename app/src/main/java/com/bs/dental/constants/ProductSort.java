package com.bs.dental.constants;

import com.bs.dental.model.AvailableSortOption;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ashraful on 2/8/2016.
 */
public class ProductSort {

    public static Map<String, Integer> optionKeyValue;
    public static int Position = 0;

    public static int NameAsc = 5;

    public static int NameDesc = 6;

    public static int PriceAsc = 10;

    public static int PriceDesc = 11;

    public static int CreatedOn = 15;

    public static Map<String, Integer> getSortOptions() {
        optionKeyValue = new LinkedHashMap<>();

        optionKeyValue.put("Position",Position);
        optionKeyValue.put("Name Up",NameAsc);
        optionKeyValue.put( "Name Down",NameDesc);
        optionKeyValue.put( "Price Up",PriceAsc);
        optionKeyValue.put("Price Down",PriceDesc);
        optionKeyValue.put("Created On",CreatedOn);


        return optionKeyValue;
    }

    public static  List<String> getSortOptionTextList()
    {
        getSortOptions();

        List<String>sort=new ArrayList<>();
        for (String key:optionKeyValue.keySet())
        {
            sort.add(key);
        }
        return sort;
    }

    public static  List<String> getSortOptionTextList(List<AvailableSortOption>availableSortOptionList)
    {
        getSortOptions();

        List<String>sort=new ArrayList<>();
        for (AvailableSortOption sortOption:availableSortOptionList)
        {
            sort.add(sortOption.getText());
        }
        return sort;
    }
}
