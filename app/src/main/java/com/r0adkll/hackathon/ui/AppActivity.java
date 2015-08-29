package com.r0adkll.hackathon.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ftinc.kit.preferences.StringPreference;
import com.r0adkll.hackathon.App;
import com.r0adkll.hackathon.ui.screens.setup.LoginActivity;
import com.r0adkll.hackathon.util.qualifiers.Token;

import javax.inject.Inject;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.ui
 * Created by drew.heavner on 8/28/15.
 */
public class AppActivity extends Activity {

    @Inject @Token
    StringPreference mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).component().inject(this);

        // 3...2...1...FIGHT!!!!
        if(mToken.isSet()){


        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        finish();
    }
}
