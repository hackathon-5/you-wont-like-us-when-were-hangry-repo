package com.r0adkll.hackathon.ui.screens.detail;

import com.r0adkll.hackathon.AppComponent;
import com.r0adkll.hackathon.ui.model.BaseActivity;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.ui.screens.detail
 * Created by drew.heavner on 8/29/15.
 */
public class DetailActivity extends BaseActivity {
    @Override
    protected void setupComponent(AppComponent appGraph) {
        appGraph.inject(this);
    }
}
