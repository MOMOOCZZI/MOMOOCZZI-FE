package com.example.momooczzi_fe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;

public class Login extends AppCompatActivity {
    ImageButton naverBtn, googleBtn, kakaoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login); // xml 이름에 맞게 수정

        naverBtn = findViewById(R.id.naverlogin);
        googleBtn = findViewById(R.id.googlelogin);
        kakaoBtn = findViewById(R.id.kakaologin);

        naverBtn.setOnClickListener(v -> startNaverLogin());
        googleBtn.setOnClickListener(v -> startGoogleLogin());
        kakaoBtn.setOnClickListener(v -> startKakaoLogin());
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
