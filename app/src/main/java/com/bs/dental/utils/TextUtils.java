package com.bs.dental.utils;

/**
 * Created by bs-110 on 12/17/2015.
 */
public class TextUtils {
    public static String getNullSafeString(String text){
        if(text == null || text.length() == 0)
            return "";
        return text;
    }

    public static String removeBlankLines(String text){
        return text.replaceAll("(?m)^[ \t]*\r?\n", "");
    }

    /*public static String getFormattedDate(String rawDateStr){
        String dtStart = rawDateStr;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = format.parse(dtStart);
            System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/
}
