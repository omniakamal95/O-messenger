package com.example.noso.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        final EditText fname = findViewById(R.id.FirstName);
        final EditText lname = findViewById(R.id.LastName);
        final EditText uname = findViewById(R.id.UserName);
        final EditText mail  = findViewById(R.id.mail);
        final EditText pass  = findViewById(R.id.Password);
        final EditText rpass = findViewById(R.id.Re_password);
        Button   Regbtn= findViewById(R.id.register);

        Regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String First , Last , Username , Email , Password , RePassword;
                First = fname.getText().toString();
                Last = lname.getText().toString();
                Username = uname.getText().toString();
                Email = mail.getText().toString();
                Password = pass.getText().toString();
                RePassword = rpass.getText().toString();
                if(First.isEmpty()||Last.isEmpty()||Username.isEmpty()||Email.isEmpty()||
                        Password.isEmpty()||RePassword.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter the missing information",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Done ;)", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
