package com.r0adkll.hackathon;

import com.r0adkll.hackathon.api.ApiService;
import com.r0adkll.hackathon.api.MockApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon
 * Created by drew.heavner on 8/28/15.
 */
@Module
public class InfoModule {

    @Provides @Singleton
    ApiService provideApiService(RestAdapter adapter){
        return new MockApiService();
    }

}
