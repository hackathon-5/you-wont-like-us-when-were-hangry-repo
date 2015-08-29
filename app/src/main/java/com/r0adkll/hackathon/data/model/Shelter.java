package com.r0adkll.hackathon.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;
import ollie.query.Select;
import rx.Observable;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.data.model
 * Created by drew.heavner on 8/29/15.
 */
@JsonObject
@Table("shelters")
public class Shelter extends Model{

    @JsonField
    @Column("name")
    public String name;

    @JsonField
    @Column("description")
    public String description;

    @JsonField
    @Column("address")
    public String address;

    @JsonField
    @Column("latitude")
    public Float latitude;

    @JsonField
    @Column("longitude")
    public Float longitude;

    @JsonField
    @Column("distance")
    public Integer distance; // in meters

    public Observable<List<Pet>> getPets(){
        return Select.from(Pet.class)
                .where("shelter=?", id)
                .observable();
    }

    public float getDistanceMiles(){
        return distance * .00062f;
    }


}
