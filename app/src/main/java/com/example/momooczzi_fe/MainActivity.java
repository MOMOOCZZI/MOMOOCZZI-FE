package com.example.momooczzi_fe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView navFriend, navHome, navEmoji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_screen); // 하단바 포함된 전체 화면

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        navFriend = findViewById(R.id.nav_friend);
        navHome = findViewById(R.id.nav_home);
        navEmoji = findViewById(R.id.nav_emoji);

        navFriend.setOnClickListener(v -> {
            setActiveTab("friend");
            loadFragment(new FriendFragment());
        });

        navHome.setOnClickListener(v -> {
            setActiveTab("home");
            loadFragment(new HomeFragment());
        });

        navEmoji.setOnClickListener(v -> {
            setActiveTab("emoji");
            loadFragment(new MypageFragment());
        });

        // 첫 화면 기본 설정
        setActiveTab("home");
        loadFragment(new HomeFragment());
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
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
