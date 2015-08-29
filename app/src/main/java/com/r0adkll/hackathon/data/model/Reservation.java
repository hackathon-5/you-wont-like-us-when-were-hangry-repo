package com.r0adkll.hackathon.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;
import java.util.List;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.ForeignKey;
import ollie.annotation.Table;
import ollie.query.Select;
import rx.Observable;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.data.model
 * Created by drew.heavner on 8/29/15.
 */
@JsonObject
@Table("reservations")
public class Reservation extends Model{

    @JsonField
    @Column("date")
    public String date;

    @JsonField
    @Column("pet")
    @ForeignKey(
        onUpdate = ForeignKey.ReferentialAction.CASCADE,
        onDelete = ForeignKey.ReferentialAction.CASCADE
    )
    public Pet pet;

    public Observable<List<ReservationTime>> getTimes(){
        return Select.from(ReservationTime.class)
                .where("reservation=?", id)
                .observable();
    }

}
