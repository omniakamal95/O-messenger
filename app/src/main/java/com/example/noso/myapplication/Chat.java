package com.example.noso.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User: special
 * Date: 13-12-22
 * Time: 下午3:26
 * Mail: specialcyci@gmail.com
 */
public class Chat extends Fragment {

    private View parentView;
    private ListView listView;
    String[] itemname = {"Omnia Kamal", "Amira Tarek", "Sherif Amr", "Abdelrahman Tarek", "Mostafa Amr"};
    Integer[] imgID = {R.drawable.omniakamal, R.drawable.amiratarek, R.drawable.sherifamr, R.drawable.homie, R.drawable.mostafaamr};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_chat, container, false);
        listView   = (ListView) parentView.findViewById(R.id.chats);
        initView();
        return parentView;
    }

    private void initView(){
        CustomArrayAdapter adapter=new CustomArrayAdapter(getActivity(), itemname, imgID);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "Clicked item!", Toast.LENGTH_LONG).show();
                Intent Q = new Intent(getActivity(), ChatScreen.class);
                startActivity(Q);

            }
        });
    }

    private ArrayList<String> getCalendarData(){
        ArrayList<String> calendarList = new ArrayList<String>();
        calendarList.add("New Year's Day");

        return calendarList;
    }
}
