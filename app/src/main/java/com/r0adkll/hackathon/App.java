package com.r0adkll.hackathon;

import android.app.Application;
import android.app.Service;
import android.content.Context;

import com.r0adkll.hackathon.api.ApiModule;
import com.r0adkll.hackathon.data.AppProvider;
import com.r0adkll.hackathon.data.DataModule;
import com.r0adkll.hackathon.ui.UiModule;

import hugo.weaving.DebugLog;
import timber.log.Timber;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon
 * Created by drew.heavner on 8/28/15.
 */
public class App extends Application{

    /***********************************************************************************************
     *
     * Variables
     *
     */

    private AppProvider mProvider;
    private AppComponent mComponent;

    /***********************************************************************************************
     *
     * Lifecycle Methods
     *
     */

    @Override
    public void onCreate() {
        super.onCreate();

        // Setup Timber
        Timber.plant(new Timber.DebugTree());

        // Build dagger graph
        buildDaggerGraph();

        // Setup Ollie
//        mProvider = new AppProvider();
//        mProvider.onCreate();

    }

    /***********************************************************************************************
     *
     * Dagger Methods
     *
     */

    /**
     * Build and inject the object graph for Dagger2
     */
    @DebugLog
    private void buildDaggerGraph(){
        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataModule(new DataModule())
                .uiModule(new UiModule())
                .apiModule(new ApiModule())
                .infoModule(new InfoModule())
                .build();
        mComponent.inject(this);
    }

    /**
     * Get the object graph component for DI
     *
     * @return      the app graphs
     */
    public AppComponent component(){
        return mComponent;
    }

    /**
     * Cast an application context to the Apps 'Application'
     * class to gain access to the injection graph
     *
     * @param ctx       the context to cast
     * @return          the casted Application reference
     */
    public static App get(Context ctx){
        return (App) ctx.getApplicationContext();
    }

    /**
     * Cast an application context to the Apps 'Application'
     * class to gain access to the injection graph
     *
     * @param srv       the service to cast
     * @return          the casted Application reference
     */
    public static App get(Service srv){
        return (App) srv.getApplication();
    }

}
