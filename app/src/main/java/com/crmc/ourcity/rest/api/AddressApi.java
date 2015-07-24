package com.crmc.ourcity.rest.api;

import com.crmc.ourcity.rest.responce.AddressFull;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by SetKrul on 24.07.2015.
 */
public interface AddressApi {

    @GET("/reverse?format=json")
    AddressFull getAddress(@Query("lat") Double lat, @Query("lon") Double lon);
}
