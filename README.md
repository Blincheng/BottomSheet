[![](https://jitpack.io/v/Blincheng/BottomSheet.svg)](https://jitpack.io/#Blincheng/BottomSheet)

#前言
好久不见，今天来分享一个可能大家会用到的工具，然后打算持续集成开发优化，为大家做一个有人管理的第三方库，做到好用实用等等等等~哈哈哈，先看看效果图~
![效果图](http://img.blog.csdn.net/20170505102047348?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMjU4NjcxNDE=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

#简介
嗯~没错的，就是这个从底部弹出的DialogFragment，然后我叫它是BottomSheet，当然通过效果图也看出来了，不仅仅是能从底部向上弹出，还能将Activity整体缩放，实现一个凹下去的效果，当然动画效果后期可以修改优化，到时候也可以大家自己去实现。

**BottomSheet 其本质是DialogFragment，实现了从底部弹出的效果，并且自带将Activity缩放的一个动画效果（后期会更加丰富），可以实现在BottomSheet 中push（Fragment）的形式从右向做添加新的Fragment**

#主要功能
1.builder模式的超简单使用方式
2.自带标题，所有事件可控，所有标题内容可定制
3.通过push的方式添加Fragment，并且自带从右到左的push效果和从左到右的popUp效果。
4.BottomSheet 弹出时，自带外层Activity动画，可以定制。
5.目前更多功能还在测试开发阶段

项目地址：[https://github.com/Blincheng/BottomSheet](https://github.com/Blincheng/BottomSheet)

#集成导入（gradle）
1.Add the JitPack repository to your build file .Add it in your root build.gradle at the end of repositories:

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

2.Add the dependency

```
dependencies {
	        compile 'com.github.Blincheng:BottomSheet:v0.3'
	}
```
其余集成方式请看：[https://jitpack.io/#Blincheng/BottomSheet/v0.3](https://jitpack.io/#Blincheng/BottomSheet/v0.3)
#使用
##基本使用

```
BottomSheetDialogInterface builder = new BottomSheetSettingsBuilder(MainActivity.this).build();
        builder.show();
```
注意：基本原则就是设置好所有的参数后build，最后再show();
##push一个页面

```
builder.push(new FirstFragment(),new BottomSheetTitleSetting().setTitle("第一个标题"));
```
注意：push（***）原则上应该在show之后调用，当然先push第一个页面，再show（）在使用上也没什么问题。建议在show（）之后push。我们来看看参数，void push(Fragment fragment, BottomSheetTitleSetting setting);第一个是页面的Fragment没疑问，然后第二个是一个BottomSheetTitleSetting，也就是说每个Fragment对应都有一个BottomSheetTitleSetting 来设置标题的内容，具体看下面的标题内容设置

##返回一个页面

```
builder.popUp();
```
说明：注意当一个页面都没push过的话调用是无效的，如果只有一个页面，直接调用会关闭BottomSheet，如果有2个以上，则会正常从左向右popUp页面。

##设置外层Activity动画的开关

```
builder.setOpenWindowShrinkAnim(true)//设置最外层动画是否打开，默认开
```
##设置BottomSheet的高度（参数意义是占屏幕总高度的百分比，默认0.5）

```
builder.setContainerHeight(0.5f)
```
##设置BottomSheet显示后的回调

```
builder.addOnShowListeners(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog) {
                                //当打开Dialog后回调
                            }
                        })
```
注意：建议push第一个页面的push在这个
##设置BottomSheet关闭后的回调

```
builder.setOnDismissListener(new BottomSheetDismissInterface() {
                        @Override
                        public void dismiss(DialogInterface dialog) {
                            //当关闭Dialog后回调
                        }
                    })
```

##设置标题栏等事件的基本回调

```
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
```
##标题内容设置
首先要做的就是拿到这个`BottomSheetTitleSetting setting = new BottomSheetTitleSetting();`
###设置标题内容

```
setting.setTitle("标题内容");
```
注意是参数类型是CharSequence，也就是说TextView支持的，这边都支持。比如可以这样

```
setting.setTitle(BottomSheetTitleSetting.getSpannableString("这是一个两行的标题",
                                                "可以用SpannableString来助攻副标题哦","#000000","#808080",46,40))
```
效果就会变成这样
![这里写图片描述](http://img.blog.csdn.net/20170505111613296?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMjU4NjcxNDE=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
###设置是否隐藏标题

```
setting.setTitleVisible(true);
```
###设置标题的左右按钮的显示和隐藏

```
setting.setTitleButtonVisible(true,true);
```
###设置标题左右文本按钮的显示和隐藏

```
setting.setLeftTextVisible(false);
setting.setRightTextVisible(false);
```
说明：默认文本按钮都是隐藏的
###其余的接口要的也有加了 ，如果真有其他特殊需求建议clone，然后我也会慢慢完善的，有任何问题欢迎大家及时提出来，现在正在测试阶段，希望大家一起来把这个事情做好。