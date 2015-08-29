package com.r0adkll.hackathon.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ftinc.kit.preferences.StringPreference;
import com.r0adkll.hackathon.util.qualifiers.Token;

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

    @Provides @Singleton @Token
    StringPreference provideAccessTokenPreference(SharedPreferences prefs){
        return new StringPreference(prefs, "pref_access_token");
    }


}
