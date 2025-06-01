package com.example.momooczzi_fe.network;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesAPI {
    @GET("maps/api/place/nearbysearch/json")
    Call<PlacesResponse> getNearbyPlaces(
            @Query("location") String location,
            @Query("radius") int radius,
            @Query(value = "keyword", encoded = true) String keyword,
            @Query("type") String type,
            @Query("key") String apiKey
    );
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    PlacesAPI apiService = retrofit.create(PlacesAPI.class);
}
