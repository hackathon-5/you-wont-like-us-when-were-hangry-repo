package com.r0adkll.hackathon.ui.screens.setup;

import android.os.Bundle;

import com.r0adkll.hackathon.AppComponent;
import com.r0adkll.hackathon.R;
import com.r0adkll.hackathon.ui.model.BaseActivity;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.ui.screens.setup
 * Created by drew.heavner on 8/28/15.
 */
public class SignupActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }



    @Override
    protected void setupComponent(AppComponent appGraph) {
        appGraph.inject(this);
    }
}
