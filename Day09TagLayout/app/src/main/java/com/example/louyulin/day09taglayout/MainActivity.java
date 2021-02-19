package com.example.louyulin.day09taglayout;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TagLayout tag_layout;
    private ArrayList<String> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tag_layout = (TagLayout) findViewById(R.id.taglayout);
        mItems = new ArrayList<>();
        mItems.add("1111111");
        mItems.add("11");
        mItems.add("1111");
        mItems.add("1111");
        mItems.add("11");
        mItems.add("1111");
        mItems.add("1111111");
        mItems.add("1111111");
        mItems.add("11");
        mItems.add("1111");
        mItems.add("1111");
        mItems.add("11");
        mItems.add("1111");
        mItems.add("1111111");

        tag_layout.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mItems.size();
            }

            @Override
            public View getView(final int position, ViewGroup parent) {
                final TextView view = (TextView) LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.item_tv,parent,false);
                view.setText(mItems.get(position));
                return view;
            }

        });
    }

}
