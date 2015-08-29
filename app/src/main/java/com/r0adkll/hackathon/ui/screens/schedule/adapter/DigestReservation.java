package com.r0adkll.hackathon.ui.screens.schedule.adapter;

import com.r0adkll.hackathon.data.model.Pet;
import com.r0adkll.hackathon.data.model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.ui.screens.schedule.adapter
 * Created by drew.heavner on 8/29/15.
 */
public class DigestReservation {

    public static List<DigestReservation> generate(List<Reservation> reservations){
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

        // For each item collapse/generate digest reservations
        for (int i = 0; i < reservations.size(); i++) {
            Reservation rv = reservations.get(i);

            if(rv.times != null && !rv.times.isEmpty()) {
                for (int j = 1; j < rv.times.size(); j++) {
                    int timeSlot = rv.times.get(j);

                    DigestReservation drv = new DigestReservation();
                    drv.pet = rv.pet;
                    drv.date = rv.date;
                    drv.startTime = timeSlot;
                    drv.endTime = timeSlot + (timeSlot % 100 == 0 ? 30 : 70);
                    digest.add(drv);
                }
            }
        }

        return digest;
    }

    public String date;
    public int startTime;
    public int endTime;
    public Pet pet;

    public DigestReservation(){}


}
