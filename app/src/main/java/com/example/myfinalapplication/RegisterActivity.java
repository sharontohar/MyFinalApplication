package com.example.myfinalapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAssociation,r_registerBtn;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnAssociation=findViewById(R.id.btnAssociation);
        r_registerBtn=findViewById(R.id.r_registerBtn);
        r_registerBtn.setOnClickListener(this);
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
    }

    @Override
    public void onClick(View view) {
        if(code.equals("2222")|| code==null){
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        }
        else{
            Toast.makeText(RegisterActivity.this, "No", Toast.LENGTH_SHORT).show();
        }
    }
}