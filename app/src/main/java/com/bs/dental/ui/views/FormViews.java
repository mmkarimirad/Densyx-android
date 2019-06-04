package com.bs.dental.ui.views;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bs.dental.model.KeyValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashraful on 12/8/2015.
 */
public class FormViews {

    public static View view;
    public static boolean isFormValid=true;


     public static List<KeyValuePair> getForMFieldValue(ViewGroup viewGroup, String KeyPrefixTag)
     {
         List<KeyValuePair> keyValuePairs =new ArrayList<>();
         for (int i = 0, count = viewGroup.getChildCount(); i < count; ++i) {
             View view = viewGroup.getChildAt(i);
             if (view instanceof EditText) {
                EditText editText=((EditText) view);

                 KeyValuePair keyValuePair =new KeyValuePair();
                 keyValuePair.setKey(KeyPrefixTag+editText.getTag());
                 keyValuePair.setValue(getTexBoxFieldValue(editText));
                 keyValuePairs.add(keyValuePair);
                 System.out.println(keyValuePair.getKey()+","+ keyValuePair.getValue());

             }
         }
         return keyValuePairs;
     }

    public static boolean isEmpty(TextView etText) {

        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }
    public static boolean isEmpty(int resourceId) {
        TextView textView=(TextView)view.findViewById(resourceId);
        if (textView.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public static String getTexBoxFieldValue(TextView etText) {
        return  etText.getText().toString().trim();
    }

    public static String getTexBoxFieldValue(int resourceId) {
        TextView textView=(TextView)view.findViewById(resourceId);
        return  textView.getText().toString().trim();
    }
    public static boolean isEqual(TextView first, TextView second  ) {
        if (first.getText().toString().trim().equals(second.getText().toString().trim())) {
            return true;
        } else {
            return false;
        }
    }



    public static boolean isValidWithMark(EditText etText,String displayText) {
        if (isEmpty(etText)) {
            etText.setError(displayText+" is Required");
            isFormValid=false;

            //etText.setError(displayText+" is Empty");
            return false;
        } else {

            return true;
        }
    }

    public static TextView getTextView(int  resourceId)
    {
       return  (TextView)view.findViewById(resourceId);
    }
    public static TextView getTextView(int  resourceId,View view)
    {
        return  (TextView)view.findViewById(resourceId);
    }

    public static void setText(int resourceId,String value,View parentView)
    {

        if(value==null)
            getTextView(resourceId,parentView).setText("");
     else
        getTextView(resourceId,parentView).setText(value);
    }

    public static void setTextOrHideIfEmpty(int resourceId,String value,View parentView)
    {
        if(value==null || value.length() == 0)
            getTextView(resourceId,parentView).setVisibility(View.GONE);
        else
            getTextView(resourceId,parentView).setText(value);
    }

    public static boolean isValidWithMark(int  resourceId,String displayText) {
        if (isEmpty(resourceId)) {
            getTextView(resourceId).setError(displayText + " is Required");
            isFormValid=false;
            return false;
        } else {

            return true;
        }
    }

    public static boolean isValidWithMark(int  resourceId) {
        TextView textView=getTextView(resourceId);
        if (isEmpty(textView)) {
            textView.setError(getCamelValue(textView.getTag().toString()) + " is Required");
            isFormValid=false;
            return false;
        } else {

            return true;
        }
    }

    public static String getCamelValue(String str)
    {
        String text="";
        for (String w : str.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
            text=text+w+" ";
        }
        return text;
    }

}
