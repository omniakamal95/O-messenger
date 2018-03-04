package com.example.noso.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noso.myapplication.services.notificationServices;

public class LoginActivity extends AppCompatActivity {

    EditText name;
    EditText pw;
    Button btn;
    TextView NoAcc;
    Button Signbtn;
    PreferenceManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = findViewById(R.id.username);
        pw = findViewById(R.id.pw);
        btn = findViewById(R.id.Loginbtn);
        NoAcc = findViewById(R.id.NoAccount);
        Signbtn = findViewById(R.id.SignUp);
        session = new PreferenceManager(getApplicationContext());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = name.getText().toString();
                String Password = pw.getText().toString();
                if (Username.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter the username", Toast.LENGTH_LONG).show();

                }
                if (Password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter the Password", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Login Successful !", Toast.LENGTH_LONG).show();
                    session.LoginSession(Username, Password);
                    Intent intent = new Intent(getApplicationContext(), notificationServices.class);
                    getApplicationContext().startService(intent);
                    Intent i = new Intent(LoginActivity.this, Chats.class);
                    startActivity(i);
                }


            }
        });

        Signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, registration.class);
                startActivity(intent);
            }
        });

    }


}
