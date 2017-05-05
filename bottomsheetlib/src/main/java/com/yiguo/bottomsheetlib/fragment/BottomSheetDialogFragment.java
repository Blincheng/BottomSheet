package com.yiguo.bottomsheetlib.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import com.yiguo.bottomsheetlib.R;
import com.yiguo.bottomsheetlib.adapter.BottomViewPagerAdapter;
import com.yiguo.bottomsheetlib.interfaces.BottomSheetDialogInterface;
import com.yiguo.bottomsheetlib.interfaces.BottomSheetDismissInterface;
import com.yiguo.bottomsheetlib.interfaces.BottomSheetEventCallBack;
import com.yiguo.bottomsheetlib.utils.AnimationUtils;
import com.yiguo.bottomsheetlib.utils.BottomSheetTitleSetting;
import com.yiguo.bottomsheetlib.utils.Config;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Author: Blincheng.
 * Date: 2017/5/3.
 * Description:
 */
public class BottomSheetDialogFragment extends DialogFragment implements BottomSheetDialogInterface,View.OnClickListener {
    private boolean isShow = false;
    private static float containerHeight;
    private View contentView;
    private FragmentActivity mActivity;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<BottomSheetTitleSetting> bottomSheetTitleSettings = new ArrayList<>();
    /**
     * 是否打开外层Activity布局缩放动画，默认是开的
     * */
    private boolean isOpenWindowShrinkAnim = true;
    private ArrayList<DialogInterface.OnShowListener> onShowListeners = new ArrayList<>();
    private BottomSheetEventCallBack bottomSheetEventCallBack;
    private BottomSheetDismissInterface onDismissListener;
    /**
     * 指示器
     * */
    private int currentIndex = -1;
    private ViewPager mViewPager;
    private BottomViewPagerAdapter adapter;
    private View title_layout;//标题布局
    private TextView title_tv;//标题文本
    private TextView title_left_tv;//标题左边文字按钮
    private TextView title_right_tv;//标题右边文字按钮
    private ImageView title_left_btn;//标题左边图片按钮
    private ImageView title_right_btn;//标题右边图片按钮
    private View title_line;//标题右边图片按钮

    public void setBottomSheetEventCallBack(BottomSheetEventCallBack bottomSheetEventCallBack) {
        this.bottomSheetEventCallBack = bottomSheetEventCallBack;
    }

    public void setOpenWindowShrinkAnim(boolean openWindowShrinkAnim) {
        isOpenWindowShrinkAnim = openWindowShrinkAnim;
    }

    public static BottomSheetDialogFragment newInstance(FragmentActivity mActivity){
        BottomSheetDialogFragment dialogFragment = new BottomSheetDialogFragment();
        dialogFragment.setActivity(mActivity);
        return dialogFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return contentView;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isShow = true;
        if(onShowListeners!=null&&onShowListeners.size()>0){
            for(DialogInterface.OnShowListener l:onShowListeners){
                if(l!=null)
                    l.onShow(getDialog());
            }
        }
        if(mViewPager != null&&fragments.size()>0){
            mViewPager.setAdapter(adapter = new BottomViewPagerAdapter(getChildFragmentManager(),fragments));
            adapter.notifyDataSetChanged();
            mViewPager.setCurrentItem(fragments.size()-1);
            initTitle(fragments.size()-1);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK){
                    cancel();
                }
                return true;
            }
        });
        return dialog;
    }

    @Override
    public void cancel() {
        if(!isHidden()&&isShow) {
            isShow = false;
            contentView.findViewById(R.id.sheet_background).setAlpha(0.0f);
            contentView.findViewById(R.id.sheet_title).startAnimation(getSlideDownAnimation());
            contentView.findViewById(R.id.sheet_title_line).startAnimation(getSlideDownAnimation());
            TranslateAnimation s = getSlideDownAnimation();
            s.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}
                @Override
                public void onAnimationEnd(Animation animation) {dismiss();}
                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            contentView.findViewById(R.id.sheet_viewpager_container).startAnimation(s);
        }
    }

    @Override
    public void push(Fragment fragment, BottomSheetTitleSetting setting) {
        if(isShow){
            if(fragments.size() == 0){
                mViewPager.setAdapter(adapter = new BottomViewPagerAdapter(getChildFragmentManager(),fragments));
            }
            fragments.add(fragment);
            bottomSheetTitleSettings.add(setting);
            adapter.notifyDataSetChanged();
            mViewPager.setCurrentItem(fragments.size()-1);
            initTitle(fragments.size()-1);
        }else{
            fragments.add(fragment);
            bottomSheetTitleSettings.add(setting);
        }
    }

    @Override
    public void popUp() {
        if(fragments.size() == 0)
            return;
        if(fragments.size() == 1){
            cancel();
            return;
        }
        if(fragments.size() > 1){
            bottomSheetTitleSettings.remove(bottomSheetTitleSettings.size()-1);
            fragments.remove(fragments.size()-1);
        }
        adapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(fragments.size()-1);
    }

    @Override
    public void show() {
        if(mActivity != null&& mActivity instanceof FragmentActivity){
            if(isOpenWindowShrinkAnim){
                AnimationUtils.startZoomAnimation(mActivity,true);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    BottomSheetDialogFragment.super.show(mActivity.getSupportFragmentManager(), Config.BOTTOM_SHEET_DIALOG_FRAGMENT);
                    AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
                    animation.setDuration(500);//设置动画持续时间
                    animation.setInterpolator(new LinearInterpolator());
                    contentView.findViewById(R.id.sheet_background).startAnimation(animation);
                    contentView.findViewById(R.id.sheet_title).startAnimation(getSlideUpAnimation());
                    contentView.findViewById(R.id.sheet_title_line).startAnimation(getSlideUpAnimation());
                    contentView.findViewById(R.id.sheet_viewpager_container).startAnimation(getSlideUpAnimation());
                }
            },200);
        }else{
            throw new RuntimeException("Activity must be instanceof support.v4.app.FragmentActivity");
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(onDismissListener != null)
            onDismissListener.dismiss(dialog);
        if(isOpenWindowShrinkAnim)
            AnimationUtils.startZoomAnimation(mActivity,false);
    }

    public void setActivity(FragmentActivity mActivity) {
        this.mActivity = mActivity;
    }

    private static TranslateAnimation getSlideUpAnimation(){
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.ABSOLUTE,0.0f,Animation.ABSOLUTE,0.0f,
                Animation.ABSOLUTE,containerHeight,Animation.ABSOLUTE,0.0f);
        translateAnimation.setDuration(400);
        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        return translateAnimation;
    }

    private static TranslateAnimation getSlideDownAnimation(){
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.ABSOLUTE,0.0f,Animation.ABSOLUTE,0.0f,
                Animation.ABSOLUTE,0.0f,Animation.ABSOLUTE,containerHeight);
        translateAnimation.setDuration(300);
        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        return translateAnimation;
    }
    public void setContentView(View contentView) {
        this.contentView = contentView;
        containerHeight = contentView.findViewById(R.id.sheet_viewpager_container).getLayoutParams().height;
        contentView.findViewById(R.id.sheet_background).setOnClickListener(this);
        contentView.findViewById(R.id.sheet_left_btn).setOnClickListener(this);
        contentView.findViewById(R.id.sheet_right_btn).setOnClickListener(this);
        contentView.findViewById(R.id.sheet_left_text).setOnClickListener(this);
        contentView.findViewById(R.id.sheet_right_text).setOnClickListener(this);
        mViewPager = (ViewPager) contentView.findViewById(R.id.sheet_viewpager_container);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
                initTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setViewPagerScroller(mViewPager);
        title_layout = contentView.findViewById(R.id.sheet_title);
        title_tv = (TextView) contentView.findViewById(R.id.sheet_title_text);
        title_left_tv = (TextView) contentView.findViewById(R.id.sheet_left_text);
        title_right_tv = (TextView) contentView.findViewById(R.id.sheet_right_text);
        title_left_btn = (ImageView) contentView.findViewById(R.id.sheet_left_btn);
        title_right_btn = (ImageView) contentView.findViewById(R.id.sheet_right_btn);
        title_line = contentView.findViewById(R.id.sheet_title_line);


    }
    private void initTitle(int position){
        //顶部初始化
        if(position>=0) {
            BottomSheetTitleSetting setting = bottomSheetTitleSettings.get(position);
            title_layout.setVisibility(setting.isTitleVisible()?View.VISIBLE:View.GONE);
            title_layout.setBackgroundColor(setting.getTitleBackgroundColor());

            if(setting.getLeftButtonTextSize()!=-1)
                title_left_tv.setTextSize(setting.getLeftButtonTextSize());
            if(setting.getRightButtonTextSize()!=-1)
                title_right_tv.setTextSize(setting.getRightButtonTextSize());
            if(setting.getTitleTextSize()!=-1)
                title_tv.setTextSize(setting.getTitleTextSize());
            if(setting.getSpannableStringBuilder() != null)
                title_tv.setText(setting.getSpannableStringBuilder());
            else
                title_tv.setText(setting.getTitleText());

            title_left_tv.setText(setting.getLeftText());
            title_right_tv.setText(setting.getRightText());

            title_tv.setTextColor(setting.getTitleTextColor());
            title_left_tv.setTextColor(setting.getLeftButtonTextColor());
            title_right_tv.setTextColor(setting.getRightButtonTextColor());

            title_left_btn.setVisibility(setting.isLeftButtonVisible()?View.VISIBLE:View.GONE);
            title_right_btn.setVisibility(setting.isRightButtonVisible()?View.VISIBLE:View.GONE);
            title_left_tv.setVisibility(setting.isLeftTextVisible()?View.VISIBLE:View.GONE);
            title_right_tv.setVisibility(setting.isRightTextVisible()?View.VISIBLE:View.GONE);

            title_line.setBackgroundColor(setting.getLineColor());
        }
    }
    public void setOnShowListeners(ArrayList<DialogInterface.OnShowListener> onShowListeners){
        if(onShowListeners!=null)
            this.onShowListeners.addAll(onShowListeners);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sheet_background) {
            if (bottomSheetEventCallBack != null) {
                bottomSheetEventCallBack.onSupernatantClick(BottomSheetDialogFragment.this, currentIndex);
            } else {
                cancel();
            }

        } else if (i == R.id.sheet_left_btn) {
            if (bottomSheetEventCallBack != null) {
                bottomSheetEventCallBack.onLeftClicked(BottomSheetDialogFragment.this, currentIndex);
            } else {
                cancel();
            }

        } else if (i == R.id.sheet_left_text) {
            if (bottomSheetEventCallBack != null) {
                bottomSheetEventCallBack.onLeftClicked(BottomSheetDialogFragment.this, currentIndex);
            } else {
                cancel();
            }

        } else if (i == R.id.sheet_right_btn) {
            if (bottomSheetEventCallBack != null) {
                bottomSheetEventCallBack.onRightClicked(BottomSheetDialogFragment.this, currentIndex);
            } else {
                cancel();
            }

        }else if (i == R.id.sheet_right_text) {
            if (bottomSheetEventCallBack != null) {
                bottomSheetEventCallBack.onRightClicked(BottomSheetDialogFragment.this, currentIndex);
            } else {
                cancel();
            }

        }
    }
    private void setViewPagerScroller(ViewPager viewPager) {

        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            Scroller scroller = new Scroller(mActivity, (Interpolator) interpolator.get(null)) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    // 这里是关键，将duration变长或变短
                    super.startScroll(startX, startY, dx, dy, duration * 5);
                }
            };
            scrollerField.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
            // Do nothing.
        } catch (IllegalAccessException e) {
            // Do nothing.
        }
    }

    public void setOnDismissListener(BottomSheetDismissInterface onDismissListener) {
        this.onDismissListener = onDismissListener;
    }
}
