package com.mic.bottomsheetlib.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Author: Blincheng.
 * Date: 2017/5/3.
 * Description:
 */

public class Config {
    public final static String BOTTOM_SHEET_DIALOG_FRAGMENT = "BOTTOM_SHEET_DIALOG_FRAGMENT";
    public final static String BOTTOM_SHEET_HEIGHT = "BOTTOM_SHEET_HEIGHT";
    public final static String BOTTOM_SHEET_WIDTH = "BOTTOM_SHEET_WIDTH";
    public final static String BOTTOM_SHEET_CONTEXT = "BOTTOM_SHEET_CONTEXT";
    public static int getScreenHeight(Activity activity){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
    public static int getScreenWidth(Activity activity){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
}
