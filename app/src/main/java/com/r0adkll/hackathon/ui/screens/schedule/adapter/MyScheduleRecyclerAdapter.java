package com.r0adkll.hackathon.ui.screens.schedule.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ftinc.kit.adapter.BetterRecyclerAdapter;
import com.ftinc.kit.font.Face;
import com.ftinc.kit.font.FontLoader;
import com.r0adkll.hackathon.R;
import com.r0adkll.hackathon.data.model.Reservation;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.ui.screens.schedule.adapter
 * Created by drew.heavner on 8/29/15.
 */
public class MyScheduleRecyclerAdapter extends BetterRecyclerAdapter<DigestReservation, MyScheduleRecyclerAdapter.ReservationViewHolder> implements StickyRecyclerHeadersAdapter<MyScheduleRecyclerAdapter.HeaderViewHolder>{

    private Activity mActivity;

    public final SimpleDateFormat HEADER_FORMAT = new SimpleDateFormat("EEE'\n'M/dd");
    public final SimpleDateFormat RESERVATION_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public MyScheduleRecyclerAdapter(Activity mActivity) {
        super();
        this.mActivity = mActivity;
    }

    @Override
    public ReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReservationViewHolder(mActivity.getLayoutInflater()
                .inflate(R.layout.item_layout_my_schedule, parent, false));
    }

    @Override
    public void onBindViewHolder(ReservationViewHolder holder, int i) {
        super.onBindViewHolder(holder, i);
        DigestReservation rv = getItem(i);

        Glide.with(mActivity)
                .load(rv.pet.photoUrl)
                .placeholder(new ColorDrawable(Color.GRAY))
                .crossFade()
                .into(holder.image);

        holder.title.setText(rv.pet.name);

        String subtitle = String.format("%s - %s at %s", toReadable(rv.startTime), toReadable(rv.endTime), rv.pet.shelter.name);
        holder.subtitle.setText(subtitle);
    }

    private String toReadable(int time){
        int hour = time / 100;
        int minute = time % 100;
        return String.format("%1d:%2$d", hour, minute);
    }

    @Override
    public long getHeaderId(int i) {
        if(i>-1){
            DigestReservation rv = getItem(i);
            try {
                Date date = RESERVATION_FORMAT.parse(rv.date);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                Calendar ncal = Calendar.getInstance();
                ncal.clear();
                ncal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
                ncal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
                ncal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
                return ncal.getTimeInMillis();
            } catch (ParseException e) {}
        }
        return -1;
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        return new HeaderViewHolder(mActivity.getLayoutInflater().inflate(R.layout.layout_myschedule_header, viewGroup, false));
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder headerViewHolder, int i) {
        DigestReservation rs = getItem(i);
        try {
            Date date = RESERVATION_FORMAT.parse(rs.date);
            String title = HEADER_FORMAT.format(date);
            headerViewHolder.title.setText(title);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * The Header View Holder
     */
    static class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            title = ButterKnife.findById(itemView, R.id.title);
        }
    }

    static class ReservationViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.image)
        ImageView image;

        @Bind(R.id.title)
        TextView title;

        @Bind(R.id.subtitle)
        TextView subtitle;

        public ReservationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
