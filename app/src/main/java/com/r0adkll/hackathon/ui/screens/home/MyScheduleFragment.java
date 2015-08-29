package com.r0adkll.hackathon.ui.screens.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftinc.kit.util.RxUtils;
import com.ftinc.kit.widget.EmptyView;
import com.r0adkll.hackathon.App;
import com.r0adkll.hackathon.R;
import com.r0adkll.hackathon.api.ApiService;
import com.r0adkll.hackathon.data.model.Reservation;
import com.r0adkll.hackathon.ui.model.BaseFragment;
import com.r0adkll.hackathon.ui.screens.schedule.adapter.DigestReservation;
import com.r0adkll.hackathon.ui.screens.schedule.adapter.MyScheduleRecyclerAdapter;
import com.r0adkll.hackathon.ui.widget.StickyRecyclerHeadersDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.app.AppObservable;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.ui.screens.home
 * Created by drew.heavner on 8/29/15.
 */
public class MyScheduleFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public static MyScheduleFragment createInstance() {
        return new MyScheduleFragment();
    }

    @Bind(R.id.appbar)
    Toolbar mAppbar;
    @Bind(R.id.recycler)
    RecyclerView mRecycler;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.empty_view)
    EmptyView mEmptyView;
    private MyScheduleRecyclerAdapter mAdapter;

    @Inject
    ApiService mApi;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAppbar.setVisibility(View.GONE);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_my_schedule, container, false);
    }

    @Override
    protected void setupComponent() {
        App.get(getActivity()).component().inject(this);
    }

    private void init(){


        mRefreshLayout.setColorSchemeResources(R.color.primary, R.color.primary_dark, R.color.accent);
        mRefreshLayout.setOnRefreshListener(this);

        mAdapter = new MyScheduleRecyclerAdapter(getActivity());
        mAdapter.setEmptyView(mEmptyView);

        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.addItemDecoration(new StickyRecyclerHeadersDecoration(mAdapter));

        onRefresh();
    }

    private void loadScheduleData(List<Reservation> schedule){
        mAdapter.clear();
        mAdapter.addAll(DigestReservation.generate(schedule));
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onRefresh() {
        if(!mRefreshLayout.isRefreshing()) mRefreshLayout.setRefreshing(true);
        AppObservable.bindSupportFragment(this, mApi.getMySchedule())
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
