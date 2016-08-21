package com.example.ashukaushik.fun;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intentMainActivity = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intentMainActivity);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
