package com.r0adkll.hackathon.ui.screens.schedule;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.view.ActionMode;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ftinc.kit.util.RxUtils;
import com.r0adkll.hackathon.AppComponent;
import com.r0adkll.hackathon.R;
import com.r0adkll.hackathon.api.ApiService;
import com.r0adkll.hackathon.api.model.ScheduleRequest;
import com.r0adkll.hackathon.api.model.SuccessResponse;
import com.r0adkll.hackathon.data.model.Pet;
import com.r0adkll.hackathon.data.model.Reservation;
import com.r0adkll.hackathon.ui.model.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import butterknife.Bind;
import pocketknife.BindExtra;
import pocketknife.SaveState;
import rx.android.app.AppObservable;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.ui.screens.schedule
 * Created by drew.heavner on 8/29/15.
 */
public class ScheduleActivity extends BaseActivity implements View.OnClickListener {

    public static Intent createIntent(Context ctx, Pet pet){
        Intent intent = new Intent(ctx, ScheduleActivity.class);
        intent.putExtra(EXTRA_PET, pet);
        return intent;
    }

    public static final String EXTRA_PET = "extra_pet";
    public final SimpleDateFormat HEADER_FORMAT = new SimpleDateFormat("EEE M/dd");
    public final SimpleDateFormat RESERVATION_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Bind({R.id.day1, R.id.day2, R.id.day3, R.id.day4, R.id.day5, R.id.day6, R.id.day7})
    List<TextView> mDays;

    @Bind({R.id.day_column_1,R.id.day_column_2,R.id.day_column_3,R.id.day_column_4,R.id.day_column_5,R.id.day_column_6,R.id.day_column_7})
    List<LinearLayout> mColumns;

    @Bind(R.id.column_container)
    LinearLayout mColumnContainer;

    @Inject
    ApiService mApi;

    @SaveState
    @BindExtra(EXTRA_PET)
    Pet mPet;

    private ActionMode mActionMode;
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray();
    private List<View> mCells = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getAppBar().setNavigationOnClickListener(this);

        Drawable close = getDrawable(R.drawable.ic_clear_24dp);
        close.setTint(Color.WHITE);
        getAppBar().setNavigationIcon(close);

        setupWeekHeaders();
        setupAvailableSchedule();
    }


    private void setupWeekHeaders(){
        Calendar now = Calendar.getInstance();

        for (int i = 0; i < mDays.size(); i++) {
            TextView header = mDays.get(0);
            header.setText(HEADER_FORMAT.format(now.getTime()).toUpperCase());
            now.add(Calendar.DAY_OF_YEAR, 1);
        }
    }

    private void setupAvailableSchedule(){
        mCells.clear();
        Calendar now = Calendar.getInstance();

        for (int i = 0; i < mColumns.size(); i++) {
            LinearLayout column = mColumns.get(i);
            View header = column.getChildAt(0);
            column.removeAllViews();
            column.addView(header);

            // Compute needed date format key
            String key = RESERVATION_FORMAT.format(now.getTime());

            // Now find the array of scheduled reservations for the day
            List<Integer> reservations = new ArrayList<>();
            if(mPet.reservations != null && !mPet.reservations.isEmpty()){
                for (int j = 0; j < mPet.reservations.size(); j++) {
                    Reservation rv = mPet.reservations.get(j);
                    if(rv.date.equals(key)) {
                        if (rv.times != null && !rv.times.isEmpty()) {
                            for (int k = 0; k < rv.times.size(); k++) {
                                reservations.add(rv.times.get(k));
                            }
                        }
                        break;
                    }
                }
            }

            // Now iterate through the number of available slots in a given day and generate the available columns
            for (int j = 0; j < 16; j++) {
                int timeSlot = getTimeSlot(j);

                Timber.i("Preparing to generate item for [%d] (%d)", timeSlot, reservations.size());

                if(!reservations.contains(Integer.valueOf(timeSlot))){

                    Timber.d("Generating cell: %s", reservations);
                    // Generate row item
                    View cell = getLayoutInflater().inflate(R.layout.item_layout_schedule, column, false);
                    cell.setTag(R.id.tag_timeslot, timeSlot);
                    cell.setTag(R.id.tag_date, key);
                    cell.setOnClickListener(mCellClickListener);
                    column.addView(cell);
                    mCells.add(cell);
                }else{
                    View blank = new View(this);
                    int width = getResources().getDimensionPixelSize(R.dimen.schedule_cell_width);
                    int height = getResources().getDimensionPixelSize(R.dimen.schedule_cell_height);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
                    column.addView(blank, params);
                }

            }


            now.add(Calendar.DAY_OF_YEAR, 1);
        }


    }

    private int getTimeSlot(int index){
        return 900 + ((index / 2) * 100) + ((index % 2) * 30);
    }

    private List<Reservation> getSelectedReservations(){
        List<Reservation> reservations = new ArrayList<>();

        Map<String, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < mCells.size(); i++) {
            View cell = mCells.get(i);
            int time = (int) cell.getTag(R.id.tag_timeslot);
            String date = (String) cell.getTag(R.id.tag_date);
            int code = date.hashCode() + time;

            boolean isSelected = mSelectedItems.get(code, false);
            if(isSelected){
                List<Integer> times = map.get(date);
                if(times == null){
                    times = new ArrayList<>();
                }

                times.add(time);
                map.put(date, new ArrayList<>(times));
            }
        }

        Set<String> keys = map.keySet();
        for (String key : keys) {
            List<Integer> times = map.get(key);
            Reservation rv = new Reservation();
            rv.date = key;
            rv.times = new ArrayList<>(times);
            reservations.add(rv);
        }

        return reservations;
    }

    private View.OnClickListener mCellClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int time = (int) v.getTag(R.id.tag_timeslot);
            String date = (String) v.getTag(R.id.tag_date);
            int code = date.hashCode() + time;

            if(mActionMode == null){
                mSelectedItems.put(code, true);
                v.setBackgroundResource(R.drawable.schedule_item_selected);
                mActionMode = startSupportActionMode(mActionModeCallback);
            }else{
                boolean value = mSelectedItems.get(code, false);
                mSelectedItems.put(code, !value);
                v.setBackgroundResource(value ? R.drawable.schedule_item_unselected : R.drawable.schedule_item_selected);
                mActionMode.invalidate();
            }
        }
    };

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {


        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.schedule_action_mode, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            // Gather the total amount of time
            List<Reservation> rvs = getSelectedReservations();

            if(rvs.isEmpty()){
                mode.finish();
                return false;
            }

            Timber.d("Preparing reservations: %s", rvs);

            int reservations = 0;
            for (int i = 0; i < rvs.size(); i++) {
                reservations += rvs.get(i).times.size();
            }
            int minutes = reservations * 30;
            String title;
            if(minutes <= 120){
                title = String.format("%d minutes", minutes);
            }else{
                float hours = ((float)minutes) / 60f;
                title = String.format("%.1f hour%s", hours, hours == 1 ? "s" : "");
            }

            mode.setTitle(title);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if(item.getItemId() == R.id.action_submit){
                // Get all selected Reservations, and send them to server
                List<Reservation> rvs = getSelectedReservations();

                AppObservable.bindActivity(ScheduleActivity.this, mApi.schedule(new ScheduleRequest(rvs)))
                        .compose(RxUtils.applyIOSchedulers())
                        .subscribe(new Action1<SuccessResponse>() {
                            @Override
                            public void call(SuccessResponse successResponse) {

                                Timber.d("Pet reservations b4: %s", mPet.reservations);

                                // Delete Reservations from view
                                for (Reservation rv : rvs) {
                                    for (Reservation reservation : mPet.reservations) {
                                        if(reservation.date.equals(rv.date)){
                                            reservation.times.addAll(rv.times);
                                        }
                                    }
                                }

                                Timber.d("Pet reservations after: %s", mPet.reservations);

                                setupAvailableSchedule();
                                mode.setTag("scheduled");
                                mode.finish();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Timber.e(throwable, "Oh crap :(");
                                Snackbar.make(mColumnContainer, "Something went wrong trying to schedule volunteer hours. Please try again.", Snackbar.LENGTH_SHORT).show();
                            }
                        });



                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            String tag = (String) mode.getTag();

            if(TextUtils.isEmpty(tag) || !tag.equals("scheduled")) {
                for (View cell : mCells) {
                    cell.setBackgroundResource(R.drawable.schedule_item_unselected);
                }
            }

            mSelectedItems.clear();
            mActionMode = null;
        }
    };



    @Override
    protected void setupComponent(AppComponent appGraph) {
        appGraph.inject(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
