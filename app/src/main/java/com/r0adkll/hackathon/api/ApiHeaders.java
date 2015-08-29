package com.r0adkll.hackathon.api;

import com.ftinc.kit.preferences.StringPreference;
import com.r0adkll.hackathon.util.qualifiers.Token;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RequestInterceptor;

@Singleton
public final class ApiHeaders implements RequestInterceptor {

    @Inject @Token
    StringPreference mAccessToken;

    @Inject
    public ApiHeaders(){}

    @Override
    public void intercept(RequestFacade request) {
        if(mAccessToken.isSet()){
            request.addHeader("Authorization" , mAccessToken.get());
        }
    }
}
