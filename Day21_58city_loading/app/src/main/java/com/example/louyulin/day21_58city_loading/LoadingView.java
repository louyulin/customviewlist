package com.example.louyulin.day21_58city_loading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoadingView extends LinearLayout {

    private ShapeView mShapeView; //形狀
    private View mShadowView;    //中间的阴影
    // 是否停止动画
    private boolean mIsStopAnimator = false;

    public LoadingView(Context context) {
        this(context,null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //加载写好的布局
        initLayout(context);

    }


    private void initLayout(Context context) {
        View view = inflate(getContext(),R.layout.ui_loading_layout, this);
        mShapeView = view.findViewById(R.id.shape_view);
        mShadowView = view.findViewById(R.id.shadowView);

        //一进来先开始下落
        startFallAnimator();

    }

    private void startFallAnimator() {
        if(mIsStopAnimator){
            return;
        }
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mShapeView,"translationY" , 0 , dip2px(78));
        translationAnimator.setDuration(500);
        //下落速度越来越快 设置一个差值器
        translationAnimator.setInterpolator(new AccelerateInterpolator());

        //中间阴影缩小配合下落
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(mShadowView,"scaleX",1f,0.3f);
        scaleAnimator.setDuration(500);

        //动画集合 一起执行
        AnimatorSet set = new AnimatorSet();
        set.playTogether(translationAnimator,scaleAnimator);

        //下落之后上抛,监听下落动画执行完毕
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //更换形状
                mShapeView.exchange();
                //开始上抛
                startUpAnimator();

            }

        });

        set.start();

    }

    private void startUpAnimator() {

        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mShapeView,"translationY" , dip2px(78) ,0 );

        //中间阴影放大配合上抛
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(mShadowView,"scaleX",0.3f,1f);

        //动画集合 一起执行
        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.setInterpolator(new DecelerateInterpolator());
        set.playTogether(translationAnimator,scaleAnimator);

        //上抛之后开始下落,监听上抛动画执行完毕
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //上抛之后开始下落0
                startFallAnimator();
            }



            @Override
            public void onAnimationStart(Animator animation) {
                //开始旋转
                startRotationAnimator();
            }
        });

        set.start();
    }

    //上抛的时候需要旋转
    private void startRotationAnimator() {
        ObjectAnimator rotationAnimator = null;
        switch (mShapeView.getCurrentShape()){
            case Circle:
            case Square:
                //180
                rotationAnimator = ObjectAnimator.ofFloat(mShapeView,"rotation",0,180);
                break;
            case Triangle:
                //120
                rotationAnimator = ObjectAnimator.ofFloat(mShapeView,"rotation",0,120);
                break;
        }
        rotationAnimator.setDuration(500);
        rotationAnimator.setInterpolator(new DecelerateInterpolator());
        rotationAnimator.start();

    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,getResources().getDisplayMetrics());
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(View.INVISIBLE);// 不要再去排放和计算，少走一些系统的源码（View的绘制流程）
        // 清理动画
        mShapeView.clearAnimation();
        mShadowView.clearAnimation();
        // 把LoadingView从父布局移除
        ViewGroup parent = (ViewGroup) getParent();
        if(parent != null){
            parent.removeView(this);// 从父布局移除
            removeAllViews();// 移除自己所有的View
        }
        mIsStopAnimator = true;
    }


}
