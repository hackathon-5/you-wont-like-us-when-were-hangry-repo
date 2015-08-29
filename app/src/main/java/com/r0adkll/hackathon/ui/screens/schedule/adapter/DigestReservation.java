package com.r0adkll.hackathon.ui.screens.schedule.adapter;

import com.r0adkll.hackathon.data.model.Pet;
import com.r0adkll.hackathon.data.model.Reservation;
import com.r0adkll.hackathon.data.model.ReservationTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.ui.screens.schedule.adapter
 * Created by drew.heavner on 8/29/15.
 */
public class DigestReservation {

    public static Observable<List<DigestReservation>> generate(List<Reservation> reservations){
        List<DigestReservation> digest = new ArrayList<>();

        // 1) sort data
        final SimpleDateFormat ResFormat = new SimpleDateFormat("yyyy-MM-dd");
        Collections.sort(reservations, (lhs, rhs) -> {
            try {
                Date lhsd = ResFormat.parse(lhs.date);
                Date rhsd = ResFormat.parse(rhs.date);
                return lhsd.compareTo(rhsd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        });

        return Observable.from(reservations)
                .flatMap(reservation -> reservation.getTimes())
                .flatMap(reservationTimes -> Observable.from(reservationTimes))
                .map(rt -> {
                    DigestReservation drv = new DigestReservation();
                    drv.pet = rt.reservation.pet;
                    drv.date = rt.reservation.date;
                    drv.startTime = rt.time;
                    drv.endTime = rt.time + (rt.time % 100 == 0 ? 30 : 70);
                    return drv;
                })
                .collect(() -> new ArrayList<>(), (digestReservations, digestReservation) -> digestReservations.add(digestReservation));
    }

    public String date;
    public int startTime;
    public int endTime;
    public Pet pet;

    public DigestReservation(){}


}
