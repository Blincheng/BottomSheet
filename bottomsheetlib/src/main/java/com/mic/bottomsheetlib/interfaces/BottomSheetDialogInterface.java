package com.mic.bottomsheetlib.interfaces;

import android.support.v4.app.Fragment;

import com.mic.bottomsheetlib.utils.BottomSheetTitleSetting;

/**
 * Author: Blincheng.
 * Date: 2017/5/3.
 * Description:
 */

public interface BottomSheetDialogInterface {
    public void cancel();
    public void push(Fragment fragment, BottomSheetTitleSetting setting);
    public void popUp();
    public void show();
}
