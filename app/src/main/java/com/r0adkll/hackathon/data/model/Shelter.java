package com.r0adkll.hackathon.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.data.model
 * Created by drew.heavner on 8/29/15.
 */
@JsonObject
public class Shelter implements Parcelable{

    @JsonField
    public int id;

    @JsonField
    public String name;

    @JsonField
    public String description;

    @JsonField
    public String address;

    @JsonField
    public float latitude;

    @JsonField
    public float longitude;

    @JsonField
    public int distance; // in meters

    public Shelter(){}

    public float getDistanceMiles(){
        return distance * .00062f;
    }

    protected Shelter(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        address = in.readString();
        latitude = in.readFloat();
        longitude = in.readFloat();
        distance = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(address);
        dest.writeFloat(latitude);
        dest.writeFloat(longitude);
        dest.writeInt(distance);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Shelter> CREATOR = new Creator<Shelter>() {
        @Override
        public Shelter createFromParcel(Parcel in) {
            return new Shelter(in);
        }

        @Override
        public Shelter[] newArray(int size) {
            return new Shelter[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shelter shelter = (Shelter) o;

        if (id != shelter.id) return false;
        if (Float.compare(shelter.latitude, latitude) != 0) return false;
        if (Float.compare(shelter.longitude, longitude) != 0) return false;
        if (distance != shelter.distance) return false;
        if (name != null ? !name.equals(shelter.name) : shelter.name != null) return false;
        if (description != null ? !description.equals(shelter.description) : shelter.description != null)
            return false;
        return !(address != null ? !address.equals(shelter.address) : shelter.address != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (latitude != +0.0f ? Float.floatToIntBits(latitude) : 0);
        result = 31 * result + (longitude != +0.0f ? Float.floatToIntBits(longitude) : 0);
        result = 31 * result + distance;
        return result;
    }
}
