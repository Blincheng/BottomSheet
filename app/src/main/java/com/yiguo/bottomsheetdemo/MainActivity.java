package com.yiguo.bottomsheetdemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.yiguo.bottomsheetlib.BottomSheetSettingsBuilder;
import com.yiguo.bottomsheetlib.interfaces.BottomSheetDialogInterface;
import com.yiguo.bottomsheetlib.interfaces.BottomSheetDismissInterface;
import com.yiguo.bottomsheetlib.interfaces.BottomSheetEventCallBack;
import com.yiguo.bottomsheetlib.utils.BottomSheetTitleSetting;

public class MainActivity extends FragmentActivity {
    private BottomSheetDialogInterface builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new BottomSheetSettingsBuilder(MainActivity.this)
                        .setOpenWindowShrinkAnim(true)//设置最外层动画是否打开
                        .addOnShowListeners(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog) {
                                //当打开Dialog后回调
                            }
                        })
                        .setBottomSheetEventCallBack(new BottomSheetEventCallBack() {
                            @Override
                            public void onLeftClicked(BottomSheetDialogInterface dialogInterface, int pageIndex) {
                                //标题栏左边按钮点击回调
                                builder.popUp();
                            }

                            @Override
                            public void onRightClicked(BottomSheetDialogInterface dialogInterface, int pageIndex) {
                                //标题栏右边按钮点击回调
                                builder.push(new SecondFragment(),new BottomSheetTitleSetting()
                                        .setTitleButtonVisible(false,true)
                                        .setTitle(BottomSheetTitleSetting.getSpannableString("这是第二个标题",
                                                "可以用SpannableString来助攻副标题哦","#000000","#808080",46,40))
                                        .setLeftTextVisible(true));
                            }
                            @Override
                            public void onSupernatantClick(BottomSheetDialogInterface dialogInterface, int pageIndex) {
                                //点击空白部分回调
                                dialogInterface.cancel();
                            }
                        })
                        .setContainerHeight(0.5f)//设置Dialog的弹出高度
                        .setOnDismissListener(new BottomSheetDismissInterface() {
                            @Override
                            public void dismiss(DialogInterface dialog) {
                                //当关闭Dialog后回调
                            }
                        })
                        .build();//最后才调用build
                builder.push(new FirstFragment(),new BottomSheetTitleSetting().setTitle("第一个标题"));
                builder.show();
            }
        });
    }
}
