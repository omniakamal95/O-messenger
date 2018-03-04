package com.example.noso.myapplication.services;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.noso.myapplication.R;

import br.com.goncalves.pugnotification.notification.PugNotification;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 * //extends firebasenotificationservice
 */
public class notificationServices extends IntentService {
    public static final String TAG = "Ay7aga";

    public notificationServices() {
        super("notificationServices");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        fireNotification();

    }

    private void fireNotification() {
        String message = ("test test test");
        PugNotification.with(this)
                .load()
                .title("title")
                .message(message)
                .bigTextStyle("text")
                .smallIcon(R.drawable.pugnotification_ic_launcher)
                .largeIcon(R.drawable.pugnotification_ic_launcher)
                .flags(Notification.DEFAULT_ALL)
                .simple()
                .build();
    }


    private boolean isNotificationNeeded() {
        Toast.makeText(this, "ay7agaa", Toast.LENGTH_SHORT).show();
        return  true;
    }
}





