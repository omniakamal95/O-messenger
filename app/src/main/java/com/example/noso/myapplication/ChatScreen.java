package com.example.noso.myapplication;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noso.myapplication.beans.ChatMessage;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ChatScreen extends AppCompatActivity implements View.OnClickListener{
    public static final int GET_FROM_GALLERY = 3;
    public static final int GET_FROM_CAMERA = 4;
    private static int SIGN_IN_REQUEST_CODE = 1;
    private LinearLayout activity_chat_screen,messageBoxLayout,revealingLayout;
    private RelativeLayout relGesture,relFile,relCamera;

    FloatingActionButton fab, cameraBtn,fileBtn,gestureBtn;
    EditText input;
    boolean chatMode;
    private FirebaseListAdapter<ChatMessage> adapter;
    Animation moveIn,moveOut,moveInFile,moveInGesture,moveInCamera;

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
        if ((requestCode == GET_FROM_GALLERY || requestCode == GET_FROM_CAMERA) && resultCode == Activity.RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                Log.e("homie", "onActivityResult: " + bitmap);
                TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                if (!textRecognizer.isOperational()) {
                    Log.e("ERROR", "Detector Dependecies are not ready yet");
                } else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = textRecognizer.detect(frame);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < items.size(); i++) {
                        TextBlock item = items.valueAt(i);
                        stringBuilder.append(item.getValue());
                        stringBuilder.append("\n");
                    }
                    input.setText(stringBuilder.toString());
                    Log.d("homie", "onActivityResult: " + stringBuilder.length());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(ChatScreen.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        activity_chat_screen = findViewById(R.id.chat_screen);
        fab = findViewById(R.id.sendFab);
        cameraBtn=findViewById(R.id.btn_camera);
        gestureBtn=findViewById(R.id.btn_gesture);
        fileBtn=findViewById(R.id.btn_file);
        input = findViewById(R.id.input);
        relCamera=findViewById(R.id.rel_camera);
        relFile=findViewById(R.id.rel_file);
        relGesture=findViewById(R.id.rel_gesture);
        revealingLayout=findViewById(R.id.revealingLayout);
        messageBoxLayout=findViewById(R.id.message_box_layout);



        moveIn= AnimationUtils.loadAnimation(this,R.anim.move_from_right);
        moveOut=AnimationUtils.loadAnimation(this,R.anim.move_out_right);
        moveInCamera=AnimationUtils.loadAnimation(this,R.anim.move_in_camera);
        moveInFile=AnimationUtils.loadAnimation(this,R.anim.move_in_file);
        moveInGesture=AnimationUtils.loadAnimation(this,R.anim.move_in_gesture);


        cameraBtn.setOnClickListener(this);
        gestureBtn.setOnClickListener(this);
        fileBtn.setOnClickListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chatMode){
                    FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(input.getText().toString(),
                            FirebaseAuth.getInstance().getCurrentUser().getDisplayName()));
                    input.setText("");
                }else{
                    revealingLayout.setVisibility(View.VISIBLE);
                    messageBoxLayout.setVisibility(View.GONE);
                    revealingLayout.startAnimation(moveIn);
                    relGesture.startAnimation(moveInGesture);
                    relFile.startAnimation(moveInFile);
                    relCamera.startAnimation(moveInCamera);
                }
            }
        });
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE);

        } else {
            Snackbar.make(activity_chat_screen, "Welcome " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), Snackbar.LENGTH_SHORT).show();
            displayChatMessage();

        }

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                final String messageText=input.getText().toString().trim();
                if(messageText.isEmpty()){
                    chatMode=false;
                    fab.setImageResource(R.drawable.ic_add);
                }else{
                    fab.setImageResource(R.drawable.ic_send);
                    chatMode=true;
                }
            }
        });
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_camera:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChatScreen.this);
                alertDialog.setTitle("choose picture from ..");
                alertDialog.setPositiveButton("camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, GET_FROM_CAMERA);
                    }
                });
                alertDialog.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(intent, GET_FROM_GALLERY);
                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.show();
                break;
            case R.id.btn_gesture:
                Toast.makeText(this, "Currently Unavailable", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_file:
                Toast.makeText(this, "Currently Unavailable", Toast.LENGTH_SHORT).show();
                break;

        } revealingLayout.startAnimation(moveOut);
          messageBoxLayout.setVisibility(View.VISIBLE);
    }
}
