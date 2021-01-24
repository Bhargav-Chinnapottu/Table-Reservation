package com.example.tablereservation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor mSensor;
    private ProgressBar progressBar;
    private ProgressDialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        dialog = new ProgressDialog(this);
    }

    public void onLogin(View view) {
        progressBar.setVisibility(View.VISIBLE);
        dialog.show();
        Toast.makeText(this, "Login Account", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        dialog.dismiss();
        progressBar.setVisibility(View.GONE);
    }

    public void register(View view) {
        progressBar.setVisibility(View.VISIBLE);
        dialog.show();
        Toast.makeText(this, "Create Account", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, RegisterActivity.class));
        dialog.dismiss();
        progressBar.setVisibility(View.GONE);

    }
}