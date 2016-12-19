package com.vasep.controller;

import java.text.DecimalFormat;

/**
 * Created by thuyetpham94 on 20/12/2016.
 */

public class money {
    public static String customFormat(String pattern, double value ) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        return output;
    }
}
