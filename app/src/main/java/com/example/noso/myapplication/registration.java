package com.example.noso.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noso.myapplication.beans.User;

public class registration extends AppCompatActivity implements View.OnClickListener {

    EditText fName, uName, mail, pass, rPass;
    TextInputLayout mailTIL, pwTIL, rePwTIL;
    Button Regbtn;
    boolean mailGood, passGood, rPassGood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        mailGood = passGood = rPassGood = false;

        fName = findViewById(R.id.FirstName);
        uName = findViewById(R.id.UserName);
        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.Password);
        pwTIL = findViewById(R.id.password_til);
        rPass = findViewById(R.id.Re_password);
        Regbtn = findViewById(R.id.register);
        mailTIL = findViewById(R.id.mail_til);
        rePwTIL = findViewById(R.id.rePW_til);

        mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String emailIn = mail.getText().toString();
                if (emailIn.isEmpty()) {
                    mailGood = false;
                    mailTIL.setErrorEnabled(false);
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailIn).matches()) {
                    mailTIL.setErrorEnabled(true);
                    mailTIL.setError("Wrong email format!");
                    mailGood = false;
                } else {
                    mailTIL.setErrorEnabled(false);
                    mailGood = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = pass.getText().toString();
                if (password.isEmpty()) {
                    passGood = false;
                    pwTIL.setErrorEnabled(false);
                } else if (password.length() < 8) {
                    pwTIL.setErrorEnabled(true);
                    pwTIL.setError("Password too short!");
                    passGood = false;
                } else {
                    pwTIL.setErrorEnabled(false);
                    passGood = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (rPass.getText().toString().isEmpty()) {
                    rPassGood = false;
                    rePwTIL.setErrorEnabled(false);
                } else if (!rPass.getText().toString().equals(pass.getText().toString())) {
                    rePwTIL.setErrorEnabled(true);
                    rePwTIL.setError("password does not match!");
                    rPassGood = false;
                } else {
                    rePwTIL.setErrorEnabled(false);
                    rPassGood = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Regbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register) {
            String First, Username, Email, Password;
            First = fName.getText().toString();
            Username = uName.getText().toString();
            Email = mail.getText().toString();
            Password = pass.getText().toString();
            User user = new User(First, Username, Email, Password);

            if (mailGood && passGood && rPassGood && !First.isEmpty() && !Username.isEmpty()) {
                Toast.makeText(registration.this, "Account Created", Toast.LENGTH_SHORT).show();
                //TODO: @POST signup

                Intent i = new Intent(registration.this, WelcomeActivity.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "check the fields", Toast.LENGTH_LONG).show();
            }


        }
    }
}


