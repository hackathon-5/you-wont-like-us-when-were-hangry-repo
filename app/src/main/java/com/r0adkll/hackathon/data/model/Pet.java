package com.r0adkll.hackathon.data.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.data.model
 * Created by drew.heavner on 8/28/15.
 */
@JsonObject
public class Pet {

    public static final String TYPE_DOG = "dog";
    public static final String TYPE_CAT = "cat";

    @JsonField
    public String name;

    // 'dog' or 'cat'
    @JsonField
    public String type;

    @JsonField
    public String photoUrl;

    @JsonField
    public String description;

    @JsonField
    public float age;

    @JsonField
    public float avgScore;

    @JsonField
    public Shelter shelter;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pet pet = (Pet) o;

        if (Float.compare(pet.age, age) != 0) return false;
        if (Float.compare(pet.avgScore, avgScore) != 0) return false;
        if (name != null ? !name.equals(pet.name) : pet.name != null) return false;
        if (type != null ? !type.equals(pet.type) : pet.type != null) return false;
        if (photoUrl != null ? !photoUrl.equals(pet.photoUrl) : pet.photoUrl != null) return false;
        if (description != null ? !description.equals(pet.description) : pet.description != null)
            return false;
        return !(shelter != null ? !shelter.equals(pet.shelter) : pet.shelter != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (photoUrl != null ? photoUrl.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (age != +0.0f ? Float.floatToIntBits(age) : 0);
        result = 31 * result + (avgScore != +0.0f ? Float.floatToIntBits(avgScore) : 0);
        result = 31 * result + (shelter != null ? shelter.hashCode() : 0);
        return result;
    }
}
