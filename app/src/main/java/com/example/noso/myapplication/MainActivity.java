package com.example.noso.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noso.myapplication.services.notificationServices;

import Services.NotificationService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text = findViewById(R.id.Welcome);
        Button btn = findViewById(R.id.btn);

        btn.setOnClickListener(MainActivity.this);


    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, notificationServices.class);
        this.startService(intent);
        Intent i = new Intent(this, FriendsActivity.class);
        startActivity(i);
    }
}
