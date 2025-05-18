package com.example.momooczzi_fe;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // 반드시 빈 생성자 필요
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ViewPager2 cardPager = view.findViewById(R.id.cardPager);
        DotsIndicator dotsIndicator = view.findViewById(R.id.dotsIndicator);

        List<Integer> images = Arrays.asList(
                R.drawable.main_screen1,
                R.drawable.main_screen2
        );

        CardAdapter adapter = new CardAdapter(images);
        cardPager.setAdapter(adapter);
        dotsIndicator.setViewPager2(cardPager);

        adapter.setOnItemClickListener(position -> {
            if (position == 1) {
                // ✅ MenuRecommandActivity로 전환
                Intent intent = new Intent(requireContext(), MenuRecommandActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

}
