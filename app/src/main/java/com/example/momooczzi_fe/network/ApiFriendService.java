package com.example.momooczzi_fe.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.momooczzi_fe.BuildConfig;
import com.example.momooczzi_fe.FriendItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiFriendService {

    public interface FriendListCallback {
        void onSuccess(List<FriendItem> friends);
        void onFailure(String errorMessage);
    }

    private static final String BASE_URL = BuildConfig.API_BASE_URL + "friend_list";
    private static final OkHttpClient client = new OkHttpClient();

    public static void getFriendList(Context context, FriendListCallback callback) {
        // 1. 토큰 꺼내기
        SharedPreferences prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        String token = prefs.getString("access_token", null);

        if (token == null) {
            Log.e("ApiFriendService", "❌ access_token 없음");
            callback.onFailure("토큰이 없습니다.");
            return;
        }

        // 2. 요청 로그
        Log.e("ApiFriendService", "✅ 요청 URL: " + BASE_URL);
        Log.e("ApiFriendService", "✅ access_token: " + token);

        // 3. 빈 바디 (POST)
        RequestBody body = RequestBody.create("{}", MediaType.parse("application/json"));

        // 4. 요청 생성
        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("Content-Type", "application/json")
                .build();

        // 5. 요청 실행
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("ApiFriendService", "❌ 네트워크 실패: " + e.getMessage());
                callback.onFailure("API 실패: " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.e("ApiFriendService", "✅ 응답 코드: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    Log.e("ApiFriendService", "✅ 응답 본문: " + json);

                    Type listType = new TypeToken<List<FriendItem>>() {}.getType();
                    List<FriendItem> friendList = new Gson().fromJson(json, listType);
                    callback.onSuccess(friendList);
                } else {
                    Log.e("ApiFriendService", "❌ 응답 실패: " + response.code());
                    callback.onFailure("응답 실패: " + response.code());
                }
            }
        });
    }
}
