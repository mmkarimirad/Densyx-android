package com.bs.dental;

import android.widget.RelativeLayout;

import com.bs.dental.model.Category;
import com.bs.dental.networking.CustomCB;
import com.bs.dental.networking.RetroClient;
import com.bs.dental.networking.response.CategoryResponse;
import com.bs.dental.ui.fragment.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashraful on 3/31/2015.
 */
public class CategoryEvent {
    public static List<Category>rootCategories;
    public static List<Category>subCategories;
    public static List<Category>allCategories;


    public static void initialize()
    {
      rootCategories=new ArrayList<>();
       subCategories=new ArrayList<>();
        allCategories=new ArrayList<>();
    }
public static  List<Category> rootCategories(RelativeLayout layout)
    {

        if(rootCategories==null || rootCategories.size()==0) {
            RetroClient.getApi().getCategory().enqueue(new CustomCB<CategoryResponse>(layout));
        }

        return rootCategories;
    }
 //   @Override
    public void saveData(List<Category> list) {
        rootCategories=new ArrayList<>();
         subCategories=new ArrayList<>();

        for (Category category : list)
            if (category.getParentCategoryId() == 0)
                rootCategories.add(category);
            else
                subCategories.add(category);
        allCategories = list;


    }

   public static ArrayList<Category>categoryList(int parentId)
    {
        ArrayList<Category>data=new ArrayList<>();
        for(Category category:subCategories)
           if(category.getParentCategoryId()==parentId)
               data.add(category);
        return  data;
    }
    public static ArrayList<Category>categoryList()
    {
        int parentId=0;
        ArrayList<Category>data=new ArrayList<>();
        for(Category category:allCategories)
        {
            if(category.getId()==Utility.categoryId)
            {
                parentId=category.getParentCategoryId();
                break;
            }
        }
        if(parentId!=0)
        for(Category category:allCategories)
            if(category.getParentCategoryId()== parentId)
                data.add(category);
        Utility.categoryId=parentId;
        return  data;
    }

   /* public static ArrayList<String> categoryList(List<Category>categories)
    {
        ArrayList<String>data=new ArrayList<>();
        for(Category category:categories)
        data.add(category.getName());
        return  data;
    }
    public static ArrayList<String>categoryList(int parentId)
    {
        ArrayList<Category>data=new ArrayList<>();
        for(Category category:subCategories)
            data.add(category.getName());
        return  data;
    }*/
}
