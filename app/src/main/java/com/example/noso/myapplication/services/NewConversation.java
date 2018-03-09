package com.example.noso.myapplication.services;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.noso.myapplication.ChatScreen;
import com.example.noso.myapplication.Interfaces.FriendsClient;
import com.example.noso.myapplication.PreferenceManager;
import com.example.noso.myapplication.R;
import com.example.noso.myapplication.beans.Users;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewConversation extends AppCompatActivity {
    FloatingActionButton fab;
    EditText search;
    String friend;
    List<String> Searched = new ArrayList<String>();
    List<Users> users;
    ArrayAdapter<String> arrayAdapter;
    List<String> names;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_conversation);

        listView = (ListView) findViewById(R.id.retrivedFriends);
        fab = findViewById(R.id.searchFriend);
        search = findViewById(R.id.newFriend);

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
                users = response.body();
                arrayAdapter = new ArrayAdapter<String>(NewConversation.this, android.R.layout.simple_list_item_1);

                names = new ArrayList<String>();
                for (int i = 0; i < users.size(); i++) {
                    names.add(users.get(i).getUsername());
                }

                arrayAdapter.addAll(names);

                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(NewConversation.this, "Clicked item!", Toast.LENGTH_LONG).show();
                        Intent Q = new Intent(NewConversation.this, ChatScreen.class);
                        startActivity(Q);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friend = search.getText().toString();
                if(arrayAdapter!=null){
                    arrayAdapter.clear();
                    for (int i = 0; i < users.size(); i++) {
                        if (friend.equals(users.get(i).getUsername())) {
                            Searched.add(users.get(i).getUsername());
                        }
                    }
                    arrayAdapter.addAll(Searched);
                    listView.setAdapter(arrayAdapter);
                    Searched.clear();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (search.getText().toString().isEmpty()) {
            finish();
        } else {
            search.setText("");
            arrayAdapter.clear();
            arrayAdapter.addAll(names);
            listView.setAdapter(arrayAdapter);
        }
    }
}
