package com.example.louyulin.viewday12_qq50;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import androidx.core.view.ViewCompat;

import static com.example.louyulin.viewday12_qq50.ScreenUtils.getScreenWidth;

public class SlidingMenu extends HorizontalScrollView {
    int mMenuWidth;
    private View mMenuView;
    private View mContentView;

    public void setAlphaListener(AlphaListener alphaListener) {
        mAlphaListener = alphaListener;
    }

    public SlidingMenu(Context context) {
        this(context , null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);

        float rightMargin =
                array.getDimension(R.styleable.SlidingMenu_menuRightMargin, ScreenUtils.dip2px(context, 50));
        // 菜单页的宽度是 = 屏幕的宽度 - 右边的一小部分距离（自定义属性）
        mMenuWidth = (int) (getScreenWidth(context) - rightMargin);
        array.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // 2. 处理事件拦截 + ViewGroup 事件分发的源码实践
        //    当菜单打开的时候，手指触摸右边内容部分需要关闭菜单，还需要拦截事件（打开情况下点击内容页不会响应点击事件）


        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            if (!isOpen){
                if (ev.getRawX() > 150){
                    return false;
                }
            }else {
                if (isIntercept){
                    isIntercept = false;
                    return false;
                }
            }
        }

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            // 只需要管手指抬起 ，根据我们当前滚动的距离来判断
            int currentScrollX = getScrollX();

            if (currentScrollX > mMenuWidth / 2) {
                // 关闭
                closeMenu();
            } else {
                // 打开
                openMenu();
            }
            // 确保 super.onTouchEvent() 不会执行
            return true;
        }
        return super.onTouchEvent(ev);
    }

    boolean isIntercept;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isOpen){
            float rawX = ev.getRawX();
            if (rawX > mMenuWidth){
                closeMenu();
                isIntercept = true;
                return  true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //默认让菜单关闭 滚动过去
        scrollTo(mMenuWidth,0);
    }

    /**
     * 布局解析完毕之后调用的方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //获取linearlayout  得到放置的两个布局(只能两个布局)
        ViewGroup contianer = (ViewGroup) getChildAt(0);
        //再得到菜单
        mMenuView = contianer.getChildAt(0);
        ViewGroup.LayoutParams menuParams = mMenuView.getLayoutParams();
        menuParams.width = mMenuWidth;
        mMenuView.setLayoutParams(menuParams);

        mContentView = contianer.getChildAt(1);
        ViewGroup.LayoutParams contentParams = mContentView.getLayoutParams();
        contentParams.width = getScreenWidth(getContext());
        mContentView.setLayoutParams(contentParams);
    }


    // 4. 处理右边的缩放，左边的缩放和透明度，需要不断的获取当前滚动的位置
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        ViewCompat.setTranslationX(mMenuView, 0.6f*l);

        float alphtScale = 1 - (1f * l / mMenuWidth);
        Log.d("SlidingMenu", "l:" + l);
        mAlphaListener.alpha(alphtScale);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Pixels converted into a dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    boolean isOpen = false;

    private void openMenu() {
        // smoothScrollTo 有动画
        smoothScrollTo(0, 0);
        isOpen = true;
    }

    /**
     * 关闭菜单 滚动到 mMenuWidth 的位置
     */
    private void closeMenu() {
        smoothScrollTo(mMenuWidth, 0);
        isOpen = false;
    }
    AlphaListener mAlphaListener;
    public interface AlphaListener{
        public void alpha(float alpha);
    }


}
