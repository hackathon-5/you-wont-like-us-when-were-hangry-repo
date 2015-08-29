package com.r0adkll.hackathon.api;


import com.r0adkll.hackathon.data.model.User;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;
import rx.Observable;

/**
 * This is the api service definition for retrofit. Here we will define the entire API out
 * using the Annotated methods of retrofit.
 */
public interface ApiService {

    @FormUrlEncoded
    @POST("/login")
    Observable<User> login(@Field("email") String email,
                           @Field("password") String pswd);

    @FormUrlEncoded
    @POST("/signup")
    Observable<User> signup(@Field("email") String email,
                            @Field("password") String pswd,
                            @Field("name") String name,
                            @Field("phone") String phone);

}
