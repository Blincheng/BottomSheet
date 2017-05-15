package com.yiguo.bottomsheetlib.anima;

import android.app.Activity;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Author: Blincheng.
 * Date: 2017/5/15.
 * Description:
 */

public class FullScreen3DAnimation extends Animation {
    private String TAG_FOR_PARENT = "tag_for_parent";
    private String TAG_FOR_CHILD = "tag_for_child";
    private Activity activity;
    private boolean isOpen = false;
    //缩放系数
    private int SCALE_CHANGE = 9;
    private float width;
    private float height;
    private Camera mCamera;
    private ViewGroup contentView;
    private ImageView childView;
    /**
     * 动画的父布局
     * */
    private FrameLayout parentView;

    public FullScreen3DAnimation(Activity activity, boolean isOpen){
        super();
        this.activity = activity;
        this.height = activity.getResources().getDisplayMetrics().heightPixels;
        this.width = activity.getResources().getDisplayMetrics().widthPixels;
        this.isOpen = isOpen;
        initLayout();
    }

    private void initLayout(){
        setInterpolator(new AccelerateInterpolator());
        setFillAfter(true);
        setDuration(400);
        contentView = (ViewGroup)this.activity.findViewById(android.R.id.content);
        contentView.setDrawingCacheEnabled(true);
        if(contentView.findViewWithTag(TAG_FOR_PARENT) == null){
            parentView = new FrameLayout(this.activity);
            parentView.setTag(TAG_FOR_PARENT);
            parentView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT));
            parentView.setBackgroundColor(Color.parseColor("#000000"));
            childView = new ImageView(activity);
            childView.setTag(TAG_FOR_CHILD);
            childView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            parentView.addView(childView);
            contentView.addView(parentView);
        }else{
            parentView = (FrameLayout) contentView.findViewWithTag(TAG_FOR_PARENT);
            childView = (ImageView) parentView.findViewWithTag(TAG_FOR_CHILD);
        }
        parentView.setVisibility(View.GONE);
        contentView.destroyDrawingCache();//释放缓存资源
        contentView.buildDrawingCache();
        childView.setImageBitmap(contentView.getDrawingCache());
    }

    public void startAnimation() {
        parentView.setVisibility(View.VISIBLE);
        childView.startAnimation(this);
        if(isOpen){
            setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    parentView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        mCamera.save();
        Matrix matrix = t.getMatrix();
        if(interpolatedTime < 0.5f){
            if(isOpen)//如果是打开状态，则当前时段一直保持缩小
                mCamera.translate(0,0,10*SCALE_CHANGE*(1-0.5f));
            mCamera.rotateX(SCALE_CHANGE*interpolatedTime);
        }else{
            if(isOpen)//如果是打开状态，从小变大
                mCamera.translate(0,0,10*SCALE_CHANGE*(1f-interpolatedTime));
            else//是关闭状态，从大变小
                mCamera.translate(0,0,10*SCALE_CHANGE*(interpolatedTime-0.5f));
            mCamera.rotateX((1-interpolatedTime)*SCALE_CHANGE);
        }
        mCamera.getMatrix(matrix);
        mCamera.restore();
        matrix.preTranslate(-width/2, -height/2);
        matrix.postTranslate(width/2, height/2);
    }
}
