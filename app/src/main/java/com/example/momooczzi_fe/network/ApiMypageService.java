package com.example.momooczzi_fe.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.momooczzi_fe.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Callback;

public class ApiMypageService {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = BuildConfig.API_BASE_URL;

    // GET /me 요청
    public static void getMyPage(Context context, Callback callback) {
        // SharedPreferences에서 access_token 꺼내기
        SharedPreferences prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        String token = prefs.getString("access_token", null);

        if (token == null) {
            Log.e("ApiMypageService", "access_token 없음");
            return;
        }

        // 요청 생성
        Request request = new Request.Builder()
                .url(BASE_URL + "me")  // base URL이 `/`로 끝나지 않으면 슬래시 붙이기
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .build();

        // 비동기 요청
        client.newCall(request).enqueue(callback);
    }
}
