package com.example.louyulin.day27kugou;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.louyulin.day27kugou.parallax.ParallaxViewPager;

public class MainActivity extends AppCompatActivity {

    private ParallaxViewPager parallax_vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        parallax_vp = (ParallaxViewPager) findViewById(R.id.parallax_vp);
        parallax_vp.setLayout(getSupportFragmentManager(),new int[]{R.layout.fragment_page_first ,
        R.layout.fragment_page_second , R.layout.fragment_page_third});
    }
}
