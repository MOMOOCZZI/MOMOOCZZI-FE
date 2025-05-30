package com.example.momooczzi_fe.network;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;


public interface Food {
    @GET("food")
    Call<List<FoodItem>> getFoodList();
}
