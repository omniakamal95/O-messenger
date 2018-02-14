package Services;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.noso.myapplication.Chat;
import com.example.noso.myapplication.ChatScreen;
import com.example.noso.myapplication.MainActivity;
import com.example.noso.myapplication.R;

import br.com.goncalves.pugnotification.notification.PugNotification;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class NotificationService extends IntentService {
    public static final String TAG = "Ay7aga";

    public NotificationService() {
        super("NotificationService");
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
                .bigTextStyle("long title")
                .smallIcon(R.drawable.icon_home)
                .largeIcon(R.drawable.icon_profile)
                .flags(Notification.DEFAULT_ALL)
                .autoCancel(true)
                .click(MainActivity.class)
                .simple()
                .build();
    }


    private boolean isNotificationNeeded() {
        Toast.makeText(this, "ay7agaa", Toast.LENGTH_SHORT).show();
        return  true;
    }
    }





