package com.example.momooczzi_fe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MypageActivity extends AppCompatActivity {

    private ImageView navFriend, navHome, navEmoji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        navFriend = findViewById(R.id.nav_friend);
        navHome = findViewById(R.id.nav_home);
        navEmoji = findViewById(R.id.nav_emoji);

        // 현재 페이지: 마이페이지니까 클릭된 상태로
        setActiveTab("emoji");

        navFriend.setOnClickListener(v -> {
            setActiveTab("friend");
            // TODO: 친구 페이지로 이동
            // 예시: startActivity(new Intent(this, FriendActivity.class));
        });

        navHome.setOnClickListener(v -> {
            setActiveTab("home");
            startActivity(new Intent(this, MainActivity.class));
        });

        navEmoji.setOnClickListener(v -> {
            // 현재 페이지니까 이동 안 함
        });
    }

    private void setActiveTab(String tab) {
        switch (tab) {
            case "friend":
                navFriend.setImageResource(R.drawable.navigate_clicked_friend);
                navHome.setImageResource(R.drawable.navigate_home);
                navEmoji.setImageResource(R.drawable.navigate_mypage);
                break;
            case "home":
                navFriend.setImageResource(R.drawable.navigate_friend);
                navHome.setImageResource(R.drawable.navigate_clicked_home);
                navEmoji.setImageResource(R.drawable.navigate_mypage);
                break;
            case "emoji":
                navFriend.setImageResource(R.drawable.navigate_friend);
                navHome.setImageResource(R.drawable.navigate_home);
                navEmoji.setImageResource(R.drawable.navigate_clicked_mypage);
                break;
        }
    }
}
