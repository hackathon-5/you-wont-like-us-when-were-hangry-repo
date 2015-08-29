package com.r0adkll.hackathon.ui.screens.setup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ftinc.kit.preferences.StringPreference;
import com.ftinc.kit.util.RxUtils;
import com.ftinc.kit.util.Utils;
import com.r0adkll.hackathon.AppComponent;
import com.r0adkll.hackathon.R;
import com.r0adkll.hackathon.api.ApiService;
import com.r0adkll.hackathon.data.model.User;
import com.r0adkll.hackathon.ui.AppActivity;
import com.r0adkll.hackathon.ui.model.BaseActivity;
import com.r0adkll.hackathon.util.qualifiers.Token;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.app.AppObservable;
import rx.functions.Action1;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.ui.screens.setup
 * Created by drew.heavner on 8/28/15.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.email_input)
    EditText mEmailInput;
    @Bind(R.id.password_input)
    EditText mPasswordInput;
    @Bind(R.id.sign_in)
    Button mSignIn;
    @Bind(R.id.sign_up)
    TextView mSignUp;
    @Bind(R.id.loading)
    ProgressBar mLoading;

    @Inject @Token
    StringPreference mTokenPref;

    @Inject
    ApiService mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.sign_in)
    void onSignInClicked(){

        String email = mEmailInput.getText().toString();
        String pswd = mPasswordInput.getText().toString();

        if(TextUtils.isEmpty(email) || Utils.isValidEmail(email)){
            Snackbar.make(mEmailInput, "Please enter a valid email address", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(pswd)){
            Snackbar.make(mEmailInput, "Please enter a password", Snackbar.LENGTH_SHORT).show();
            return;
        }

        // Grab input, request login
        showLoading();
        AppObservable.bindActivity(this, mApi.login(email, pswd))
                .compose(RxUtils.applyIOSchedulers())
                .subscribe(user -> {
                    hideLoading();
                    mTokenPref.set(user.token);

                    Intent main = new Intent(LoginActivity.this, AppActivity.class);
                    main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(main);
                    finish();
                }, throwable -> {
                    hideLoading();
                    Snackbar.make(mEmailInput, throwable.getLocalizedMessage(), Snackbar.LENGTH_SHORT).show();
                });

    }

    @OnClick(R.id.sign_up)
    void onSignUpClicked(){

        // Show the signup flow
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);


    }

    private void showLoading(){
        mLoading.setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        mLoading.setVisibility(View.GONE);
    }

    @Override
    protected void setupComponent(AppComponent appGraph) {
        appGraph.inject(this);
    }
}
