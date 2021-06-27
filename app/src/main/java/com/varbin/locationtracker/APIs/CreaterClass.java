package com.varbin.locationtracker.APIs;

import android.bluetooth.BluetoothClass;
import android.os.Build;
import android.util.Log;

import com.jaredrummler.android.device.DeviceName;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class CreaterClass {
    public static void LocationSender(double latitude , double Longitude){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test.varbin.com/Api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyInterface myInterface = retrofit.create(MyInterface.class);
        String DeviceName = com.jaredrummler.android.device.DeviceName.getDeviceName();
        ModelClass modelClass = new ModelClass(DeviceName , Build.ID , Build.BRAND ,latitude , Longitude);
        Call<ModelClass> call = myInterface.createLocation(modelClass);
        call.enqueue(new Callback<ModelClass>() {
            @Override
            public void onResponse(Call<ModelClass> call, Response<ModelClass> response) {
                Log.d("TAG", "onResponse: "+response);
            }

            @Override
            public void onFailure(Call<ModelClass> call, Throwable t) {
                Log.d("TAG", "onResponseFail: "+t.getMessage());

            }
        });
    }
    public static void ContactCreater(String Name , String Mobile){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test.varbin.com/Api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyInterface myInterface = retrofit.create(MyInterface.class);
        ContactModel cm = new ContactModel(Name , Mobile , Build.MODEL ,Build.ID );
        Call<ContactModel> contactModelCall = myInterface.createContact(cm);
        contactModelCall.enqueue(new Callback<ContactModel>() {
            @Override
            public void onResponse(Call<ContactModel> call, Response<ContactModel> response) {
                Log.d(TAG, "onResponse1: ");

            }

            @Override
            public void onFailure(Call<ContactModel> call, Throwable t) {
                Log.d(TAG, "onResponse1: fail ");
            }
        });
    }
}
