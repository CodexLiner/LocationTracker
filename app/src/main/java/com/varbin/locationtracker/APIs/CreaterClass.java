package com.varbin.locationtracker.APIs;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreaterClass {
    public static void LocationSender(double latitude , double Longitude){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test.varbin.com/Api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyInterface myInterface = retrofit.create(MyInterface.class);
        ModelClass modelClass = new ModelClass("Model 2600" , "5458464885" , "Honor" ,latitude , Longitude);
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
}
