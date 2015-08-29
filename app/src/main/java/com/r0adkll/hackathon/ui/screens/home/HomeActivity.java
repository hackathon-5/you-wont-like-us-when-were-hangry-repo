package com.r0adkll.hackathon.ui.screens.home;

import android.os.Bundle;

import com.r0adkll.hackathon.AppComponent;
import com.r0adkll.hackathon.R;
import com.r0adkll.hackathon.ui.model.BaseActivity;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.ui.screens.home
 * Created by drew.heavner on 8/29/15.
 */
public class HomeActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void setupComponent(AppComponent appGraph) {
        appGraph.inject(this);
    }
}
