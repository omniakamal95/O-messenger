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
                Username = uname.getText().toString();
                Email = mail.getText().toString();
                Password = pass.getText().toString();
                RePassword = rpass.getText().toString();
                User user = new User(
                        First,Username,Email,Password
                );
                if (First.length()!=0&&Username.length()!=0&&Password.length()!=0&&RePassword.length()!=0)
                {
                    if (Password.equals(RePassword))
                    {

                       // Create new user [sendNetworkRequest()]
                        Toast.makeText(registration.this, "Account Created", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        Toast.makeText(registration.this, "Make sure your passwords are identical", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(registration.this, "Check the missing fields!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}


