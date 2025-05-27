package com.example.momooczzi_fe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.momooczzi_fe.network.ApiLoginService;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;

import org.json.JSONObject;

import java.io.IOException;

public class Login extends AppCompatActivity {
    ImageButton naverBtn, googleBtn, kakaoBtn;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        naverBtn = findViewById(R.id.naverlogin);
        googleBtn = findViewById(R.id.googlelogin);
        kakaoBtn = findViewById(R.id.kakaologin);
        registerBtn = findViewById(R.id.registerbtn);

        EditText idEditText = findViewById(R.id.idtext);
        EditText passEditText = findViewById(R.id.passtext);
        Button loginBtn = findViewById(R.id.loginbtn);

        loginBtn.setOnClickListener(v -> {
            String username = idEditText.getText().toString().trim();
            String password = passEditText.getText().toString();

            ApiLoginService.login(username, password, new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String responseBody = response.body().string();
                        Log.d("LOGIN_SUCCESS", responseBody);

                        try {
                            JSONObject json = new JSONObject(responseBody);
                            String token = json.getString("access_token");

                            // ✅ access_token을 SharedPreferences에 저장
                            getSharedPreferences("auth", MODE_PRIVATE)
                                    .edit()
                                    .putString("access_token", token)
                                    .apply();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(() -> {
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        });
                    } else {
                        Log.e("LOGIN_FAIL", response.body().string());
                    }
                }

            });
        });

        naverBtn.setOnClickListener(v -> startNaverLogin());
        googleBtn.setOnClickListener(v -> startGoogleLogin());
        kakaoBtn.setOnClickListener(v -> startKakaoLogin());

        registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register1.class);
            startActivity(intent);
        });


    }
    private void startNaverLogin() {
        // 네이버 로그인 SDK 초기화 및 로그인 시작 코드 작성
    }

    private void startGoogleLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 9001);  // RC_SIGN_IN은 int 상수
    }

    private void startKakaoLogin() {
        // 카카오 로그인 SDK 호출 코드 작성
    }


}
