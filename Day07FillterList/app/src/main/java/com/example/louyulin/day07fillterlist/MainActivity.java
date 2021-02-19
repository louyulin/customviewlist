package com.example.louyulin.day07fillterlist;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView letter_tv;
    private LetterSideBar mLetterSideBar;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        mLetterSideBar.setTouchLetterListener(new LetterSideBar.TouchLetterListener() {
            @Override
            public void touchLetter(String letter) {
                mHandler.removeCallbacksAndMessages(null);
                letter_tv.setVisibility(View.VISIBLE);
                letter_tv.setText(letter);
            }

            @Override
            public void upLetter() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        letter_tv.setVisibility(View.GONE);
                    }
                } , 2000);
            }
        });


    }

    private void initView() {
        letter_tv = (TextView) findViewById(R.id.letter_tv);
        mLetterSideBar = findViewById(R.id.letterSideBar);

    }
}
