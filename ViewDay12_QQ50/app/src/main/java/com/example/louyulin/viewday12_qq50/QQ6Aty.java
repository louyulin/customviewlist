package com.example.louyulin.viewday12_qq50;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class QQ6Aty extends AppCompatActivity {

    private View alphaView;
    private SlidingMenu home_sliding_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq6_aty);
        initView();
    }

    private void initView() {
        alphaView = (View) findViewById(R.id.alphaView);
        alphaView.post(new Runnable() {
            @Override
            public void run() {
                alphaView.setAlpha(0);
            }
        });

        home_sliding_menu = (SlidingMenu) findViewById(R.id.home_sliding_menu);
        home_sliding_menu.setAlphaListener(new SlidingMenu.AlphaListener() {
            @Override
            public void alpha(float alpha) {
                alphaView.setAlpha(alpha);
            }
        });

    }
}
