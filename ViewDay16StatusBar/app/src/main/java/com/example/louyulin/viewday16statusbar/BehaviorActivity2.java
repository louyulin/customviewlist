package com.example.louyulin.viewday16statusbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.louyulin.viewday16statusbar.rvutil.DefaultRefreshCreator;
import com.example.louyulin.viewday16statusbar.rvutil.RefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BehaviorActivity2 extends AppCompatActivity {
    int page = 0;
    private RVAdapter mRecyclerViewAdapter;
    private TextView mFooterTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior2);

        final List<String> listStr = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            listStr.add("item:" + i);
        }


        Toolbar toolbar = findViewById(R.id.tool_bar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BehaviorActivity2.this,BehaviorActivity.class));
            }
        });
        setSupportActionBar(toolbar);

        final RefreshRecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewAdapter = new RVAdapter();
        mRecyclerViewAdapter.setListStr(listStr);
        recyclerView.setAdapter(mRecyclerViewAdapter);
        View mFooterView = LayoutInflater.from(this).inflate(R.layout.itemfooter,null,false);
        mFooterTv = mFooterView.findViewById(R.id.tv);
        recyclerView.addFooterView(mFooterView);
        recyclerView.addRefreshViewCreator(new DefaultRefreshCreator());
        recyclerView.setOnRefreshListener(new RefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.onStopRefresh();
                        if (mRecyclerViewAdapter != null) {
                            page = 0;
                            listStr.clear();
                            for (int i = 0; i < 20; i++) {
                                listStr.add("item:" + i);
                            }
                            mFooterTv.setText("正在加载更多");
                        }
                    }
                }, 1500);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount() - 1);
                int lastChildBottom = lastChildView.getBottom();
                int recyclerBottom = recyclerView.getBottom() - recyclerView.getPaddingBottom();
                int lastPosition = recyclerView.getLayoutManager().getPosition(lastChildView);
                if (lastChildBottom == recyclerBottom && lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                    //滑动到底部
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            page = page + 1;
                            if (page < 5) {
                                for (int i = 0; i < 20; i++) {
                                    listStr.add("item:" + i);
                                }
                                Log.d("BehaviorActivity", "---");
                                mRecyclerViewAdapter.setListStr(listStr);
                            } else {
                                Log.d("BehaviorActivity", "???");
                                mFooterTv.setText("没有更多了");
                            }
                        }
                    }, 1500);
                }

            }
        });
    }

}
