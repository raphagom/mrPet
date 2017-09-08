package com.mrpet.mrpet;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#FFFFFF"));


        Thread splash = new  Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent mrPetIntent = new Intent(getApplicationContext(), MrPET.class);
                    startActivity(mrPetIntent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        splash.start();
    }
}
