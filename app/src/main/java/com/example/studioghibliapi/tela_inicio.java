package com.example.studioghibliapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
//importando para sensor de luz
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.ImageView;

public class tela_inicio extends AppCompatActivity implements SensorEventListener{
//definindo nomes
    SensorManager sensorManager;
    Sensor sensor;
    ImageView totoro;
    ImageView catbus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicio);

        getSupportActionBar().hide();

        //identificando os gifs
        totoro = findViewById(R.id.totoro_andando);
        catbus = findViewById(R.id.catbus);

        //aplicando o sensor
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), Local.class));
                finish();
            }
        },5000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener((SensorEventListener) this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    //identificando se há luz ou não
    @Override
    public void onSensorChanged(SensorEvent event) {
        //condição para se o hambiente estiver CLARO aparecer o totoro andando
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            if (event.values[0] == 0) {
                catbus.setVisibility(View.VISIBLE);
                totoro.setVisibility(View.GONE);
                //condição para se o hambiente estiver ESCURO colocar o gif do gatobus
            } else {
                catbus.setVisibility(View.GONE);
                totoro.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}