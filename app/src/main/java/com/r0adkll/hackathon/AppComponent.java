package com.r0adkll.hackathon;

import com.r0adkll.hackathon.data.DataModule;
import com.r0adkll.hackathon.ui.UiModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon
 * Created by drew.heavner on 8/28/15.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        DataModule.class,
        UiModule.class
})
public interface AppComponent {

    void inject(App app);

}
