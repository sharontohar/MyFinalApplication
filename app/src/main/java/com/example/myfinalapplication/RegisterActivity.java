package com.example.myfinalapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity{
    Button btnAssociation,r_registerBtn;
    private String code=null;
    private FirebaseAuth auth;
    EditText r_name,r_mobile,r_email,r_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        r_name=findViewById(R.id.r_name);
        r_mobile=findViewById(R.id.r_mobile);
        r_email=findViewById(R.id.r_email);
        r_pass=findViewById(R.id.r_pass);
        btnAssociation=findViewById(R.id.btnAssociation);
        r_registerBtn=findViewById(R.id.r_registerBtn);
        btnAssociation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder myDialog = new AlertDialog.Builder(RegisterActivity.this);
                myDialog.setTitle("Alert Title");
                final EditText codeInput = new EditText(RegisterActivity.this);
                codeInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                myDialog.setView(codeInput);

                myDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        code=codeInput.getText().toString();
                    }

                });
                myDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                myDialog.show();
            }
        });
        r_registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(code==null || code.equals("2222")){
                    String user = r_email.getText().toString().trim();
                    String pass = r_pass.getText().toString().trim();

                    if(user.isEmpty()){
                        r_email.setError("Email can not be empty");
                    }
                    if(pass.isEmpty()){
                        r_pass.setError("Password can not bt empty");
                    }
                    else{
                        auth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this,"Register successful", Toast.LENGTH_SHORT).show();
                                    if(code==null){
                                        startActivity(new Intent(RegisterActivity.this, donationsBoard.class));
                                    }
                                    else{
                                        startActivity(new Intent(RegisterActivity.this, DonationsBoAssociation.class));
                                    }
                                }
                                else{
                                    Toast.makeText(RegisterActivity.this,"Register failed" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this, "No", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
