package com.example.myfinalapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    EditText email_Login,pass_Login;
    Button LoginBtn;
    String code,user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent2 = getIntent();
        code = intent2.getStringExtra("code");
        user = intent2.getStringExtra("email");

        auth = FirebaseAuth.getInstance();
        email_Login=findViewById(R.id.email_Login);
        pass_Login=findViewById(R.id.pass_Login);
        LoginBtn=findViewById(R.id.LoginBtn);
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_Login.getText().toString();
                String pass = pass_Login.getText().toString();

                if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    if(!pass.isEmpty()){
                        auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(LoginActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, donationsBoard.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this,"Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        pass_Login.setError("Password can not be empty");
                    }
                }else if(email.isEmpty()){
                    email_Login.setError("Email can not be empty");
                } else {
                    email_Login.setError("Please enter valid email");
                }
            }
        });

    }
}