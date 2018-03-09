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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    Button loginBtn, signUpBtn;
    PreferenceManager session;
    LinearLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Animation shakingAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_animation);
        mailGood = pwGood = false;

        mailTIL = findViewById(R.id.email_TIL);
        pwTIL = findViewById(R.id.pw_TIL);
        mail = findViewById(R.id.username);
        pw = findViewById(R.id.pw);
        loginBtn = findViewById(R.id.Loginbtn);
        signUpBtn = findViewById(R.id.SignUp);
        parent = findViewById(R.id.login_parent);
        session = new PreferenceManager(getApplicationContext());

        mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateEmail();
            }
        });

        pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validatePassword();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateEmail();
                validatePassword();
                if (mailGood && pwGood) {
                    final String Username = mail.getText().toString();
                    final String Password = pw.getText().toString();
                    Users users = new Users();
                    users.setEmail(Username);
                    users.setPassword(Password);
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl("https://thawing-fortress-83069.herokuapp.com/")
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit = builder.build();
                    UsersClient client = retrofit.create(UsersClient.class);
                    Call<Users> call = client.login(users);
                    Log.d("homie", "onClick: " + call.toString());
                    disableControls();
                    call.enqueue(new Callback<Users>() {
                        @Override
                        public void onResponse(Call<Users> call, Response<Users> response) {
                            enableControls();
                            Users users = response.body();
                            String xAuth = response.headers().get("x-auth");
                            Log.d("homie", "onResponse: " + (users != null ? users.getId() : null));
                            if (users != null) {
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
                            enableControls();
                            Toast.makeText(LoginActivity.this, "Sorry, something went wrong!", Toast.LENGTH_LONG).show();
                            Log.e("homie", "onFailure: ", t);
                        }
                    });
                }else{
                    parent.startAnimation(shakingAnimation);
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

    private void disableControls() {
        loginBtn.setEnabled(false);
        pw.setEnabled(false);
        mail.setEnabled(false);
    }

    private void enableControls(){
        loginBtn.setEnabled(true);
        pw.setEnabled(true);
        mail.setEnabled(true);
    }

    private void validatePassword() {
        String password = pw.getText().toString();
        if (password.isEmpty()) {
            pwGood = false;
            pwTIL.setErrorEnabled(true);
            pwTIL.setError("A password is required");

        } else if (password.length() < 8) {
            pwTIL.setErrorEnabled(true);
            pwTIL.setError("Password too short!");
            pwGood = false;
        } else {
            pwTIL.setErrorEnabled(false);
            pwGood = true;
        }
    }

    private void validateEmail() {
        String emailIn = mail.getText().toString();
        if (emailIn.isEmpty()) {
            mailGood = false;
            mailTIL.setErrorEnabled(true);
            mailTIL.setError("An email is required");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailIn).matches()) {
            mailTIL.setErrorEnabled(true);
            mailTIL.setError("Wrong email format!");
            mailGood = false;
        } else {
            mailTIL.setErrorEnabled(false);
            mailGood = true;
        }
    }


}
