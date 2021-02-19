package com.example.louyulin.day05paint;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Progress progress_bar;
    private ShapeView shapeView;
    private HorizontalProgressBarWithNumber progress_horizontal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        shapeView = (ShapeView) findViewById(R.id.shapeView);
        progress_bar = (Progress) findViewById(R.id.progress_bar);
        progress_bar.setMax(100);
        progress_horizontal = findViewById(R.id.progress_horizontal);
        progress_horizontal.setMax(100);
//        progress_bar.setProgress(75);

        ValueAnimator animator = ObjectAnimator.ofFloat(0, 100);
        animator.setDuration(4000);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                progress_bar.setProgress((int) progress);
                progress_horizontal.setProgress((int) progress);
            }
        });

        exchange(shapeView);
    }

    volatile boolean  isTrue = true;

    public void exchange(View view){

        new Thread(){
            @Override
            public void run() {
                super.run();
                while (isTrue){
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                shapeView.exchanged();
                            }
                        });
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isTrue = false;
    }
}
