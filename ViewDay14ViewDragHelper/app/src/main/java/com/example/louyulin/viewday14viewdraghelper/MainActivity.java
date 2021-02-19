package com.example.louyulin.viewday14viewdraghelper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ListView list_view;
    private List<String>  mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        for (int i = 0; i < 200 ; i++) {
            mItems.add("i - >" + i);
        }

        list_view.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mItems.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lv,parent,false);
                TextView itemTv = convertView.findViewById(R.id.item_tv);
                itemTv.setText(mItems.get(position));
                return convertView;
            }
        });
    }

    private void initView() {
        list_view = (ListView) findViewById(R.id.list_view);
    }
}
