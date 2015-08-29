package com.r0adkll.hackathon.ui.screens.setup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.ftinc.kit.preferences.StringPreference;
import com.ftinc.kit.util.RxUtils;
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
public class SignupActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.input_firstname)
    EditText mInputFirstname;
    @Bind(R.id.input_lastname)
    EditText mInputLastname;
    @Bind(R.id.input_phone)
    EditText mInputPhone;
    @Bind(R.id.input_email)
    EditText mInputEmail;
    @Bind(R.id.input_password)
    EditText mInputPassword;
    @Bind(R.id.loading)
    ProgressBar mLoading;

    @Inject @Token
    StringPreference mToken;

    @Inject
    ApiService mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        getAppBar().setNavigationOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showLoading(){
        mLoading.setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        mLoading.setVisibility(View.GONE);
    }

    @OnClick(R.id.sign_up)
    void onSignUpClicked(){

        // Gather Inputs
        String first = mInputFirstname.getText().toString().trim();
        String last = mInputLastname.getText().toString().trim();
        String phone = mInputPhone.getText().toString().trim();
        String email = mInputEmail.getText().toString().trim();
        String pswd = mInputPassword.getText().toString().trim();

        showLoading();
        AppObservable.bindActivity(this, mApi.signup(email, pswd, String.format("%s %s", first, last), phone))
                .compose(RxUtils.applyIOSchedulers())
                .subscribe(user -> {
                    hideLoading();
                    mToken.set(user.token);

                    Intent main = new Intent(SignupActivity.this, AppActivity.class);
                    main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(main);
                    finish();
                }, throwable -> {
                    hideLoading();
                    Snackbar.make(mInputFirstname, throwable.getLocalizedMessage(), Snackbar.LENGTH_SHORT).show();
                });


    }

    @Override
    protected void setupComponent(AppComponent appGraph) {
        appGraph.inject(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
