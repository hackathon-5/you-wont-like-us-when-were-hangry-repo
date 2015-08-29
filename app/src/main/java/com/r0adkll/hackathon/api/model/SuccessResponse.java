package com.r0adkll.hackathon.api.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.api.model
 * Created by drew.heavner on 8/29/15.
 */
@JsonObject
public class SuccessResponse {

    @JsonField
    public String status;

}
