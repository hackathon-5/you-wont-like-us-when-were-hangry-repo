package com.r0adkll.hackathon;

import android.content.Context;

import com.r0adkll.hackathon.util.qualifiers.ApiUrl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon
 * Created by drew.heavner on 8/28/15.
 */
@Module
public class AppModule {

    private App mApp;

    public AppModule(App app) {
        mApp = app;
    }

    @Provides @Singleton
    Context provideApplicationContext(){
        return mApp;
    }

    @Provides @Singleton @ApiUrl
    String provideApiUrl(){
        return "http://0.0.0.0/v1";
    }

}
