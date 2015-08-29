package com.r0adkll.hackathon.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.data.model
 * Created by drew.heavner on 8/29/15.
 */
@JsonObject
public class Reservations implements Parcelable{

    @JsonField
    public String date;

    @JsonField
    public List<Integer> times;

    public Reservations(){
        times = new ArrayList<>();
    }

    protected Reservations(Parcel in) {
        date = in.readString();
        int[] timez = in.createIntArray();
        times = new ArrayList<>();
        for (int i = 0; i < timez.length; i++) {
            times.add(timez[0]);
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        int[] timez = new int[times.size()];
        for (int i = 0; i < times.size(); i++) {
            timez[i] = times.get(i);
        }
        dest.writeIntArray(timez);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Reservations> CREATOR = new Creator<Reservations>() {
        @Override
        public Reservations createFromParcel(Parcel in) {
            return new Reservations(in);
        }

        @Override
        public Reservations[] newArray(int size) {
            return new Reservations[size];
        }
    };

    @Override
    public String toString() {
        return "Reservations{" +
                "date='" + date + '\'' +
                ", times=" + times +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservations that = (Reservations) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return !(times != null ? !times.equals(that.times) : that.times != null);

    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (times != null ? times.hashCode() : 0);
        return result;
    }
}
