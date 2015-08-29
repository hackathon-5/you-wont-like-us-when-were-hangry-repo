package com.r0adkll.hackathon.api;

import android.content.Context;

import com.r0adkll.hackathon.BuildConfig;
import com.r0adkll.hackathon.util.LoganSquareConverter;
import com.r0adkll.hackathon.util.qualifiers.ApiUrl;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.Converter;

@Module
public class ApiModule {

    @Provides @Singleton
    Endpoint provideApiEndpoint(@ApiUrl String url){
        return Endpoints.newFixedEndpoint(url);
    }

    @Provides @Singleton
    OkHttpClient provideOkHttpClient(){
        return new OkHttpClient();
    }

    @Provides @Singleton
    Client provideClient(OkHttpClient client) {
        return new OkClient(client);
    }

    @Provides @Singleton
    Converter provideRetrofitConverter(){
        return new LoganSquareConverter();
    }

    @Provides @Singleton
    RestAdapter provideRestAdapter(Context ctx,
                                   Endpoint endpoint,
                                   Client client,
                                   Converter converter,
                                   ApiHeaders headers) {

        return new RestAdapter.Builder()
                .setClient(client)
                .setEndpoint(endpoint)
                .setRequestInterceptor(headers)
                .setErrorHandler(new ErrorHandler(ctx))
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.HEADERS_AND_ARGS : RestAdapter.LogLevel.BASIC)
                .setConverter(converter)
                .build();
    }



}
