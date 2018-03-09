package com.example.noso.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class Setting extends AppCompatActivity {
    ImageView imgUser;
    EditText Username;
    Spinner status;
    Button saveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        myToolbar.setTitle("Settings");
        setSupportActionBar(myToolbar);

        imgUser = findViewById(R.id.imgUser);
        Username = findViewById(R.id.textName);
        status = findViewById(R.id.statusSpinner);
        saveChanges = findViewById(R.id.saveButton);
    }
}
