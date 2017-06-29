package com.mic.bottomsheetlib.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

/**
 * Author: Blincheng.
 * Date: 2017/5/3.
 * Description:
 */

public class BottomSheetTitleSetting {
    private boolean isTitleVisible = true;
    private int titleTextColor = Color.parseColor("#000000");
    private int titleBackgroundColor = Color.parseColor("#ffffff");
    private int titleTextSize = -1;
    private int leftButtonTextColor = Color.parseColor("#808080");
    private int rightButtonTextColor = Color.parseColor("#808080");
    private int leftButtonTextSize = -1;
    private int rightButtonTextSize = -1;
    private int lineColor = Color.parseColor("#DDDDDD");
    private CharSequence titleText = "BottomSheetTitle";
    private CharSequence leftText = "返回";
    private CharSequence rightText = "关闭";
    private boolean isLeftButtonVisible = true;
    private boolean isRightButtonVisible = true;
    private boolean isLeftTextVisible = false;
    private boolean isRightTextVisible = false;
    private SpannableStringBuilder spannableStringBuilder;

    /**
     * 设置是否显示左右按钮，默认都是显示的
     * @param isShowRight
     * @param isShowLeft
     */
    public BottomSheetTitleSetting setTitleButtonVisible(boolean isShowLeft, boolean isShowRight){
        isLeftButtonVisible = isShowLeft;
        isRightButtonVisible = isShowRight;
        return this;
    }
    /**
     * 设置标题字体大小，如果不需要设置，保留-1就可以了
     * @param titleTextSize
     * @param leftTextSize
     * @param rightTextSize
     */
    public BottomSheetTitleSetting setTextSize(int titleTextSize, int leftTextSize, int rightTextSize){
        this.titleTextSize = titleTextSize;
        leftButtonTextSize = leftTextSize;
        rightButtonTextSize = rightTextSize;
        return this;
    }
    /**
     * 设置标题栏各字体颜色,不想更换保留-1就可以了
     * @param titleTextColor 标题颜色
     * @param leftTextColor 左按钮颜色
     * @param rightTextColor 右按钮颜色
     * @return BottomSheetTitleSetting
     */
    public BottomSheetTitleSetting setTextColor(int titleTextColor, int leftTextColor, int rightTextColor){
        if(titleTextColor!=-1)
            this.titleTextColor = titleTextColor;
        if(leftTextColor!=-1)
            this.leftButtonTextColor = leftTextColor;
        if(rightTextColor!=-1)
            this.rightButtonTextColor = rightTextColor;
        return this;
    }
    /**
     * 设置线的颜色,不设置则保留-1就可以了
     * @param color
     */
    public BottomSheetTitleSetting setLineColor(int color){
        if(color==-1)
            return this;
        lineColor = color;
        return this;
    }
    /**
     * 设置标题背景颜色
     * @param backgroundColor
     * @return BottomSheetTitleSetting
     */
    public BottomSheetTitleSetting setBackgoundColor(int backgroundColor){
        this.titleBackgroundColor = backgroundColor;
        return this;
    }
    /**
     * 设置导航是否可见
     * @param isTitleVisible
     * @return BottomSheetTitleSetting
     */
    public BottomSheetTitleSetting setTitleVisible(boolean isTitleVisible){
        this.isTitleVisible = isTitleVisible;
        return this;
    }
    /**
     * 设置标题
     * @param title
     * @return
     */
    public BottomSheetTitleSetting setTitle(CharSequence title){
        this.titleText = title;
        return this;
    }
    /**
     * 设置左文字
     * @param leftText
     * @return
     */
    public BottomSheetTitleSetting setLeftText(CharSequence leftText){
        this.leftText = leftText;
        return this;
    }

    /**
     * 设置右文字
     * @param rightText
     * @return
     */
    public BottomSheetTitleSetting setRightText(CharSequence rightText){
        this.rightText = rightText;
        return this;
    }

    /**
     * 获取两行的SpannableString
     * */
    public static SpannableStringBuilder getSpannableString(String title,String subTitle
            ,String titleColor,String subTitleColor
            ,int titleSize,int subTitleSize){
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        SpannableString titleString = new SpannableString(title+"\n");
        titleString.setSpan(new ForegroundColorSpan(Color.parseColor(titleColor)),0,title.length(),Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        titleString.setSpan(new AbsoluteSizeSpan(titleSize),0,title.length(),Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableStringBuilder.append(titleString);

        SpannableString subTitleString = new SpannableString(subTitle);
        subTitleString.setSpan(new ForegroundColorSpan(Color.parseColor(subTitleColor)),0,subTitle.length(),Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        subTitleString.setSpan(new AbsoluteSizeSpan(subTitleSize),0,subTitle.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableStringBuilder.append(subTitleString);

        return spannableStringBuilder;
    }

    public boolean isTitleVisible() {
        return isTitleVisible;
    }

    public int getTitleTextColor() {
        return titleTextColor;
    }

    public int getTitleBackgroundColor() {
        return titleBackgroundColor;
    }

    public int getTitleTextSize() {
        return titleTextSize;
    }

    public int getLeftButtonTextColor() {
        return leftButtonTextColor;
    }

    public int getRightButtonTextColor() {
        return rightButtonTextColor;
    }

    public int getLeftButtonTextSize() {
        return leftButtonTextSize;
    }

    public int getRightButtonTextSize() {
        return rightButtonTextSize;
    }

    public int getLineColor() {
        return lineColor;
    }

    public CharSequence getTitleText() {
        return titleText;
    }

    public CharSequence getLeftText() {
        return leftText;
    }

    public CharSequence getRightText() {
        return rightText;
    }

    public boolean isLeftButtonVisible() {
        return isLeftButtonVisible;
    }

    public boolean isRightButtonVisible() {
        return isRightButtonVisible;
    }

    public SpannableStringBuilder getSpannableStringBuilder() {
        return spannableStringBuilder;
    }

    public boolean isLeftTextVisible() {
        return isLeftTextVisible;
    }

    public BottomSheetTitleSetting setLeftTextVisible(boolean leftTextVisible) {
        isLeftTextVisible = leftTextVisible;
        return this;
    }

    public boolean isRightTextVisible() {
        return isRightTextVisible;
    }

    public BottomSheetTitleSetting setRightTextVisible(boolean rightTextVisible) {
        isRightTextVisible = rightTextVisible;
        return this;
    }
}
