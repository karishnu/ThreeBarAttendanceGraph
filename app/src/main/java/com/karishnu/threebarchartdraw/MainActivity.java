package com.karishnu.threebarchartdraw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BarView barView = (BarView) findViewById(R.id.barView);
        barView.setPercentage(50,75,80);
    }
}
