package com.example.noso.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.noso.myapplication.Interfaces.FriendsClient;
import com.example.noso.myapplication.beans.Users;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Users: special
 * Date: 13-12-22
 * Time: 下午3:26
 * Mail: specialcyci@gmail.com
 */
public class FriendsList extends Fragment {

    private View parentView;
    private ListView listView;
    private LinearLayout errorLayout;
    private Call<List<Users>> call;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.friends_list, container, false);
        listView = (ListView) parentView.findViewById(R.id.friendsList);
        errorLayout=(LinearLayout) parentView.findViewById(R.id.layout_error_friends);
        initView();
        return parentView;
    }

    private void initView() {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://thawing-fortress-83069.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        FriendsClient client = retrofit.create(FriendsClient.class);
        call = client.friends(PreferenceManager.xAuthToken);
        Log.d("homie", "onClick: " + call.toString());
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                List<Users> users = response.body();
                if(users==null || users.size()==0){
                    errorLayout.setVisibility(View.VISIBLE);
                }else{
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
                    List<String> names = new ArrayList<String>();
                    for (int i = 0; i < users.size(); i++) {
                        names.add(users.get(i).getUsername());
                    }
                    arrayAdapter.addAll(names);
                    listView.setAdapter(arrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getActivity(), "Clicked item!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if(call.isExecuted())
            call.cancel();
    }
}
