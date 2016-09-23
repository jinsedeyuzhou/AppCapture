package com.android.capture;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        TabLayout.Tab one = mTabLayout.newTab();
        TabLayout.Tab two = mTabLayout.newTab();
        mTabLayout.addTab(one.setText("Tab1"));
        mTabLayout.addTab(two.setText("Tab2"));
        one.setIcon(R.mipmap.ic_launcher);
        two.setIcon(R.mipmap.ic_launcher);
    }
}
