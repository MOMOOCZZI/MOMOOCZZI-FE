package com.example.momooczzi_fe;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class MenuRecommandActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private StepPagerAdapter pagerAdapter;
    private View[] indicators; // indicator View 배열

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_recommand);

        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new StepPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setUserInputEnabled(false);

        ImageView nextButton = findViewById(R.id.next_btn);
        ImageView prevButton = findViewById(R.id.prev_btn);

        nextButton.setOnClickListener(v -> goToNextPage());
        prevButton.setOnClickListener(v -> goToPreviousPage());

        indicators = new View[] {
                findViewById(R.id.indicator1),
                findViewById(R.id.indicator2),
                findViewById(R.id.indicator3),
                findViewById(R.id.indicator4),
                findViewById(R.id.indicator5),
                findViewById(R.id.indicator6)  // ← Step5 (마지막 페이지)
        };

        updateIndicators(0);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                int indicatorIndex;

                if (position <= 4) {
                    indicatorIndex = position;
                } else if (position <= 6) {
                    indicatorIndex = 4; // Location 관련 프래그먼트 → Step4에서 멈춘 것처럼
                } else {
                    indicatorIndex = 5; // Step5 위치
                }

                updateIndicators(indicatorIndex);
            }
        });

    }

    public void goToNextPage() {
        int current = viewPager.getCurrentItem();

        if (current == 5) {
            // LocationFoundFragment → Step5Fragment (스킵)
            viewPager.setCurrentItem(7, false);
        } else if (current < pagerAdapter.getItemCount() - 1) {
            viewPager.setCurrentItem(current + 1, true);
        }
    }


    public void goToPreviousPage() {
        if (viewPager.getCurrentItem() > 0) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    private void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_active : R.drawable.indicator_inactive
            );
        }
    }
}
