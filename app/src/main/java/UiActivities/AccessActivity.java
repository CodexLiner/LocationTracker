package UiActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.varbin.locationtracker.R;

import static android.content.ContentValues.TAG;

public class AccessActivity extends AppCompatActivity {
    Button button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);
        button = findViewById(R.id.GetAccess);
//        startActivity(new Intent(this , MainActivity.class));
//        if (isAccess()){
//            if (isAccess()){ startActivity(new Intent(this , RegistrationActivity.class)); }
//        }else {
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
//                }
//            });
//        }
        onNotice();
    }

    private void onNotice() {
        if (Settings.Secure.getString(this.getContentResolver(),"enabled_notification_listeners").contains(getApplicationContext().getPackageName()))
        {
            //service is enabled do something
            startActivity(new Intent(this , RegistrationActivity.class));
            finish();
        } else {
            //service is not enabled try to enabled by calling...
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }
    }

    private boolean isAccess()  {
        final String aName = "com.varbin.locationtracker/AutoCallRecorder.MyAccessibilityService";
        boolean status = false;
        int code = 0;
        try {
            code = Settings.Secure.getInt(this.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "isAccess: " + code);
        if (code==1){
            TextUtils.SimpleStringSplitter mStrin = new TextUtils.SimpleStringSplitter(':');
            String setVal = Settings.Secure.getString(getContentResolver() , Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (setVal!=null){
                TextUtils.SimpleStringSplitter splitter = mStrin;
                splitter.setString(setVal);
                while (splitter.hasNext()){
                    String s = splitter.next();
                    if (s.equals(aName)){
                        return true;
                    }
                }
            }
        }
        return status;
    }
    @Override
    protected void onResume() {
        super.onResume();
//        if (isAccess()){
////            if (isAccess()){ startActivity(new Intent(this , RegistrationActivity.class)); }
//            onNotice();
//            finish();
//        }
        onNotice();
    }
}