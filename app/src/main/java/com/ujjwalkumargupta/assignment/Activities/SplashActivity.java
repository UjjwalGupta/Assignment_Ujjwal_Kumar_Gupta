package com.ujjwalkumargupta.assignment.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.ujjwalkumargupta.assignment.R;

public class SplashActivity extends AppCompatActivity {
    private String TAG = "SplashActivity";
    private ProgressBar progressBar;
    private RelativeLayout rlParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initialize();
        action();
    }

    private void initialize() {
        progressBar = (ProgressBar) findViewById(R.id.pd_splash);
        rlParent = (RelativeLayout) findViewById(R.id.rl_parent_splash);
    }

    private void action() {
        progressBar.setVisibility(View.VISIBLE);
        rlParent.postDelayed(runnable, 3000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}