package com.techbuzz.katraj.drunkpersondetection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends Activity {
    ImageView imageview;
    private static SplashTimer timer;
    private final static int TIMER_INTERVAL = 4000; // 2 sec
    private boolean activityStarted;
    private boolean exitAds = false;
    private boolean mWasGetContentIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mWasGetContentIntent = intent.getAction().equals(
                Intent.ACTION_GET_CONTENT);
        setContentView(R.layout.activity_splash_screen);

    imageview=(ImageView)findViewById(R.id.logo);

        StartAnimations();

        timer = new SplashTimer();
        timer.sendEmptyMessageDelayed(1, TIMER_INTERVAL);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    final class SplashTimer extends Handler {
        @Override
        public void handleMessage(Message msg) {
            post(new Runnable() {

                public void run() {
                    timer = null;

                    startHomePageActivity();
                }
            });
        }
    }

    private void startHomePageActivity() {

        if (activityStarted) {
            return;
        }
        activityStarted = true;

        SplashScreenActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                finish();
            }
        });


    }
    private void StartAnimations()
    {
        Animation animFadeOut = AnimationUtils.loadAnimation(this, R.anim.alpha);
        animFadeOut.reset();
        imageview.clearAnimation();
        imageview.startAnimation(animFadeOut);

    }
}
