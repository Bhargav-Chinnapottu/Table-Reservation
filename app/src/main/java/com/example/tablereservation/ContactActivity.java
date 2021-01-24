package com.example.tablereservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class ContactActivity extends AppCompatActivity {
    NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    public void emailUs(View view) {
        Intent intent=new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: bhargavchinnapotu@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"subject");
        intent.putExtra(Intent.EXTRA_TEXT,"body");
        startActivity(Intent.createChooser(intent,"Email"));
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel id","android channel",NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"channel id");
        builder.setContentTitle("Restaurant");
        builder.setAutoCancel(true);
        builder.setContentText("Send Email");
        builder.setSmallIcon(R.drawable.restaurant);
        manager.notify(1,builder.build());

    }
    public void callUs(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:9490491773"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("channel id","channel id",NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"channel id");
        builder.setSmallIcon(R.drawable.restaurant);
        builder.setContentTitle("Restaurant");
        builder.setContentText("Calling..");
        builder.setAutoCancel(true);
        manager.notify(1,builder.build());
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,DashboardActivity.class);
        startActivity(intent);
    }
}