package com.example.louyulin.viewday16statusbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class BehaviorActivity extends AppCompatActivity {
    int page = 0;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);

        final List<String> listStr = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            listStr.add("item:" + i);
        }

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        if (mRecyclerViewAdapter != null) {
                            page = 0;
                            listStr.clear();
                            for (int i = 0; i < 20; i++) {
                                listStr.add("item:" + i);
                            }
                            mRecyclerViewAdapter.setIsNoMore(false);
                        }
                    }
                }, 1500);
            }
        });
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewAdapter = new RecyclerViewAdapter();
        mRecyclerViewAdapter.setListStr(listStr);
        recyclerView.setAdapter(mRecyclerViewAdapter);

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
                                mRecyclerViewAdapter.setIsNoMore(true);
                            }
                        }
                    }, 1500);
                }

            }
        });
    }


}
