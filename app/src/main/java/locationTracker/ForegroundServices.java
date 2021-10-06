package locationTracker;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.varbin.locationtracker.APIs.CreaterClass;
import com.varbin.locationtracker.MyLocationManager.LocationGetter;
import com.varbin.locationtracker.R;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import UiActivities.MainActivity;
import callDetails.lastCall;
import networkStates.networkState;
import notifications.NotificationService;
import synceAdapter.AccountConstants;

public class ForegroundServices extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("App is Running")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_baseline_add_location_alt_24)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        Intent intent1 = new Intent(getApplicationContext() , NotificationService.class);
        startService(intent1);
        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                  LocationGetter.Starts(getApplicationContext());
                  CreaterClass.getStatus(getApplicationContext());
//                AccountConstants.mainThread = true;
//                try {
//                    CreaterClass.FileDataUploader("", "","");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }, 1, 20000, TimeUnit.MILLISECONDS);
        //stopSelf();
        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent in = new Intent(getApplicationContext() , this.getClass());
        in.setPackage(getPackageName());
        PendingIntent rs = PendingIntent.getService(getApplicationContext(),1, in ,  PendingIntent.FLAG_ONE_SHOT);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.ELAPSED_REALTIME , SystemClock.elapsedRealtime() + 1000 , rs );
        super.onTaskRemoved(rootIntent);

    }
}
