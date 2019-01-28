package com.tribalscale.felipepaiva.arway2.splash;

import android.content.Intent;
import android.os.Handler;

import com.tribalscale.felipepaiva.arway2.chat.ChatActivity;

import androidx.appcompat.app.AppCompatActivity;

class SplashPresenter implements SplashContract.presenter{

    @Override
    public void prepare(AppCompatActivity appCompatActivity) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            appCompatActivity.startActivity(new Intent(appCompatActivity, ChatActivity.class));
            appCompatActivity.finish();
        }, 2000);
    }
}
