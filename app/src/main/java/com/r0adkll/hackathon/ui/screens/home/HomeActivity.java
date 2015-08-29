package com.r0adkll.hackathon.ui.screens.home;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ftinc.kit.adapter.BetterRecyclerAdapter;
import com.ftinc.kit.attributr.ui.widget.StickyRecyclerHeadersElevationDecoration;
import com.ftinc.kit.util.RxUtils;
import com.ftinc.kit.util.UIUtils;
import com.ftinc.kit.widget.EmptyView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.r0adkll.hackathon.AppComponent;
import com.r0adkll.hackathon.R;
import com.r0adkll.hackathon.api.ApiService;
import com.r0adkll.hackathon.api.model.FindPetsResponse;
import com.r0adkll.hackathon.data.model.Pet;
import com.r0adkll.hackathon.ui.model.BaseActivity;
import com.r0adkll.hackathon.ui.screens.detail.DetailActivity;
import com.r0adkll.hackathon.ui.screens.home.adapter.HomeItem;
import com.r0adkll.hackathon.ui.screens.home.adapter.HomeRecyclerAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.app.AppObservable;
import rx.functions.Action1;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.ui.screens.home
 * Created by drew.heavner on 8/29/15.
 */
public class HomeActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BetterRecyclerAdapter.OnItemClickListener<HomeItem> {

    @Bind(R.id.recycler)
    RecyclerView mRecycler;

    @Bind(R.id.empty_view)
    EmptyView mEmptyView;

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @Inject
    ApiService mApi;


    private GoogleApiClient mClient;
    private HomeRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mRefreshLayout.setColorSchemeResources(R.color.primary, R.color.primary_dark, R.color.accent);
        mRefreshLayout.setOnRefreshListener(this);

        mAdapter = new HomeRecyclerAdapter(this);
        mAdapter.setEmptyView(mEmptyView);
        mAdapter.setOnItemClickListener(this);

        mRecycler.setAdapter(mAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setSpanSizeLookup(mAdapter.getSpanLookup());
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setItemAnimator(new DefaultItemAnimator());

        // Connect to google play
        setupGooglePlay();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!mClient.isConnected() || !mClient.isConnecting()){
            mClient.connect();
        }
    }

    private void setPetData(List<HomeItem> data){
        mAdapter.clear();
        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    private void loadPetsFromServer(LatLng location){

        AppObservable.bindActivity(this, mApi.findPets(location.latitude, location.longitude))
                .compose(RxUtils.applyIOSchedulers())
                .map(findPetsResponse -> HomeItem.convert(findPetsResponse.pets))
                .subscribe(homeItems -> {
                    mRefreshLayout.setRefreshing(false);
                    setPetData(homeItems);
                }, throwable -> {
                    Snackbar.make(mRecycler, throwable.getLocalizedMessage(), Snackbar.LENGTH_SHORT).show();
                    mRefreshLayout.setRefreshing(false);
                });

    }

    private void refreshLocation(){
        // make request to track location to report to the server
        LocationRequest request = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setNumUpdates(1)
                .setSmallestDisplacement(0)
                .setInterval(1000)
                .setFastestInterval(0);

        LocationServices.FusedLocationApi
                .requestLocationUpdates(mClient, request, location -> {
                    Timber.i("Location found!!! Pull from server");
                    LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                    loadPetsFromServer(loc);
                });
    }

    private void setupGooglePlay(){

        mClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        Timber.i("Connected to GooglePlay Services");

                        // Attempt to find previous location
                        Location lastLocation = LocationServices.FusedLocationApi
                                .getLastLocation(mClient);
                        if(lastLocation != null){
                            LatLng loc = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                            loadPetsFromServer(loc);
                        }else {
                            refreshLocation();
                        }
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        mClient.connect();
                    }
                })
                .addOnConnectionFailedListener(connectionResult -> Snackbar.make(mRecycler, "Unable to connect to Google Play Services", Snackbar.LENGTH_SHORT).show()).build();

    }

    @Override
    protected void setupComponent(AppComponent appGraph) {
        appGraph.inject(this);
    }

    @Override
    public void onRefresh() {
        refreshLocation();
    }

    @Override
    public void onItemClick(View view, HomeItem item, int i) {
        Timber.i("Pet clicked: %s", item.item.name);

        Intent intent = DetailActivity.createIntent(this, item.item);
        UIUtils.startActivityWithTransition(this, intent, view, "pet_image");

    }
}
