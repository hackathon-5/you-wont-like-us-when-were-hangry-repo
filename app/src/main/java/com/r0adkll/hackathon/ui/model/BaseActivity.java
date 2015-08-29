package com.r0adkll.hackathon.ui.model;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ftinc.kit.util.BuildUtils;
import com.r0adkll.hackathon.App;
import com.r0adkll.hackathon.AppComponent;
import com.r0adkll.hackathon.R;

import butterknife.ButterKnife;
import pocketknife.PocketKnife;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.ui
 * Created by drew.heavner on 8/28/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    /***********************************************************************************************
     *
     * Variables
     *
     */

    private Toolbar mAppBar;

    /***********************************************************************************************
     *
     * Lifecycle Methods
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupComponent(App.get(this).component());
        super.onCreate(savedInstanceState);
        PocketKnife.bindExtras(this);
        PocketKnife.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        getAppBar();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PocketKnife.saveInstanceState(this, outState);
    }

    /***********************************************************************************************
     *
     * Helper Methods
     *
     */

    @ColorInt
    protected int color(@ColorRes int resId){
        return getResources().getColor(resId);
    }

    @SuppressLint("NewApi")
    protected Drawable drawable(@DrawableRes int resId){
        if(BuildUtils.isLollipop()) {
            return getDrawable(resId);
        }else{
            return getResources().getDrawable(resId);
        }
    }

    /***********************************************************************************************
     *
     * Base Methods
     *
     */

    /**
     * Get the {@link Toolbar} from the layout automatically if it exists
     *
     * @return      the toolbar in the layout
     */
    protected Toolbar getAppBar() {
        if(this.mAppBar == null) {
            this.mAppBar = ButterKnife.findById(this, R.id.appbar);
            if(this.mAppBar != null) {
                this.setSupportActionBar(this.mAppBar);
            }
        }

        return this.mAppBar;
    }

    /**
     * Override this method to setup the component/subcomponent
     * for the subclassed activity
     *
     * @param appGraph      the application graph to inject with
     */
    protected abstract void setupComponent(AppComponent appGraph);

}
