package com.r0adkll.hackathon.ui.model;

import android.os.Bundle;

import pocketknife.PocketKnife;

/**
 *
 */
public abstract class BaseFragment extends com.ftinc.kit.mvp.BaseFragment {


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PocketKnife.bindArguments(this);
        PocketKnife.restoreInstanceState(this, savedInstanceState);
        setupComponent();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PocketKnife.saveInstanceState(this, outState);
    }

    protected void setupComponent(){}

}
