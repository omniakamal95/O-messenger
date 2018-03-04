package com.example.noso.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Chats extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        listView = (ListView) findViewById(R.id.chats);
        String[] itemname = {"Omnia Kamal", "Amira Tarek", "Sherif Amr", "Abdelrahman Tarek", "Mostafa Amr"};
        Integer[] imgID = {R.drawable.omniakamal, R.drawable.amiratarek, R.drawable.sherifamr, R.drawable.homie, R.drawable.mostafaamr};
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, itemname, imgID);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Chats.this, "item clicked", Toast.LENGTH_SHORT).show();
                Intent Q = new Intent(Chats.this, ChatScreen.class);
                startActivity(Q);
            }

        });
    }

}
