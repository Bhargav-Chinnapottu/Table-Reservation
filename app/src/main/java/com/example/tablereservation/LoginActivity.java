package com.example.tablereservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputEditText email;
    private TextInputEditText password;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressDialog=new ProgressDialog(this);
        notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
    }
    public void login(View view) {
        String e=email.getText().toString();
        String p=password.getText().toString();
        progressDialog.show();
        if(e.isEmpty()||p.isEmpty()){
            Toast.makeText(this,"Please fill all the details",Toast.LENGTH_SHORT).show();
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                NotificationChannel channel=new NotificationChannel("channel id","android channel",NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"channel id");
            builder.setContentTitle("Restaurant");

            builder.setContentText("Enter Valid Details");

            builder.setSmallIcon(R.drawable.ic_launcher_background);

            builder.setAutoCancel(true);

            notificationManager.notify(1,builder.build());
        }

        else{
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
                        FirebaseUser user=mAuth.getCurrentUser();
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                            NotificationChannel channel=new NotificationChannel("channel id","android channel",NotificationManager.IMPORTANCE_DEFAULT);
                            notificationManager.createNotificationChannel(channel);
                        }
                        NotificationCompat.Builder builder=new NotificationCompat.Builder(LoginActivity.this,"channel id");
                        builder.setContentTitle("Restaurant");

                        builder.setContentText("Login Success");

                        builder.setSmallIcon(R.drawable.restaurant);

                        builder.setAutoCancel(true);

                        notificationManager.notify(1,builder.build());
                        progressDialog.dismiss();
                    }
                    else{
                        progressDialog.show();
                        Toast.makeText(LoginActivity.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                            NotificationChannel channel=new NotificationChannel("channel id","android channel",NotificationManager.IMPORTANCE_DEFAULT);
                            notificationManager.createNotificationChannel(channel);
                        }
                        NotificationCompat.Builder builder=new NotificationCompat.Builder(LoginActivity.this,"channel id");
                        builder.setContentTitle("Restaurant");

                        builder.setContentText("Login Failed");

                        builder.setSmallIcon(R.drawable.restaurant);

                        builder.setAutoCancel(true);

                        notificationManager.notify(1,builder.build());
                        progressDialog.dismiss();
                    }
                }
            });
            progressBar.setVisibility(View.GONE);
        }

    }
    public void forgot(View view){
        String e=email.getText().toString();
        progressDialog.show();
        if(e.isEmpty()){
            Toast.makeText(this,"Email field should not be empty",Toast.LENGTH_SHORT).show();
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                NotificationChannel channel=new NotificationChannel("channel id","android channel",NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"channel id");
            builder.setContentTitle("Restaurant");

            builder.setContentText("Enter Valid Details");

            builder.setSmallIcon(R.drawable.restaurant);

            builder.setAutoCancel(true);

            notificationManager.notify(1,builder.build());
            progressDialog.dismiss();

        }
        else{
            mAuth.sendPasswordResetEmail(e).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Reset email sent",Toast.LENGTH_SHORT).show();
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                            NotificationChannel channel=new NotificationChannel("channel id","android channel",NotificationManager.IMPORTANCE_DEFAULT);
                            notificationManager.createNotificationChannel(channel);
                        }
                        NotificationCompat.Builder builder=new NotificationCompat.Builder(LoginActivity.this,"channel id");
                        builder.setContentTitle("Restaurant");

                        builder.setContentText("Reset email sent");

                        builder.setSmallIcon(R.drawable.restaurant);

                        builder.setAutoCancel(true);

                        notificationManager.notify(1,builder.build());
                    }
                    else {
                        Toast.makeText(LoginActivity.this,"Enter a valid email ",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                            NotificationChannel channel=new NotificationChannel("channel id","android channel",NotificationManager.IMPORTANCE_DEFAULT);
                            notificationManager.createNotificationChannel(channel);
                        }
                        NotificationCompat.Builder builder=new NotificationCompat.Builder(LoginActivity.this,"channel id");
                        builder.setContentTitle("Restaurant");

                        builder.setContentText("Enter a valid email");

                        builder.setSmallIcon(R.drawable.restaurant);

                        builder.setAutoCancel(true);

                        notificationManager.notify(1,builder.build());
                    }
                }
            });
        }

    }
}