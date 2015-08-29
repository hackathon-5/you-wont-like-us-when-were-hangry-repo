package com.r0adkll.hackathon.ui.screens.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.r0adkll.hackathon.AppComponent;
import com.r0adkll.hackathon.R;
import com.r0adkll.hackathon.data.model.Pet;
import com.r0adkll.hackathon.ui.model.BaseActivity;
import com.r0adkll.hackathon.ui.screens.schedule.ScheduleActivity;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ollie.Model;
import pocketknife.BindExtra;
import pocketknife.SaveState;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.ui.screens.detail
 * Created by drew.heavner on 8/29/15.
 */
public class DetailActivity extends BaseActivity implements View.OnClickListener {

    public static Intent createIntent(Context ctx, Pet pet) {
        Intent intent = new Intent(ctx, DetailActivity.class);
        intent.putExtra(EXTRA_PET, pet.id);
        return intent;
    }

    public static final String EXTRA_PET = "extra_pet";

    @Bind(R.id.pet_image)
    ImageView mPetImage;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @Bind(R.id.application_bar)
    AppBarLayout mApplicationBar;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.subtitle)
    TextView mSubtitle;
    @Bind(R.id.rating)
    RatingBar mRating;
    @Bind(R.id.description)
    TextView mDescription;
    @Bind(R.id.action_schedule)
    Button mActionSchedule;
    @Bind(R.id.content_scroll_view)
    NestedScrollView mContentScrollView;
    @Bind(R.id.main_content)
    CoordinatorLayout mMainContent;

    @SaveState
    @BindExtra(EXTRA_PET)
    Long mPetId;

    Pet mPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mPet = Model.find(Pet.class, mPetId);

        Slidr.attach(this, new SlidrConfig.Builder()
                .position(SlidrPosition.LEFT)
                .primaryColor(Color.TRANSPARENT)
                .secondaryColor(getResources().getColor(R.color.black40))
                .distanceThreshold(1f / 3f)
                .velocityThreshold(2400f)
                .edge(true)
                .build());

        getAppBar().setNavigationOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Glide.with(this)
                .load(mPet.photoUrl)
                .into(mPetImage);

        // Bind UI
        mCollapsingToolbar.setTitle(mPet.name);
        mTitle.setText(String.format("%.1f year%s old", mPet.age, mPet.age > 1 ? "s" : ""));
        mSubtitle.setText(String.format("%s (%.1f miles away)", mPet.shelter.name, mPet.shelter.getDistanceMiles()));
        mDescription.setText(mPet.description);
        mRating.setRating(mPet.avgScore);

    }

    @OnClick(R.id.action_schedule)
    void onScheduleClicked(){
        Intent intent = ScheduleActivity.createIntent(this, mPet);
        startActivity(intent);


    }

    @Override
    protected void setupComponent(AppComponent appGraph) {
        appGraph.inject(this);
    }

    @Override
    public void onClick(View v) {
        supportFinishAfterTransition();
    }
}
