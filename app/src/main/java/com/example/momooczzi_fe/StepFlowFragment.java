package com.example.momooczzi_fe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class StepFlowFragment extends Fragment {

    private ViewPager2 viewPager;

    public StepFlowFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_flow, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new StepPagerAdapter(requireActivity()));
        return view;
    }

    public void goToStep(int index) {
        viewPager.setCurrentItem(index, true);
    }
}
