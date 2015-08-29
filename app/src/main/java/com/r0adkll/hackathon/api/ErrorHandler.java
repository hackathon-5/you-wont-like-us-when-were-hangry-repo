/*
 * Copyright (c) Drew Heavner 2015. All rights reserved.
 */

package com.r0adkll.hackathon.api;

import android.content.Context;


import com.r0adkll.hackathon.R;

import retrofit.RetrofitError;
import timber.log.Timber;

/**
 * Created by r0adkll on 1/2/15.
 */
public class ErrorHandler implements retrofit.ErrorHandler {

    private Context mCtx;



    /**
     * Constructor
     */
    public ErrorHandler(Context ctx){
        mCtx = ctx;
    }

    @Override
    public Throwable handleError(RetrofitError cause) {
        String errorDescription;

        if (cause.getKind() == RetrofitError.Kind.NETWORK) {
            errorDescription = mCtx.getString(R.string.error_network);
        } else {
            if (cause.getResponse() == null) {
                errorDescription = mCtx.getString(R.string.error_no_response);
            } else {
                try {
                    errorDescription = (String) cause.getBodyAs(String.class);
                }catch (Exception ex2){

                    try {
                        errorDescription = mCtx.getString(R.string.error_network_http_error, cause.getResponse().getStatus());
                    } catch (Exception ex3) {
                        Timber.e("handleError: " + ex2.getLocalizedMessage());
                        errorDescription = mCtx.getString(R.string.error_unknown);
                    }

                }
            }
        }

        return new Exception(errorDescription, cause);
    }
}
