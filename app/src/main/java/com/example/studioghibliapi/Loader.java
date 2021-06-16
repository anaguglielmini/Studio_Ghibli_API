package com.example.studioghibliapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;

public class Loader extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);

    getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(getBaseContext(), Local.class));
            finish();
        }
    },1);
}
}