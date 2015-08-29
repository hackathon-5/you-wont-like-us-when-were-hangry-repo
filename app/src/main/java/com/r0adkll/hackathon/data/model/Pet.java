package com.r0adkll.hackathon.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

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
 * Created by drew.heavner on 8/28/15.
 */
@JsonObject
@Table("pets")
public class Pet extends Model {

    public static final String TYPE_DOG = "dog";
    public static final String TYPE_CAT = "cat";

    @JsonField
    @Column("name")
    public String name;

    // 'dog' or 'cat'
    @JsonField
    @Column("type")
    public String type;

    @JsonField
    @Column("photo_url")
    public String photoUrl;

    @JsonField
    @Column("description")
    public String description;

    @JsonField
    @Column("age")
    public Float age;

    @JsonField
    @Column("score")
    public Float avgScore;

    @JsonField
    @Column("shelter")
    @ForeignKey(
        onUpdate = ForeignKey.ReferentialAction.CASCADE,
        onDelete = ForeignKey.ReferentialAction.CASCADE
    )
    public Shelter shelter;

    public Observable<List<Reservation>> getReservations(){
        return Select.from(Reservation.class)
                .where("pet=?", id)
                .observable();
    }

}
