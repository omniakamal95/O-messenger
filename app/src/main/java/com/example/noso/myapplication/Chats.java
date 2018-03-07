package com.example.noso.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.noso.myapplication.Interfaces.FriendsClient;
import com.example.noso.myapplication.Interfaces.UsersClient;
import com.example.noso.myapplication.beans.Users;
import com.example.noso.myapplication.services.NewConversation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Chats extends AppCompatActivity {
    FloatingActionButton fab;
    PreferenceManager session;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        android.support.v7.widget.Toolbar myToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.chats_toolbar);
        myToolbar.setTitle("Chats");
        myToolbar.setBackgroundColor(R.color.navigationBarColor);
        myToolbar.setTitleTextColor(R.color.windowBackground);
        setSupportActionBar(myToolbar);
        session = new PreferenceManager(getApplicationContext());
        listView = findViewById(R.id.chats);


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        FriendsClient client = retrofit.create(FriendsClient.class);
        Call<List<Users>> call = client.friends(PreferenceManager.xAuthToken);
        Log.d("homie", "onClick: " + call.toString());
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                List<Users> users = response.body();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Chats.this, android.R.layout.simple_list_item_1);

                List<String> names = new ArrayList<String>();
                for (int i = 0; i < users.size(); i++) {
                    names.add(users.get(i).getUsername());
                }

                arrayAdapter.addAll(names);

                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(Chats.this, "Clicked item!", Toast.LENGTH_LONG).show();
                        Intent Q = new Intent(Chats.this, ChatScreen.class);
                        startActivity(Q);
                    }
                });


            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });

        fab = findViewById(R.id.newConversation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Chats.this, NewConversation.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.manageFriends:
                Intent i = new Intent(Chats.this, FriendsActivity.class);
                startActivity(i);
                return true;
            case R.id.settingsAcc:
                Intent q = new Intent(Chats.this, Setting.class);
                startActivity(q);
                return true;
            case R.id.logout:
                //TODO: get x-auth and send @PUT
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:3000/")
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();
                UsersClient client = retrofit.create(UsersClient.class);
                Call<String> call = client.logout(PreferenceManager.xAuthToken);
                Log.d("homie", "onClick: " + call.toString());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Intent intent = new Intent(Chats.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
                break;
        }

        return false;
    }
}
