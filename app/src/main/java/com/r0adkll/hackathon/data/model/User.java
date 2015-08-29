package com.r0adkll.hackathon.data.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.data.model
 * Created by drew.heavner on 8/28/15.
 */
@JsonObject
@Table("user")
public class User extends Model{

    @JsonField
    @Column("name")
    public String name;

    @JsonField
    @Column("email")
    public String email;

    @JsonField
    @Column("phone")
    public String phone;

    @JsonField
    @Column("access_token")
    public String access_token;

}
