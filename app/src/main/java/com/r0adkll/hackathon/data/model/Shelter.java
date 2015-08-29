package com.r0adkll.hackathon.data.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.data.model
 * Created by drew.heavner on 8/29/15.
 */
@JsonObject
public class Shelter {

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

}
