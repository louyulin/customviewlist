package com.example.louyulin.viewday14viewdraghelper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.core.widget.ListViewCompat;
import androidx.customview.widget.ViewDragHelper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class VerticalDragListView extends FrameLayout {

    private ViewDragHelper mViewDragHelper;
    private View mDragListView;
    private int mMenuHeight;
    private boolean mMenuIsOpen;

    public VerticalDragListView(Context context) {
        this(context , null);
    }

    public VerticalDragListView(Context context, AttributeSet attrs) {
        this(context, null , 0);
    }

    public VerticalDragListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mViewDragHelper = ViewDragHelper.create(this,mCallback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childeCount = getChildCount();

        if (childeCount != 2){
            throw new RuntimeException("ViewDrag only two views");
        }

        mDragListView = getChildAt(1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            View menuView = getChildAt(0);
            mMenuHeight = menuView.getMeasuredHeight();
        }
    }

    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            //指定该子view是否可以拖动 就是child
            return child == mDragListView;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            if (top <= 0){
                top = 0;
            }

            if (top > mMenuHeight){
                top = mMenuHeight;
            }
            return top;
        }

        //松开
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            if (releasedChild == mDragListView){
                if (mDragListView.getTop() > mMenuHeight / 2){
                    //滚动到菜单的高度
                    mViewDragHelper.settleCapturedViewAt(0 , mMenuHeight);
                    mMenuIsOpen = true;
                }else {
                    //滚动到0的位置
                    mViewDragHelper.settleCapturedViewAt(0 , 0);
                    mMenuIsOpen = false;
                }
                invalidate();
            }
        }
    };

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    private float mDownY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mMenuIsOpen){
            return true;
        }

        //向下滑动拦截 不要给ListView做处理
        //谁拦截谁  父view 拦截子view  但是子view可以调 requestDisallowIntercept 请求不要拦截
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                //让DrageHelper 拿到完整的时间
                mViewDragHelper.processTouchEvent(ev);
                break;

            case MotionEvent.ACTION_MOVE:
               float moveY = ev.getY();
               if ((moveY - mDownY) > 0 &&  !canChildScrollUp()){
                   //向下滑动 不让listView 做处理  并且滚动到顶部
                   return true;
               }
                break;

        }
        return super.onInterceptTouchEvent(ev);
    }

    //还能不能向上滚动 取自于 swiperefreshlayout
    public boolean canChildScrollUp() {
        if (mDragListView instanceof ListView) {
            return ListViewCompat.canScrollList((ListView) mDragListView, -1);
        }
        return mDragListView.canScrollVertically(-1);
    }
}



