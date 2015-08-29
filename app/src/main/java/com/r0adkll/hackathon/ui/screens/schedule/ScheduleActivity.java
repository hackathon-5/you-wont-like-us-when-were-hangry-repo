package com.r0adkll.hackathon.ui.screens.schedule;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.r0adkll.hackathon.AppComponent;
import com.r0adkll.hackathon.R;
import com.r0adkll.hackathon.ui.model.BaseActivity;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.ui.screens.schedule
 * Created by drew.heavner on 8/29/15.
 */
public class ScheduleActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getAppBar().setNavigationOnClickListener(this);

        Drawable close = getDrawable(R.drawable.ic_clear_24dp);
        close.setTint(Color.WHITE);
        getAppBar().setNavigationIcon(close);
    }

    @Override
    protected void setupComponent(AppComponent appGraph) {
        appGraph.inject(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
