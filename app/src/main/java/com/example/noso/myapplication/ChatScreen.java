package com.example.noso.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.noso.myapplication.beans.ChatMessage;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ChatScreen extends AppCompatActivity {
    private static int SIGN_IN_REQUEST_CODE = 1;
    RelativeLayout activity_chat_screen;
    FloatingActionButton fab;
    private FirebaseListAdapter<ChatMessage> adapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_Sign_out) {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                    Snackbar.make(activity_chat_screen, "You have been signed out. ", Snackbar.LENGTH_SHORT).show();
                    finish();
                }
            });

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.message_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Snackbar.make(activity_chat_screen, "Successfully logged in, Welcome!", Snackbar.LENGTH_SHORT).show();
                displayChatMessage();
            } else {
                Snackbar.make(activity_chat_screen, "We couldn't sign you in, please try again later", Snackbar.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        activity_chat_screen = findViewById(R.id.chat_screen);
        fab = findViewById(R.id.sendFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = findViewById(R.id.input);
                FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(input.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getDisplayName()));
                input.setText("");
            }
        });
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE);

        } else {
            Snackbar.make(activity_chat_screen, "Welcome " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), Snackbar.LENGTH_SHORT).show();
            displayChatMessage();

        }
    }

    private void displayChatMessage() {
        ListView ListOfMessages = findViewById(R.id.list_of_messages);
        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class, R.layout.list_view, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                TextView MessageText, MessageUser, MessageTime;
                MessageText = v.findViewById(R.id.message_text);
                MessageUser = v.findViewById(R.id.message_user);
                MessageTime = v.findViewById(R.id.message_time);
                MessageText.setText(model.getMessagetext());
                MessageUser.setText(model.getMessageuser());
                MessageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessagetime()));
            }
        };
        ListOfMessages.setAdapter(adapter);
    }


}
