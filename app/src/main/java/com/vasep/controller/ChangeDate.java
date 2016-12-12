package com.vasep.controller;

/**
 * Created by thuyetpham94 on 09/12/2016.
 */
public class ChangeDate {
    public static String convertDate(String date){
        String new_date = null;
        new_date = date.substring(8,10)+"/"+date.substring(5,7)+"/"+date.substring(0,4);
        return new_date;
    }
}
