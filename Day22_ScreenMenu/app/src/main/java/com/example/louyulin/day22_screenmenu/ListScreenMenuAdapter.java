package com.example.louyulin.day22_screenmenu;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ListScreenMenuAdapter extends BaseMenuAdapter {
    private String[] mItems = {"类型" , "品牌" , "价格" , "更多"};
    @Override
    public int getCount() {
        return mItems.length;
    }

    @Override
    public View getTabView(int position, ViewGroup parent) {
        TextView tabView = (TextView)LayoutInflater.from(parent.getContext()).
                inflate(R.layout.ui_list_data_screen_tab, parent,false);
        tabView.setText(mItems[position]);
        return tabView;
    }

    @Override
    public View getMenuView(int position, ViewGroup parent) {
        TextView menuView = (TextView)LayoutInflater.from(parent.getContext()).
                inflate(R.layout.ui_list_data_screen_menu, parent,false);
        menuView.setText(mItems[position]);
        menuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
        return menuView;
    }

    @Override
    public void menuOpen(View tabView) {
        super.menuOpen(tabView);
        TextView tabTv = (TextView) tabView;
        tabTv.setTextColor(Color.RED);
    }

    @Override
    public void menuClose(View tabView) {
        super.menuClose(tabView);
        TextView tabTv = (TextView) tabView;
        tabTv.setTextColor(Color.BLACK);
    }


}
