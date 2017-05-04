package com.yiguo.bottomsheetlib.interfaces;

/**
 * Author: Blincheng.
 * Date: 2017/5/3.
 * Description:
 */

public interface BottomSheetEventCallBack {
    /**
     * 左边按钮点击事件
     * */
    void onLeftClicked(BottomSheetDialogInterface dialogInterface, int pageIndex);
    /**
     * 右边按钮点击事件
     * */
    void onRightClicked(BottomSheetDialogInterface dialogInterface, int pageIndex);
    /**
     * 空白部分点击事件
     * */
    void onSupernatantClick(BottomSheetDialogInterface dialogInterface, int pageIndex);
}
