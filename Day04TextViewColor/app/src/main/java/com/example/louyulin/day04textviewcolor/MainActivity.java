package com.example.louyulin.day04textviewcolor;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;
    private Button btn2;
    private ColorTrackTextView colortv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        colortv = (ColorTrackTextView) findViewById(R.id.colortv);
    }

    @Override
    public void onClick(View v) {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        switch (v.getId()) {
            case R.id.btn1:
                valueAnimator.setDuration(2000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentProgress = (float) animation.getAnimatedValue();
                        colortv.setDirection(ColorTrackTextView.Direction.LEFT_TORIGHT);
                        colortv.setmCurrentProgress(currentProgress);
                    }
                });
                valueAnimator.start();
                break;
            case R.id.btn2:
                valueAnimator.setDuration(2000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentProgress = (float) animation.getAnimatedValue();
                        colortv.setDirection(ColorTrackTextView.Direction.RIGHT_TOLEFT);
                        colortv.setmCurrentProgress(currentProgress);
                    }
                });
                valueAnimator.start();
                break;
        }
    }
}
