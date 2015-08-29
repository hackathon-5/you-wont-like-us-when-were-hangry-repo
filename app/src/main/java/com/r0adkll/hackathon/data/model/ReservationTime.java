package com.r0adkll.hackathon.data.model;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.ForeignKey;
import ollie.annotation.Table;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.data.model
 * Created by drew.heavner on 8/29/15.
 */
@Table("reserve_times")
public class ReservationTime extends Model{

    @Column("time")
    public Integer time;

    @Column("reservation")
    @ForeignKey(
        onUpdate = ForeignKey.ReferentialAction.CASCADE,
        onDelete = ForeignKey.ReferentialAction.CASCADE
    )
    public Reservation reservation;

}
