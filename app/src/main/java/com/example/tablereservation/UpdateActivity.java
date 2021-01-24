package com.example.tablereservation;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference().child("User");
    EditText phone,username;
    Button updateUser;
    User user;
    long maxid=0;
    NotificationManager notificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        phone = findViewById(R.id.phone);
        username = findViewById(R.id.username);
        updateUser = (Button) findViewById(R.id.update);
        notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        user= new User();

        updateUser.setOnClickListener(v -> {
            String un = username.getText().toString().trim();
            String ph = phone.getText().toString().trim();
            if(un.isEmpty()||ph.isEmpty()){
                Toast.makeText(this,"Please enter valid details",Toast.LENGTH_SHORT).show();
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                    NotificationChannel channel=new NotificationChannel("channel id","android channel",NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);
                }
                NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"channel id");
                builder.setContentTitle("TableReservation");
                builder.setContentText("Please enter valid details");
                builder.setSmallIcon(R.drawable.restaurant);
                builder.setAutoCancel(true);
                notificationManager.notify(1,builder.build());
            }
            else {
                user.setPhone(ph);
                user.setUsername(un);
                myRef.child(String.valueOf(maxid + 1)).setValue(user);
                Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("channel id", "android channel", NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);
                }
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel id");
                builder.setContentTitle("TableReservation");
                builder.setContentText("User updated successfully");
                builder.setSmallIcon(R.drawable.restaurant);
                builder.setAutoCancel(true);
                notificationManager.notify(1, builder.build());

                startActivity(new Intent(this, DashboardActivity.class));
            }
        });
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxid = snapshot.getChildrenCount();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                    NotificationChannel channel=new NotificationChannel("channel id","android channel",NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);
                }
                NotificationCompat.Builder builder=new NotificationCompat.Builder(UpdateActivity.this,"channel id");
                builder.setContentTitle("TableReservation");
                builder.setContentText("Something went wrong");
                builder.setSmallIcon(R.drawable.restaurant);
                builder.setAutoCancel(true);
                notificationManager.notify(1,builder.build());
            }
        });

    }
}
