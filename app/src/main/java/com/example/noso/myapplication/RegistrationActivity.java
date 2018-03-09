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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "homie";
    EditText  uName, mail, pass, rPass;
    TextInputLayout mailTIL, pwTIL, rePwTIL,uNameTIL;
    Button Regbtn;
    boolean mailGood, passGood, rPassGood ,uNameGood;
    PreferenceManager session;
    LinearLayout parent;
    Animation shakingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        shakingAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_animation);
        mailGood = passGood = rPassGood = false;

        uName = findViewById(R.id.UserName);
        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.Password);
        rPass = findViewById(R.id.Re_password);
        pwTIL = findViewById(R.id.password_til);
        Regbtn = findViewById(R.id.register);
        mailTIL = findViewById(R.id.mail_til);
        rePwTIL = findViewById(R.id.rePW_til);
        uNameTIL=findViewById(R.id.username_TIL);
        parent=findViewById(R.id.reg_parent);
        session = new PreferenceManager(getApplicationContext());

        uName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validateUsername();
            }
        });
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

        pass.addTextChangedListener(new TextWatcher() {
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

        rPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateConfirmPassword();
            }
        });

        Regbtn.setOnClickListener(this);

    }

    private void validateConfirmPassword() {
        if (rPass.getText().toString().isEmpty()) {
            rPassGood = false;
            rePwTIL.setErrorEnabled(true);
            rePwTIL.setError("A password confirmation is required");
        } else if (!rPass.getText().toString().equals(pass.getText().toString())) {
            rePwTIL.setErrorEnabled(true);
            rePwTIL.setError("password does not match");
            rPassGood = false;
        } else {
            rePwTIL.setErrorEnabled(false);
            rPassGood = true;
        }
    }

    private void validatePassword() {
        String password = pass.getText().toString();
        if (password.isEmpty()) {
            passGood = false;
            pwTIL.setErrorEnabled(true);
            pwTIL.setError("A Password is required");
        } else if (password.length() < 8) {
            pwTIL.setErrorEnabled(true);
            pwTIL.setError("Password too short");
            passGood = false;
        } else {
            pwTIL.setErrorEnabled(false);
            passGood = true;
        }
    }

    private void validateEmail() {
        String emailIn = mail.getText().toString();
        if (emailIn.isEmpty()) {
            mailGood = false;
            mailTIL.setErrorEnabled(true);
            mailTIL.setError("The Email is required");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailIn).matches()) {
            mailTIL.setErrorEnabled(true);
            mailTIL.setError("Wrong email format");
            mailGood = false;
        } else {
            mailTIL.setErrorEnabled(false);
            mailGood = true;
        }
    }

    private void validateUsername() {
        String username = uName.getText().toString();
        if (username.isEmpty()) {
            uNameGood = false;
            uNameTIL.setErrorEnabled(true);
            uNameTIL.setError("The Username is required");
        }  else {
            uNameTIL.setErrorEnabled(false);
            uNameGood = true;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register) {
            validateUsername();
            validateEmail();
            validatePassword();
            validateConfirmPassword();
            if (mailGood && passGood && rPassGood && uNameGood) {
                final String First, Email, Password,Username;
                Email = mail.getText().toString();
                Password = pass.getText().toString();
                Username = uName.getText().toString();
                Users users = new Users(Username, Email, Password);

                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("https://thawing-fortress-83069.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();
                UsersClient client = retrofit.create(UsersClient.class);
                Call<Users> call = client.signup(users);
                Log.d("homie", "onClick: " + call.toString());
                disableControls();
                call.enqueue(new Callback<Users>() {
                    @Override
                    public void onResponse(Call<Users> call, Response<Users> response) {
                        enableControls();
                        Users users = response.body();
                        String xAuth = response.headers().get("x-auth");
                        session.LoginSession(Username, Password, xAuth);
                        Intent i = new Intent(RegistrationActivity.this, WelcomeActivity.class);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Users> call, Throwable t) {
                        enableControls();
                        Toast.makeText(RegistrationActivity.this, "Sorry, something went wrong!", Toast.LENGTH_LONG).show();
                        Log.e("homie", "onFailure: ", t);
                    }
                });
            } else{
                parent.startAnimation(shakingAnimation);
            }
        }
    }

    private void disableControls(){
        uName.setEnabled(false);
        mail.setEnabled(false);
        pass.setEnabled(false);
        rPass.setEnabled(false);
        Regbtn.setEnabled(false);
    }

    private void enableControls(){
        uName.setEnabled(true);
        mail.setEnabled(true);
        pass.setEnabled(true);
        rPass.setEnabled(true);
        Regbtn.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
