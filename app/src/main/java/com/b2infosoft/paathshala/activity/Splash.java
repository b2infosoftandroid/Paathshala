package com.b2infosoft.paathshala.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.credential.Active;


public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Automatic session tracking
        //Branch.getAutoInstance(getApplication());
        setContentView(R.layout.activity_splash);

        //findViewById(R.id.imageView).startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(0000);
                    Active active= Active.getInstance(getApplicationContext());
                    if(active.isLogin()) {
                        startActivity(new Intent(Splash.this, MainActivity.class));
                    }else{
                        startActivity(new Intent(Splash.this, LoginActivity.class));
                    }
                    finish();
                } catch (InterruptedException e) {

                }
            }
        }).start();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }
}
