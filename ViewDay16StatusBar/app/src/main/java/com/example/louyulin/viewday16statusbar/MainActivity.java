package com.example.louyulin.viewday16statusbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

public class MainActivity extends AppCompatActivity {

    private NestedScrollView scorllview;
    private LinearLayout titleBar;
    private ImageView iv;
    private int mMeasuredHeight;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        StatusBarUtil.setActivityTranslucent(this);


    }

    private void initView() {
        scorllview = (NestedScrollView) findViewById(R.id.scorllview);
        titleBar = (LinearLayout) findViewById(R.id.titleBar);
        titleBar.getBackground().setAlpha(0);
        iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BehaviorActivity2.class));
            }
        });
        iv.post(new Runnable() {
            @Override
            public void run() {
                mMeasuredHeight = iv.getMeasuredHeight() - titleBar.getMeasuredHeight();
            }
        });
        scorllview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                float alpha = (float) scrollY / mMeasuredHeight;

                if (alpha < 0){
                    alpha = 0;
                }

                if (alpha > 1){
                    alpha = 1;
                }

                titleBar.getBackground().setAlpha((int) (alpha*255));
            }
        });

    }



}
