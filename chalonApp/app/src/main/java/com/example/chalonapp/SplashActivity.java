package com.example.chalonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    Animation topAnim, bottonAim;
    ImageView logoUp, logoDown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottonAim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logoUp = findViewById(R.id.logoUp);
        logoDown = findViewById(R.id.logoDown);

        logoUp.setAnimation(topAnim);
        logoDown.setAnimation(bottonAim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                //Finish
                finish();

            }
        }, 2500);
    }
}