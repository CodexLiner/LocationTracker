package com.varbin.locationtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.varbin.locationtracker.APIs.CreaterClass;

public class RegistrationActivity extends AppCompatActivity {
    EditText Name , Email , Mobile ;
    Button LoginButton;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Name = findViewById(R.id.EmployeeName);
//        Email = findViewById(R.id.EmployeeId);
        Mobile = findViewById(R.id.EmployeeMobile);
        LoginButton = findViewById(R.id.LoginButton);
        sharedPreferences = getSharedPreferences("LoginInfo" , MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
        PermissionCheck();
        if (sharedPreferences.contains("Name")) {
            Intent intent = new Intent(this , MainActivity.class);
            intent.putExtra("Name" , sharedPreferences.getString("Name" , null));
            intent.putExtra("Email", sharedPreferences.getString("Email", null));
            intent.putExtra("Mobile" , sharedPreferences.getString("Mobile" , null));
            startActivity(intent);
            overridePendingTransition(0,0);
        }
        LoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String Ename , Eid , Enum ;
                    Ename = Name.getText().toString().toLowerCase().trim();
                    Eid = "Email.getText().toString().toLowerCase().trim();";
                    Enum = Mobile.getText().toString().trim();

                    if (Ename.equals("")){
                        Name.requestFocus();
                        Name.setError("Required");
                        return;
                    }
//                    if (Eid.equals("")){
//                        Email.requestFocus();
//                        Email.setError("Required");
//                        return;
//                    }
                    if (Enum.equals("")){
                        Mobile.requestFocus();
                        Mobile.setError("Required");
                        return;
                    }
                    SharedMaker( Eid , Ename , Enum);
                }
            });
        }

    private void PermissionCheck() {
       if ( ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
           ActivityCompat.requestPermissions(this , new String[] { Manifest.permission.ACCESS_FINE_LOCATION} , 0);
       }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void SharedMaker(String eid, String ename, String anEnum) {
        editor.putString("Mobile", anEnum);
        editor.putString("Email", eid);
        editor.putString("Name" , ename);
        editor.commit();
        editor.apply();
        CreaterClass.ContactCreater(ename , anEnum);
        startActivity(new Intent(getApplicationContext() , MainActivity.class));
        overridePendingTransition(0,0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}