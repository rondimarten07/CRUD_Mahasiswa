package com.example.crudmahasiswa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivitySplashScreen extends AppCompatActivity {

    private String TAG = ActivitySplashScreen.class.getSimpleName();
    private int TIME_OUT = 3000;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashcreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ActivitySplashScreen.this, SigninActivity.class);
                startActivity(intent);
                finish();

            }
        },TIME_OUT);
    }

}