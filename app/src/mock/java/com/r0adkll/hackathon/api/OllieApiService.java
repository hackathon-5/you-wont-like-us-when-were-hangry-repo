package com.r0adkll.hackathon.api;

import com.r0adkll.hackathon.api.model.FindPetsResponse;
import com.r0adkll.hackathon.api.model.ScheduleRequest;
import com.r0adkll.hackathon.api.model.ScheduleResponse;
import com.r0adkll.hackathon.api.model.SuccessResponse;
import com.r0adkll.hackathon.data.model.User;

import retrofit.http.Body;
import retrofit.http.Field;
import rx.Observable;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.api
 * Created by drew.heavner on 8/29/15.
 */
public class OllieApiService implements ApiService {



    @Override
    public Observable<User> login(@Field("email") String email, @Field("password") String pswd) {
        return null;
    }

    @Override
    public Observable<User> signup(@Field("email") String email, @Field("password") String pswd, @Field("name") String name, @Field("phone") String phone) {
        return null;
    }

    @Override
    public Observable<FindPetsResponse> findPets(@Field("lat") double latitude, @Field("lon") double longitude) {
        return null;
    }

    @Override
    public Observable<SuccessResponse> schedule(@Body ScheduleRequest request) {
        return null;
    }

    @Override
    public Observable<ScheduleResponse> getMySchedule() {
        return null;
    }
}
