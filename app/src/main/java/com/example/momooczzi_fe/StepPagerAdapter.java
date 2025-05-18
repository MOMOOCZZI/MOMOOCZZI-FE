package com.example.momooczzi_fe;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class StepPagerAdapter extends FragmentStateAdapter {

    public StepPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new Step1Fragment();
            case 1: return new Step2Fragment();
            case 2: return new Step3Fragment();
            case 3: return new Step4Fragment();
            case 4: return new LocationLoadingFragment();
            case 5: return new LocationFoundFragment();
            case 6: return new LocationFailedFragment();
            case 7: return new Step5Fragment();
            default: return new Step1Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }
}
