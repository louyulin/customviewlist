package com.example.louyulin.day09taglayout;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public abstract class BaseAdapter {
    // 1.有多少个条目
    public abstract int getCount();

    // 2.getView通过position
    public abstract View getView(int position, ViewGroup parent);
}
