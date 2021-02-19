package com.example.louyulin.viewday16statusbar;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

public class BottomBarBehavior extends CoordinatorLayout.Behavior<View> {
    private boolean isAnimate;//动画是否在进行

    public BottomBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 有嵌套滑动到来了，问下该Behavior是否接受嵌套滑动
     *
     * @param coordinatorLayout 当前的CoordinatorLayout
     * @param child             该Behavior对应的View
     * @param directTargetChild 我的理解是在CoordinateLayout下作为父View,而该View的子类是Tager的那个View,也就是Target的父View),因为我测试用ViewPager包裹了RecycleView后该参数返回Viewpager,如果没有包裹参数返回的是RecycleView
     * @param target            具体嵌套滑动的那个子类
     * @param nestedScrollAxes  支持嵌套滚动轴。水平方向，垂直方向，或者不指定
     * @param type              导致此滚动事件的输入类型
     * @return 是否接受该嵌套滑动
     */

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int nestedScrollAxes, @ViewCompat.NestedScrollType int type) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    private boolean isOut = false;

    /**
     * 在滚动子级view使用事件后调用，并使用消耗的量
     *
     * @param coordinatorLayout 此行为与关联的视图的父级CoordinatorLayout
     */
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        //dy大于0是向上滚动 小于0是向下滚动，判断的时候尽量不要判断是否大于等于或者小于等于0，否则可能会影响点击事件
        //System.out.println(dy);
        //向上滚动的时候是出来
        // 而且向上的时候是出来，向下是隐藏
        if (dyConsumed > 0) {
            if (!isOut) {
                // 往上滑动，是隐藏 , 加一个标志位 已经往下走了
                int translationY = ((CoordinatorLayout.LayoutParams) child.getLayoutParams()).bottomMargin + child.getMeasuredHeight();
                child.animate().translationY(translationY).setDuration(500).start();
                isOut = true;
            }
        } else {
            if (isOut) {
                // 往下滑动
                child.animate().translationY(0).setDuration(500).start();
                isOut = false;
            }

        }


    }



}
