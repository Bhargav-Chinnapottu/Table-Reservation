package com.example.tablereservation;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private SensorManager sensorManager;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        progressDialog = new ProgressDialog(this);
    }

    public void contactus(View view) {
        progressDialog.show();
        startActivity(new Intent(this, ContactActivity.class));
        progressDialog.dismiss();
    }
    public void updateuser(View view) {
        progressDialog.show();
        startActivity(new Intent(this,UpdateActivity.class));
        progressDialog.dismiss();
    }



    public void aboutus(View view) {
        progressDialog.show();
        startActivity(new Intent(this, AboutActivity.class));
        progressDialog.dismiss();

    }

    public void logout(View view) {
        progressDialog.show();
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void tableReservation(View view) {
        progressDialog.show();
        startActivity(new Intent(this,TableActivity.class));
        progressDialog.dismiss();
    }


    public void GPS(View view) {
        startActivity(new Intent(this,GPSActivity.class));
    }
}