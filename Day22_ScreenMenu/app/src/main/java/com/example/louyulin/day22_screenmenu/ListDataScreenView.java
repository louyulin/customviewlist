package com.example.louyulin.day22_screenmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class ListDataScreenView extends LinearLayout {
    //存放头部tab
    private LinearLayout mMenuTabView;
    private Context mContext;
    //1.2创建framelayout 用来存放阴影+内容布局
    private FrameLayout mMenuMiddleView;
    //阴影
    private View mShadowView;
    //阴影颜色
    private int mShadowColor = 0x88888888;
    private FrameLayout mMenuContentView;
    //筛选菜单的adapter
    private BaseMenuAdapter mAdapter;
    private int mMenuContainerHeight;
    //当前打开的位置
    private int mCurrentPosition = -1;
    //动画是否在执行
    boolean mAnimatorExecute;

    public ListDataScreenView(Context context) {
        this(context , null);
    }

    public ListDataScreenView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public ListDataScreenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initLayout();
        setOrientation(VERTICAL);
    }

    private void initLayout() {
        //简单的效果用代码去创建布局
        //1.1 创建头部存放tab
        mMenuTabView = new LinearLayout(mContext);
        mMenuTabView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mMenuTabView);
        //1.2创建framelayout 用来存放阴影+内容布局
        mMenuMiddleView = new FrameLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0);
        params.weight = 1;
        mMenuMiddleView.setLayoutParams(params);
        addView(mMenuMiddleView);
        mShadowView = new View(mContext);
        mShadowView.setBackgroundColor(mShadowColor);
        mShadowView.setAlpha(0f);
        mShadowView.setVisibility(GONE);
        mMenuMiddleView.addView(mShadowView);
       //创建菜单存放菜单的内容
        mMenuContentView = new FrameLayout(mContext);
        mMenuContentView.setBackgroundColor(Color.WHITE);
        mMenuMiddleView.addView(mMenuContentView);
    }

    /**
     * 具体的观察者
     */
    private class AdapterDataSetObserver extends MenuObserver{

        @Override
        public void closeMenu() {
            //如果有注册就会收到通知
            ListDataScreenView.this.closeMenu();
        }
    }

    private AdapterDataSetObserver mAdapterDataSetObserver;

    public void setAdapter(BaseMenuAdapter menuAdapter){
        //观察者
        if (mAdapter != null && mAdapterDataSetObserver != null){
            menuAdapter.unregisterDataSetObserver(mAdapterDataSetObserver);
        }

        this.mAdapter = menuAdapter;
        //注册一个观察者
        mAdapterDataSetObserver = new AdapterDataSetObserver();
        mAdapter.registerDataSetObserver(mAdapterDataSetObserver);

        //获取多少条目
        int count = mAdapter.getCount();
        for (int i = 0; i <count ; i++) {
            //获取菜单tab
            View tabView = mAdapter.getTabView(i,mMenuTabView);
            LinearLayout.LayoutParams params = (LayoutParams) tabView.getLayoutParams();
            params.weight = 1;
            tabView.setLayoutParams(params);
            mMenuTabView.addView(tabView);
            //设置点击事件
            setTabClick(tabView , i);

            //获取菜单的内容
            View menuView = mAdapter.getMenuView(i, mMenuContentView);
            menuView.setVisibility(GONE);
            mMenuContentView.addView(menuView);
        }
    }

    //设置tab的点击
    private void setTabClick(final View tabView, final int position) {
     tabView.setOnClickListener(new OnClickListener() {
         @Override
         public void onClick(View v) {
            if (mCurrentPosition == -1) {
                //没打开去打开
                openMenu(position,tabView);
            }else {
                if (mCurrentPosition == position){
                    //打开了去关闭
                    closeMenu();
                }else {
                    View currentView = mMenuContentView.getChildAt(mCurrentPosition);
                    currentView.setVisibility(GONE);
                    mAdapter.menuClose(mMenuTabView.getChildAt(mCurrentPosition));
                    mCurrentPosition = position;
                    currentView = mMenuContentView.getChildAt(mCurrentPosition);
                    currentView.setVisibility(VISIBLE);
                    mAdapter.menuOpen(mMenuTabView.getChildAt(mCurrentPosition));
                }

            }
         }
     });
    }

    private void closeMenu() {
        //关闭菜单
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mMenuContentView , "translationY"
                , 0 , -mMenuContainerHeight );
        translationAnimator.setDuration(300);
        translationAnimator.start();

        mShadowView.setVisibility(VISIBLE);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mShadowView , "alpha" ,1f
                , 0f);
        alphaAnimator.setDuration(300);
        alphaAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                View childAt = mMenuContentView.getChildAt(mCurrentPosition);
                childAt.setVisibility(GONE);
                mShadowView.setVisibility(GONE);
                mCurrentPosition = -1;
                mAnimatorExecute = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mAnimatorExecute = true;
                mAdapter.menuClose(mMenuTabView.getChildAt(mCurrentPosition));
            }
        });
        alphaAnimator.start();
    }

    private void openMenu(final int position, final View tabView) {
        if (mAnimatorExecute){
            return;
        }
        //获取当前位置显示当前菜单
        View childAt = mMenuContentView.getChildAt(position);
        childAt.setVisibility(VISIBLE);
        //打开菜单
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mMenuContentView , "translationY"
                , -mMenuContainerHeight , 0);
        translationAnimator.setDuration(300);
        translationAnimator.start();

        mShadowView.setVisibility(VISIBLE);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mShadowView , "alpha" ,0f
                , 1f);
        alphaAnimator.setDuration(300);
        alphaAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentPosition = position;
                mAnimatorExecute = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mAnimatorExecute = true;
                //把当前的tabview传到外面
                mAdapter.menuOpen(tabView);
            }
        });



        mShadowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
        alphaAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (mMenuContainerHeight == 0 && height > 0) {
            mMenuContainerHeight = (int) (height * 75f / 100);
            ViewGroup.LayoutParams layoutParams = mMenuContentView.getLayoutParams();
            layoutParams.height = mMenuContainerHeight;
            mMenuContentView.setLayoutParams(layoutParams);
            mMenuContentView.setTranslationY(-mMenuContainerHeight);
        }
    }
}
