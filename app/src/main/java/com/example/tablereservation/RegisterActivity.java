package com.example.tablereservation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputEditText username;
    private TextInputEditText email;
    private TextInputEditText password;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.remail);
        password=findViewById(R.id.rpassword);
        username=findViewById(R.id.username);
        progressDialog=new ProgressDialog(this,0);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
    }

    public void rregister(View view) {
        String un=Objects.requireNonNull(username.getText().toString());
        String re= Objects.requireNonNull(email.getText()).toString();
        String rp= Objects.requireNonNull(password.getText()).toString();
        progressDialog.show();
        if (re.isEmpty()||rp.isEmpty()){
            Toast.makeText(this,"Please enter details",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
        else {
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(re,rp).addOnCompleteListener(this, task -> {
                if(task.isSuccessful()){
                    FirebaseUser user=mAuth.getCurrentUser();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    Toast.makeText(RegisterActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(RegisterActivity.this,"Authentication failed",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }

    }
}