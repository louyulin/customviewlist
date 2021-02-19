package com.example.louyulin.day22_screenmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListDataScreenView listDataScreenView = findViewById(R.id.list_data_screen_view);

        listDataScreenView.setAdapter(new ListScreenMenuAdapter());
    }
}
