package com.r0adkll.hackathon.ui.screens.schedule;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ftinc.kit.util.RxUtils;
import com.ftinc.kit.widget.EmptyView;
import com.r0adkll.hackathon.AppComponent;
import com.r0adkll.hackathon.R;
import com.r0adkll.hackathon.api.ApiService;
import com.r0adkll.hackathon.data.model.Reservation;
import com.r0adkll.hackathon.ui.model.BaseActivity;
import com.r0adkll.hackathon.ui.screens.schedule.adapter.DigestReservation;
import com.r0adkll.hackathon.ui.screens.schedule.adapter.MyScheduleRecyclerAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.app.AppObservable;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.ui.screens.schedule
 * Created by drew.heavner on 8/29/15.
 */
public class MyScheduleActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recycler)
    RecyclerView mRecycler;

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @Bind(R.id.empty_view)
    EmptyView mEmptyView;

    @Inject
    ApiService mApi;

    private MyScheduleRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);
        ButterKnife.bind(this);

        mRefreshLayout.setColorSchemeResources(R.color.primary, R.color.primary_dark, R.color.accent);
        mRefreshLayout.setOnRefreshListener(this);

        mAdapter = new MyScheduleRecyclerAdapter(this);
        mAdapter.setEmptyView(mEmptyView);

        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.addItemDecoration(new StickyRecyclerHeadersDecoration(mAdapter));

        onRefresh();
    }

    private void loadScheduleData(List<Reservation> schedule){
        mAdapter.clear();
//        mAdapter.addAll(DigestReservation.generate(schedule));
        mAdapter.notifyDat
        aSetChanged();
    }

    @Override
    protected void setupComponent(AppComponent appGraph) {
        appGraph.inject(this);
    }

    @Override
    public void onRefresh() {
        if(!mRefreshLayout.isRefreshing()) mRefreshLayout.setRefreshing(true);
        AppObservable.bindActivity(this, mApi.getMySchedule())
                .compose(RxUtils.applyIOSchedulers())
                .subscribe(scheduleResponse -> {
                    mRefreshLayout.setRefreshing(false);
                    loadScheduleData(scheduleResponse.reservations);
                }, throwable -> {
                    mRefreshLayout.setRefreshing(false);
                    Snackbar.make(mRecycler, throwable.getLocalizedMessage(), Snackbar.LENGTH_SHORT).show();
                });
    }
}
