package com.r0adkll.hackathon;

import com.r0adkll.hackathon.api.ApiModule;
import com.r0adkll.hackathon.data.DataModule;
import com.r0adkll.hackathon.ui.AppActivity;
import com.r0adkll.hackathon.ui.UiModule;
import com.r0adkll.hackathon.ui.screens.detail.DetailActivity;
import com.r0adkll.hackathon.ui.screens.home.FindPetFragment;
import com.r0adkll.hackathon.ui.screens.home.HomeActivity;
import com.r0adkll.hackathon.ui.screens.home.MyScheduleFragment;
import com.r0adkll.hackathon.ui.screens.schedule.MyScheduleActivity;
import com.r0adkll.hackathon.ui.screens.schedule.ScheduleActivity;
import com.r0adkll.hackathon.ui.screens.setup.LoginActivity;
import com.r0adkll.hackathon.ui.screens.setup.SignupActivity;

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
        UiModule.class,
        ApiModule.class,
        InfoModule.class
})
public interface AppComponent {

    void inject(App app);
    void inject(AppActivity activity);
    void inject(LoginActivity activity);
    void inject(SignupActivity signupActivity);
    void inject(HomeActivity homeActivity);
    void inject(DetailActivity detailActivity);
    void inject(ScheduleActivity scheduleActivity);
    void inject(MyScheduleActivity myScheduleActivity);
    void inject(FindPetFragment findPetFragment);

    void inject(MyScheduleFragment myScheduleFragment);
}
