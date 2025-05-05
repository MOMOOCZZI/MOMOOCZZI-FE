package com.example.momooczzi_fe;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 cardPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ViewPager2
        cardPager = findViewById(R.id.cardPager);

        List<Integer> images = Arrays.asList(
                R.drawable.main_screen1,
                R.drawable.main_screen2
        );

        CardAdapter adapter = new CardAdapter(images);
        cardPager.setAdapter(adapter);

        cardPager.setOffscreenPageLimit(3);
        cardPager.setClipToPadding(false);
        cardPager.setClipChildren(false);

        // DotsIndicator 연결
        DotsIndicator dotsIndicator = findViewById(R.id.dotsIndicator);
        dotsIndicator.setViewPager2(cardPager);
    }
}
