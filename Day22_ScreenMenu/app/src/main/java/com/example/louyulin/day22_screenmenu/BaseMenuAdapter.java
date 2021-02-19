package com.example.louyulin.day22_screenmenu;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMenuAdapter {
    // 获取总共有多少条
    public abstract int getCount();

    // 获取当前的TabView
    public abstract View getTabView(int position, ViewGroup parent);

    // 获取当前的菜单内容
    public abstract View getMenuView(int position, ViewGroup parent);

    //创建一个list 放观察者
    private MenuObserver mMenuObserver;

    /**
     * 菜单打开
     *
     * @param tabView
     */
    public void menuOpen(View tabView) {

    }

    /**
     * 菜单关闭
     *
     * @param tabView
     */
    public void menuClose(View tabView) {

    }

    public void registerDataSetObserver(MenuObserver observer) {
        mMenuObserver = observer;
    }

    public void unregisterDataSetObserver(MenuObserver observer) {
        mMenuObserver = null;

    }

    public void closeMenu() {
        if (mMenuObserver != null){
            mMenuObserver.closeMenu();
        }
    }
}
