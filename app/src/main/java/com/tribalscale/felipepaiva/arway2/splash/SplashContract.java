package com.tribalscale.felipepaiva.arway2.splash;

import androidx.appcompat.app.AppCompatActivity;

public interface SplashContract {

    interface view {
    }

    interface presenter{
        void prepare(AppCompatActivity appCompatActivity);
    }
}
