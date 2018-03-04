package com.example.noso.myapplication.services;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.noso.myapplication.ChatScreen;
import com.example.noso.myapplication.Chats;
import com.example.noso.myapplication.CustomArrayAdapter;
import com.example.noso.myapplication.R;

public class NewConversation extends AppCompatActivity {
    private ListView listView;
    FloatingActionButton fab ;
    EditText search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_conversation);

        listView   = (ListView) findViewById(R.id.retrivedFriends);
        fab = findViewById(R.id.searchFriend);
        search = findViewById(R.id.newFriend);
        String[] itemname = {"Omnia Kamal", "Amira Tarek", "Sherif Amr", "Abdelrahman Tarek", "Mostafa Amr"};
        Integer[] imgID = {R.drawable.omniakamal, R.drawable.amiratarek, R.drawable.sherifamr, R.drawable.homie, R.drawable.mostafaamr};
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, itemname, imgID);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(NewConversation.this, "item clicked", Toast.LENGTH_SHORT).show();
                Intent Q = new Intent(NewConversation.this, ChatScreen.class);
                startActivity(Q);

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }

        });

    }
}
