package com.example.louyulin.day23_circleloading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

public class LoadingView extends RelativeLayout {
    private CircleView left , middle , right;
    private int mTranslationDistance = 25;
    public LoadingView(Context context) {
        this(context , null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTranslationDistance = dip2px(mTranslationDistance);
        setBackgroundColor(Color.WHITE);
        //添加三个view
        left = getCircleView(context);
        left.exchangedColor(Color.BLUE);
        middle = getCircleView(context);
        middle.exchangedColor(Color.RED);
        right = getCircleView(context);
        right.exchangedColor(Color.GREEN);
        addView(left);
        addView(middle);
        addView(right);

        post(new Runnable() {
            @Override
            public void run() {
            //保证布局实例化结束再开启动画
                expendAnim();
            }
        });
    }

    private void expendAnim() {
        //开启动画
        ObjectAnimator leftTranslationAnimator = ObjectAnimator.ofFloat(left , "translationX" ,
                0 ,-mTranslationDistance);
        ObjectAnimator rightTranslationAnimator = ObjectAnimator.ofFloat(right , "translationX" ,
                0 ,mTranslationDistance);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(350);
        set.playTogether(leftTranslationAnimator,rightTranslationAnimator);
        set.setInterpolator(new DecelerateInterpolator(2f));
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //往里面跑
                innerAnim();
            }
        });
        set.start();
    }

    private void innerAnim() {
        //开启动画
        ObjectAnimator leftTranslationAnimator = ObjectAnimator.ofFloat(left , "translationX" ,
                -mTranslationDistance ,0);
        ObjectAnimator rightTranslationAnimator = ObjectAnimator.ofFloat(right , "translationX" ,
                mTranslationDistance ,0);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(350);
        set.setInterpolator(new AccelerateInterpolator(2f));
        set.playTogether(leftTranslationAnimator,rightTranslationAnimator);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //切换颜色 左边给中间 中间给右边 右边给左边
                int leftColor = left.getColor();
                int rightColor = right.getColor();
                int middleColor = middle.getColor();

                middle.exchangedColor(leftColor);
                right.exchangedColor(middleColor);
                left.exchangedColor(rightColor);

                //往里面跑
                expendAnim();
            }
        });
        set.start();
    }

    private CircleView getCircleView(Context context) {
        CircleView circleView = new CircleView(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dip2px(10),dip2px(10));
        params.addRule(CENTER_IN_PARENT);
        circleView.setLayoutParams(params);
        return circleView;
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,getResources().getDisplayMetrics());
    }


}
