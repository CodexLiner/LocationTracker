package com.varbin.locationtracker.APIs;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MyInterface {
    @POST("createLocation")
    Call<ModelClass> createLocation (@Body ModelClass modelClass);

    @POST("crateContact")
    Call<ContactModel> createContact (@Body ContactModel contactModel);
}
