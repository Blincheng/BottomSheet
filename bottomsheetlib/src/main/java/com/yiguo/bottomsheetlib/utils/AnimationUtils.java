package com.yiguo.bottomsheetlib.utils;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Author: Blincheng.
 * Date: 2017/5/3.
 * Description:
 */

public class AnimationUtils {
    public static void startZoomAnimation(Context context,boolean isShow){
        if(context instanceof FragmentActivity){
            final View contentView = ((Activity)context).findViewById(android.R.id.content);
            LinearLayout parent = (LinearLayout) ((Activity)context).findViewById(android.R.id.content).getParent();
            parent.setBackgroundColor(context.getResources().getColor(android.R.color.black));
//            if (contentView != null && Build.VERSION.SDK_INT >= 19) {
//                contentView.setFitsSystemWindows(true);
//                contentView.setBackgroundResource(android.R.color.white);
//            }
            if(contentView.getScaleX() < 1f&&!isShow){//变大
                final ValueAnimator anim = ObjectAnimator.ofFloat(0.92F, 1.0F).setDuration(200);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float cVal = (Float) animation.getAnimatedValue();
                        contentView.setScaleX(cVal);
                        contentView.setScaleY((1-(1f-cVal)/1.78f));
                    }
                });
                anim.start();
            }else{//变小
                final ValueAnimator anim = ObjectAnimator.ofFloat(0.92F, 1.0F).setDuration(200);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float cVal = (Float) animation.getAnimatedValue();
                        contentView.setScaleX(1.92f-cVal);
                        contentView.setScaleY((1-(1f-(1.92f-cVal))/1.78f));
                    }
                });
                anim.start();
            }
        }
    }
}
