package com.example.noso.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noso.myapplication.Interfaces.UsersClient;
import com.example.noso.myapplication.beans.Users;
import com.example.noso.myapplication.services.notificationServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText mail, pw;
    TextInputLayout mailTIL, pwTIL;
    boolean mailGood, pwGood;
    Button btn, signUpBtn;
    PreferenceManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mailGood = pwGood = false;

        mailTIL = findViewById(R.id.email_TIL);
        pwTIL = findViewById(R.id.pw_TIL);
        mail = findViewById(R.id.username);
        pw = findViewById(R.id.pw);
        btn = findViewById(R.id.Loginbtn);
        signUpBtn = findViewById(R.id.SignUp);
        session = new PreferenceManager(getApplicationContext());

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

        pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = pw.getText().toString();
                if (password.isEmpty()) {
                    pwGood = false;
                    pwTIL.setErrorEnabled(false);
                } else if (password.length() < 8) {
                    pwTIL.setErrorEnabled(true);
                    pwTIL.setError("Password too short!");
                    pwGood = false;
                } else {
                    pwTIL.setErrorEnabled(false);
                    pwGood = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Username = mail.getText().toString();
                final String Password = pw.getText().toString();
                if (mailGood && pwGood) {

                    Intent intent = new Intent(getApplicationContext(), notificationServices.class);
                    getApplicationContext().startService(intent);
                    Users users = new Users();
                    users.setEmail(Username);
                    users.setPassword(Password);
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl("http://10.0.2.2:3000/")
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit = builder.build();
                    UsersClient client = retrofit.create(UsersClient.class);
                    Call<Users> call = client.login(users);
                    Log.d("homie", "onClick: " + call.toString());
                    call.enqueue(new Callback<Users>() {
                        @Override
                        public void onResponse(Call<Users> call, Response<Users> response) {
                            Users users = response.body();
                            String xAuth = response.headers().get("x-auth");
                            Log.d("homie", "onResponse: " + (users != null ? users.getId() : null));
                            if (users != null) {
                                //                        Log.d("homie", "onResponse: "+call);
                                session.LoginSession(Username, Password, xAuth);
                                Toast.makeText(getApplicationContext(), "Login Successful !", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(LoginActivity.this, Chats.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Please check your credentials", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Users> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Sorry, something went wrong!", Toast.LENGTH_LONG).show();
                            Log.e("homie", "onFailure: ", t);
                        }

                    });
                }
            }
        });


        signUpBtn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}
