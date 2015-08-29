package com.r0adkll.hackathon.ui.screens.home;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.r0adkll.hackathon.AppComponent;
import com.r0adkll.hackathon.R;
import com.r0adkll.hackathon.ui.model.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.ui.screens.home
 * Created by drew.heavner on 8/29/15.
 */
public class TabbedHomeActivity extends BaseActivity {

    @Bind(R.id.tabs)
    TabLayout mTabs;
    @Bind(R.id.application_bar)
    AppBarLayout mApplicationBar;
    @Bind(R.id.pager)
    ViewPager mPager;
    @Bind(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    private PetPagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_home);
        init();
    }

    private void init(){

        mPagerAdapter = new PetPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        // OPTIMIZE: this value based on device capabilities for memory optimizations
        mPager.setOffscreenPageLimit(2);

        // Initialize the Tab Layout
        mTabs.setupWithViewPager(mPager);
    }



    /**
     * The category pager adapter
     */
    private static class PetPagerAdapter extends FragmentStatePagerAdapter {

        public PetPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return FindPetFragment.createInstance();
            }else{
                return MyScheduleFragment.createInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? "Find pets" : "Your Schedule";
        }

    }

    @Override
    protected void setupComponent(AppComponent appGraph) {

    }
}
