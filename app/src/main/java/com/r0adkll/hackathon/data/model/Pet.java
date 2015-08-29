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

}
