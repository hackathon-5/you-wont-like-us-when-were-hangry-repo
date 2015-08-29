package com.r0adkll.hackathon.api.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.r0adkll.hackathon.data.model.Reservation;

import java.util.List;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.api.model
 * Created by drew.heavner on 8/29/15.
 */
@JsonObject
public class ScheduleResponse {

    @JsonField
    public List<Reservation> reservations;

    public ScheduleResponse(){}

}
