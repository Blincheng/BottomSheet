package com.mic.bottomsheetlib;

import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mic.bottomsheetlib.fragment.BottomSheetDialogFragment;
import com.mic.bottomsheetlib.interfaces.BottomSheetDialogInterface;
import com.mic.bottomsheetlib.interfaces.BottomSheetDismissInterface;
import com.mic.bottomsheetlib.interfaces.BottomSheetEventCallBack;
import com.mic.bottomsheetlib.utils.Config;

import java.util.ArrayList;

/**
 * Author: Blincheng.
 * Date: 2017/5/3.
 * Description:一个自下而上弹出的Dialog,并且可以从右往左push页面，
 * 自带导航栏和弹出时缩小外层布局的效果（类似iOS京东的商详选择规格的动画）
 */

public class BottomSheetSettingsBuilder {

    private boolean isOpenWindowShrinkAnim = true;
    private FragmentActivity mActivity;
    private BottomSheetEventCallBack callBack;
    private int height;
    private ArrayList<DialogInterface.OnShowListener> onShowListeners = new ArrayList<>();
    private BottomSheetDismissInterface onDismissListener;

    public BottomSheetSettingsBuilder(FragmentActivity mActivity) {
        this.mActivity = mActivity;
    }
    public int getHeight() {
        return height > 0?height:Config.getScreenHeight(mActivity)/2;
    }

    public BottomSheetSettingsBuilder setBottomSheetEventCallBack(BottomSheetEventCallBack callBack) {
        this.callBack = callBack;
        return this;
    }
    public BottomSheetDialogInterface build(){
        return buildInnerFragment();
    }
    private BottomSheetDialogInterface buildInnerFragment(){
        BottomSheetDialogFragment dialogFragment = BottomSheetDialogFragment.newInstance(mActivity);
        RelativeLayout view = (RelativeLayout) LayoutInflater.from(mActivity.getApplicationContext()).inflate(R.layout.dialog_bottom_sheet_context,null,true);
        //设置ViewPager宽高
        ViewGroup.LayoutParams lp = view.findViewById(R.id.sheet_viewpager_container).getLayoutParams();
        lp.height = getHeight();
        lp.width = -1;
        view.findViewById(R.id.sheet_viewpager_container).setLayoutParams(lp);
        dialogFragment.setContentView(view);
        dialogFragment.setOpenWindowShrinkAnim(isOpenWindowShrinkAnim);
        dialogFragment.setOnShowListeners(onShowListeners);
        dialogFragment.setBottomSheetEventCallBack(callBack);
        dialogFragment.setOnDismissListener(onDismissListener);
        return dialogFragment;
    }
    /**
     * 是否打开外层Activity布局缩放动画，默认是开的
     * */
    public BottomSheetSettingsBuilder setOpenWindowShrinkAnim(boolean openWindowShrinkAnim) {
        isOpenWindowShrinkAnim = openWindowShrinkAnim;
        return  this;
    }
    /**
     * @param pct 表示占屏幕整体高度的比例
     * */
    public BottomSheetSettingsBuilder setContainerHeight(float pct) {
        this.height = Config.getScreenHeight(mActivity)*pct> 0?
                (int) (Config.getScreenHeight(mActivity) * pct) :
                Config.getScreenHeight(mActivity)/2;
        return this;
    }

    public BottomSheetSettingsBuilder addOnShowListeners(DialogInterface.OnShowListener onShowListener) {
        onShowListeners.add(onShowListener);
        return this;
    }
    public BottomSheetSettingsBuilder setOnDismissListener(BottomSheetDismissInterface onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }
}
