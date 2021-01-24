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

public class TableActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference().child("Table");
    Button reserveTable;
    EditText people, name, date, time;
    Table table;
    long maxid = 0;
    NotificationManager notificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        name = (EditText) findViewById(R.id.name);
        people = (EditText) findViewById(R.id.people);
        date = (EditText) findViewById(R.id.date);
        time = (EditText) findViewById(R.id.time);
        reserveTable = (Button) findViewById(R.id.btnreserve);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        table = new Table();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxid = snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("channel id", "android channel", NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);
                }
                NotificationCompat.Builder builder = new NotificationCompat.Builder(TableActivity.this, "channel id");
                builder.setSmallIcon(R.drawable.restaurant);
                builder.setAutoCancel(true);
                builder.setContentTitle("TableReservation");
                builder.setContentText("Something went wrong");
                notificationManager.notify(1, builder.build());

                Toast.makeText(TableActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

        reserveTable.setOnClickListener(v -> {
            String p = people.getText().toString().trim();
            String d = date.getText().toString().trim();
            String t = time.getText().toString().trim();
            String n = name.getText().toString().trim();
            if (p.isEmpty() || d.isEmpty() || t.isEmpty() || n.isEmpty()) {
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("channel id", "android channel", NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);
                }
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel id");
                builder.setSmallIcon(R.drawable.restaurant);
                builder.setAutoCancel(true);
                builder.setContentTitle("TableReservation");
                builder.setContentText("Enter valid details");
                notificationManager.notify(1, builder.build());

                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();

            } else {
                table.setName(n);
                table.setDate(d);
                table.setPeople(p);
                table.setTime(t);
                myRef.child(String.valueOf(maxid + 1)).setValue(table);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("channel id", "android channel", NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);
                }
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel id");
                builder.setSmallIcon(R.drawable.restaurant);
                builder.setAutoCancel(true);
                builder.setContentTitle("TableReservation");
                builder.setContentText("Table successfully booked");
                notificationManager.notify(1, builder.build());

                Toast.makeText(this, "Table successfully booked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, DashboardActivity.class));

            }

        });
    }


}