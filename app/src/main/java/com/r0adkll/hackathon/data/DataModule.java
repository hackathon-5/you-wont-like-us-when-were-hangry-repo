package com.r0adkll.hackathon.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    /***********************************************************************************************
     *
     * Constants
     *
     */


    /***********************************************************************************************
     *
     * Preferences
     *
     */

    @Provides @Singleton
    SharedPreferences getDefaultPreferences(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    /***********************************************************************************************
     *
     * Typed Preferences
     *
     */


}
