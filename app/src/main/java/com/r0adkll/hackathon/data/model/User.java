package com.r0adkll.hackathon.data.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.data.model
 * Created by drew.heavner on 8/28/15.
 */
@JsonObject
public class User {

    @JsonField
    public String name;

    @JsonField
    public String email;

    @JsonField
    public String phone;

    @JsonField
    public String token;

}
