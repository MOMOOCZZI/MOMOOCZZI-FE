package com.example.momooczzi_fe.network;

import android.util.Log;

import com.example.momooczzi_fe.BuildConfig;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Callback;

public class ApiFoodService {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = BuildConfig.API_BASE_URL;

    public static void postFoodRecommendation(Boolean gender, String emotion, String happen,
                                              double lat, double lon, Callback callback) {
        try {
            Log.e("FoodAPI", "JSON 생성 시작");
            JSONObject json = new JSONObject();
            json.put("gender", gender);
            json.put("emotion", emotion);
            json.put("happen", happen);
            json.put("latitude", lat);
            json.put("longitude", lon);

            RequestBody body = RequestBody.create(
                    json.toString(),
                    MediaType.get("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(BASE_URL + "food")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(callback);
            Log.e("FoodAPI", "JSON 생성 성공");
        } catch (Exception e) {
            Log.e("FoodAPI", "JSON 생성 실패", e);
        }
    }
}
