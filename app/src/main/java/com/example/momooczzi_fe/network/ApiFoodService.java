package com.example.momooczzi_fe.network;

import android.content.Context;
import android.util.Log;
import android.content.SharedPreferences;

import com.example.momooczzi_fe.BuildConfig;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Callback;

public class ApiFoodService {
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();

    private static final String BASE_URL = BuildConfig.API_BASE_URL;

    public static void postFoodRecommendation(Context context, Boolean gender, String emotion, String happen,
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

            SharedPreferences prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
            String token = prefs.getString("access_token", null);

            if (token == null) {
                Log.e("ApiFoodService", "❌ access_token 없음");
                callback.onFailure(null, new IOException("access_token 없음"));
                return;
            }

            // 디버깅 로그 추가
            Log.d("FoodAPI", "요청 URL: " + BASE_URL + "food");
            Log.d("FoodAPI", "요청 JSON: " + json.toString());
            Log.d("FoodAPI", "토큰: Bearer " + token);

            Request request = new Request.Builder()
                    .url(BASE_URL + "food")
                    .addHeader("Authorization", "Bearer " + token)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(callback);
            Log.e("FoodAPI", "JSON 생성 성공");
        } catch (Exception e) {
            Log.e("FoodAPI", "JSON 생성 실패", e);
        }
    }
}
