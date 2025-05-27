package com.example.momooczzi_fe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.Arrays;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.viewPager);
        nextBtn = findViewById(R.id.nextBtn);

        List<OnboardingItem> pages = Arrays.asList(
                new OnboardingItem(R.drawable.cloudy, "그날 그날 날씨에 맞게", "날씨에 어울리는 음식을\n추천받을 수 있어요!"),
                new OnboardingItem(R.drawable.puzzle, "그날 감정에 따라", "화날 땐 매운 음식!\n즐거울 땐 달달한 음식 :)"),
                new OnboardingItem(R.drawable.heart, "사용자의 취향을 고려하여", "앱을 사용하며 좋아하셨던 음식을 \n 기반으로 음식을 추천해드려요.")
        );

        OnboardingAdapter adapter = new OnboardingAdapter(pages);
        viewPager.setAdapter(adapter);

        DotsIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager2(viewPager);

        nextBtn.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() < pages.size() - 1) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                finishOnboarding();
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == pages.size() - 1) {
                    nextBtn.setText("시작하기");
                } else {
                    nextBtn.setText("다음");
                }
            }
        });
    }

    private void finishOnboarding() {
        startActivity(new Intent(this, Login.class));
        finish();
    }
}
