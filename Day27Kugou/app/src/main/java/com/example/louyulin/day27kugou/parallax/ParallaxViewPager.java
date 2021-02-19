package com.example.louyulin.day27kugou.parallax;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.louyulin.day27kugou.R;

import java.util.ArrayList;
import java.util.List;

//视察动画viewpager
public class ParallaxViewPager extends ViewPager {
    private List<ParallaxFragment> mFragments;
    public ParallaxViewPager(@NonNull Context context) {
        super(context);
    }

    public ParallaxViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mFragments = new ArrayList<>();
    }

    //设置布局数组
    public void setLayout(FragmentManager fragmentManager , int[] layout) {
        mFragments.clear();
        //实例化Fragment
        for (int layoutid : layout) {
            ParallaxFragment fragment = new ParallaxFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ParallaxFragment.LAYOUT_ID_KEY , layoutid);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }

        //设置我们适配器
        setAdapter(new ParallaxPageAdapter(fragmentManager));

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 滚动  position 当前位置    positionOffset 0-1     positionOffsetPixels 0-屏幕的宽度px

                // 获取左out 右 in
                ParallaxFragment outFragment = mFragments.get(position);
                List<View> parallaxViews = outFragment.getParallaxViews();
                for (View parallaxView : parallaxViews) {
                    ParallaxTag tag = (ParallaxTag) parallaxView.getTag(R.id.parallax_tag);
                    // 为什么这样写 ？
                    parallaxView.setTranslationX((-positionOffsetPixels)*tag.translationXOut);
                    parallaxView.setTranslationY((-positionOffsetPixels)*tag.translationYOut);
                }

                try {
                    ParallaxFragment inFragment = mFragments.get(position+1);
                    parallaxViews = inFragment.getParallaxViews();
                    for (View parallaxView : parallaxViews) {
                        ParallaxTag tag = (ParallaxTag) parallaxView.getTag(R.id.parallax_tag);
                        parallaxView.setTranslationX((getMeasuredWidth()-positionOffsetPixels)*tag.translationXIn);
                        parallaxView.setTranslationY((getMeasuredWidth()-positionOffsetPixels)*tag.translationYIn);
                    }
                }catch (Exception e){}
            }

            @Override
            public void onPageSelected(int i) {
                //选择切换完毕
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                //
            }
        });
    }

    private class ParallaxPageAdapter extends FragmentPagerAdapter{

        public ParallaxPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
