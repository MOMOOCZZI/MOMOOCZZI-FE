package com.example.momooczzi_fe.network;

import com.example.momooczzi_fe.BuildConfig;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Callback;
import okhttp3.Response;

import org.json.JSONObject;

public class ApiSignupService {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = BuildConfig.API_BASE_URL;

    public static void signup(String userId, String password, String nickname,String intro, boolean marketing, boolean personal,
                              Callback callback) {
        try {
            JSONObject json = new JSONObject();
            json.put("user_id", userId);
            json.put("password", password);
            json.put("nickname", nickname);
            json.put("introduction", intro);
            json.put("marketing", marketing);
            json.put("personal", personal);

            RequestBody body = RequestBody.create(
                    json.toString(),
                    MediaType.get("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(BASE_URL + "signup")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(callback);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
