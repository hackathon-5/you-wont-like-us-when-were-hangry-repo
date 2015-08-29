package com.r0adkll.hackathon.ui.screens.home;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftinc.kit.adapter.BetterRecyclerAdapter;
import com.ftinc.kit.util.RxUtils;
import com.ftinc.kit.util.UIUtils;
import com.ftinc.kit.widget.EmptyView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.r0adkll.hackathon.App;
import com.r0adkll.hackathon.R;
import com.r0adkll.hackathon.api.ApiService;
import com.r0adkll.hackathon.ui.model.BaseFragment;
import com.r0adkll.hackathon.ui.screens.detail.DetailActivity;
import com.r0adkll.hackathon.ui.screens.home.adapter.HomeItem;
import com.r0adkll.hackathon.ui.screens.home.adapter.HomeRecyclerAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rx.android.app.AppObservable;
import timber.log.Timber;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.ui.screens.home
 * Created by drew.heavner on 8/29/15.
 */
public class FindPetFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BetterRecyclerAdapter.OnItemClickListener<HomeItem> {

    public static FindPetFragment createInstance() {
        return new FindPetFragment();
    }

    @Bind(R.id.appbar)
    Toolbar mAppbar;

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAppbar.setVisibility(View.GONE);

        mRefreshLayout.setColorSchemeResources(R.color.primary, R.color.primary_dark, R.color.accent);
        mRefreshLayout.setOnRefreshListener(this);

        mAdapter = new HomeRecyclerAdapter(getActivity());
        mAdapter.setEmptyView(mEmptyView);
        mAdapter.setOnItemClickListener(this);

        mRecycler.setAdapter(mAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        layoutManager.setSpanSizeLookup(mAdapter.getSpanLookup());
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setItemAnimator(new DefaultItemAnimator());

        // Connect to google play
        setupGooglePlay();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home, container, false);
    }

    @Override
    protected void setupComponent() {
        super.setupComponent();
        App.get(getActivity()).component().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!mClient.isConnected() || !mClient.isConnecting()){
            mClient.connect();
        }
    }

    @Override
    public void onRefresh() {
        refreshLocation();
    }

    @Override
    public void onItemClick(View view, HomeItem item, int i) {
        Timber.i("Pet clicked: %s", item.item.name);

        Intent intent = DetailActivity.createIntent(getActivity(), item.item);
        UIUtils.startActivityWithTransition(getActivity(), intent, view, "pet_image");

    }

    private void setPetData(List<HomeItem> data){
        mAdapter.clear();
        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    private void loadPetsFromServer(LatLng location){

        AppObservable.bindSupportFragment(this, mApi.findPets(location.latitude, location.longitude))
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

        mClient = new GoogleApiClient.Builder(getActivity())
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
}
