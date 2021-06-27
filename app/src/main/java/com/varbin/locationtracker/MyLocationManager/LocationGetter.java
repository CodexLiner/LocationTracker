package com.varbin.locationtracker.MyLocationManager;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.varbin.locationtracker.APIs.CreaterClass;
import com.varbin.locationtracker.LocationService;

public class LocationGetter {
    private static FusedLocationProviderClient fs;

    public static void Starts(Context cn) {
        fs = LocationServices.getFusedLocationProviderClient(cn);
        getLastLocation(cn);
    }

    private static void getLastLocation(Context cn) {
        if (isLocationOn(cn)) {
            if (ActivityCompat.checkSelfPermission(cn, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(cn, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            fs.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location == null) {
//                        Toast.makeText(cn, "lang " + location.getLatitude(), Toast.LENGTH_SHORT).show();
                        CheckForNewLocation(cn);
                    }
                        CheckForNewLocation(cn);
                }
            });

        }
    }

    private static void CheckForNewLocation(Context cn) {
        LocationRequest lr = new LocationRequest();
        lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        lr.setInterval(5);
        lr.setSmallestDisplacement(5);
        lr.setFastestInterval(0);
        lr.setNumUpdates(1);
        fs = LocationServices.getFusedLocationProviderClient(cn);
        if (ActivityCompat.checkSelfPermission(cn, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(cn, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationCallback cb = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                CreaterClass.LocationSender(location.getLatitude(), location.getLongitude());

            }
        };
        fs.requestLocationUpdates(lr, cb, Looper.myLooper());
    }


    private static boolean isLocationOn(Context cn) {
        LocationManager locationManager = (LocationManager) cn.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}
