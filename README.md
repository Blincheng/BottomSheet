[![](https://jitpack.io/v/Blincheng/BottomSheet.svg)](https://jitpack.io/#Blincheng/BottomSheet)

一个自下而上弹出的Dialog,并且可以从右往左push页面，
自带导航栏和弹出时缩小外层布局的效果（类似iOS京东的商详选择规格的动画）
集成：
Step 1. Add the JitPack repository to your build file


	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
Step 2. Add the dependency


  	dependencies {
		compile 'com.github.Blincheng:BottomSheet:v0.1'
	}
  
  最简单使用：（可以看到先push一个页面，然后再show()，其实不管怎么做，都是在展示FragmentDialog的时候才会去push，因为内部处理了）
  
  
  		BottomSheetDialogInterface builder = new BottomSheetSettingsBuilder(MainActivity.this).build();
                builder.push(new FirstFragment(),new BottomSheetTitleSetting().setTitle("第一次使用"));
                builder.show();
		
		
  你可以设置最外层动画是否打开：
  
                builder.setOpenWindowShrinkAnim(true)//设置最外层动画是否打开
		
  你可以设置打开Dialog后的回调：
  
                builder.addOnShowListeners(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog) {
                                //当打开Dialog后回调
                            }
                        })
		
  你可以设置Dialog的弹出高度：
  
                builder.setContainerHeight(0.5f)//设置Dialog的弹出高度
		
  你可以设设置关闭Dialog后的回调：
  
                builder.setOnDismissListener(new BottomSheetDismissInterface() {
                            @Override
                            public void dismiss(DialogInterface dialog) {
                                //当关闭Dialog后回调
                            }
                        })
		
  你可以设置标题栏等基本的事件的回调：
  
                builder.setBottomSheetEventCallBack(new BottomSheetEventCallBack() {
                            @Override
                            public void onLeftClicked(BottomSheetDialogInterface dialogInterface, int pageIndex) {
                                //标题栏左边按钮点击回调
                            }

                            @Override
                            public void onRightClicked(BottomSheetDialogInterface dialogInterface, int pageIndex) {
                                //标题栏右边按钮点击回调
                            }
                            @Override
                            public void onSupernatantClick(BottomSheetDialogInterface dialogInterface, int pageIndex) {
                                //点击空白部分回调
                            }
                        })
		
  当你要push一个页面时你可以这样做：
  
                builder.push(new FirstFragment(),new BottomSheetTitleSetting().setTitle("第一个标题"));
		
  我要push一个2行标题的页面，可以这样做（其实主要是BottomSheetTitleSetting功劳）：
 
                builder.push(new SecondFragment(),new BottomSheetTitleSetting()
                                        .setTitleButtonVisible(false,true)
                                        .setTitle(BottomSheetTitleSetting.getSpannableString("这是第二个标题",
                                                "可以用SpannableString来助攻副标题哦","#000000","#808080",46,40))
                                        .setLeftTextVisible(true));
