package com.bs.dental.utils;

import android.widget.EditText;

/**
 * Created by bs-110 on 1/8/2016.
 */
public class EditTextUtils {

    public boolean isValidString(EditText et){
        String string = et.getText().toString();
        if(string == null || string.length() <= 0){
            return false;
        }
        return true;
    }

    public String getString(EditText et){
        String string = et.getText().toString();
        if(string == null || string.length() <= 0){
            return "";
        }
        return string;
    }

    public boolean isValidInteger(EditText et){
       if(! isValidString(et)){
           return false;
       } else {
           try {
               Integer.parseInt(getString(et));
               return true;
           } catch (NumberFormatException e) {
               e.printStackTrace();
           }
       }
        return false;
    }

    public int getInteger(EditText et){
        if(isValidInteger(et)){
            return Integer.parseInt(getString(et));
        }
        return 0;
    }
}
