package UiActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.varbin.locationtracker.MyLocationManager.LocationGetter;
import com.varbin.locationtracker.R;

public class PermissionActivity extends AppCompatActivity {
SharedPreferences sharedPreferences ;
SharedPreferences.Editor editor;
Button button;
String[] permisions = {
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_SYNC_SETTINGS,
            Manifest.permission.WRITE_SYNC_SETTINGS,

};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        button = findViewById(R.id.GetPermission);
        PermissionCheck();
        sharedPreferences = getSharedPreferences("palm", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (!(sharedPreferences ==null)){
            boolean g = sharedPreferences.getBoolean("granted" , false);
            if (g) {
                startActivity(new Intent(getApplicationContext(), AccessActivity.class));
                overridePendingTransition(0,0);
                finish();
            }else {
                PermissionCheck();
            }
        }else {
            PermissionCheck();
        }
    }
    private void PermissionCheck() {
        if (LocationGetter.hasPermision(PermissionActivity.this , permisions)){
            startActivity(new Intent(getApplicationContext(), AccessActivity.class));
            finish();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LocationGetter.hasPermision(PermissionActivity.this , permisions)){
                    ActivityCompat.requestPermissions(PermissionActivity.this, permisions, 0);
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            editor.putBoolean("granted", true);
            editor.apply();
            startActivity(new Intent(getApplicationContext(), AccessActivity.class));
            overridePendingTransition(0,0);
            finish();
        }
        else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            editor.putBoolean("granted", false);
            editor.apply();
        }
    }
}