package com.tribalscale.felipepaiva.arway2.splash;

import android.os.Bundle;

import com.tribalscale.felipepaiva.arway2.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity implements SplashContract.view {

    private SplashContract.presenter splashPresenter = new SplashPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Just gonna left it here as a boilerplate for further user routing if need
        splashPresenter.prepare(this);
    }
}
