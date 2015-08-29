package com.r0adkll.hackathon.ui;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;
import android.view.inputmethod.InputMethodManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by r0adkll on 4/2/15.
 */
@Module
public class UiModule {

    @Provides @Singleton
    InputMethodManager provideIMM(Context ctx){
        return (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Provides @Singleton
    NotificationManagerCompat provideNotificationManagerCompat(Context ctx){
        return NotificationManagerCompat.from(ctx);
    }

    @Provides @Singleton
    NotificationManager provideNotificationManager(Context ctx){
        return (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
    }

}
