package com.example.momooczzi_fe.network;

import android.content.Intent;
import android.util.Log;

import com.example.momooczzi_fe.BuildConfig;
import com.example.momooczzi_fe.Login;
import com.example.momooczzi_fe.MainActivity;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ApiLoginService {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = BuildConfig.API_BASE_URL;

    public static void login(String username, String password, okhttp3.Callback callback) {
        try {
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("password", password);

            RequestBody body = RequestBody.create(
                    json.toString(),
                    MediaType.get("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(BASE_URL + "login")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(callback);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
