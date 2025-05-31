package com.example.momooczzi_fe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class Step1Fragment extends Fragment {

    private ImageButton male, female;
    private SharedViewModel sharedViewModel;

    public Step1Fragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step1, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        male = view.findViewById(R.id.maleIcon);
        female = view.findViewById(R.id.femaleIcon);

        male.setBackgroundResource(0);
        female.setBackgroundResource(0);

        // 남성 클릭
        male.setOnClickListener(v -> {
            male.setBackgroundResource(R.drawable.bg_selected); // 선택 테두리
            female.setBackgroundResource(0); // 다른 쪽은 해제
            sharedViewModel.setGender(true);
            Log.e("RECOMMAND_DEBUG",  "성별" + sharedViewModel.getGender().getValue());
        });

        // 여성 클릭
        female.setOnClickListener(v -> {
            female.setBackgroundResource(R.drawable.bg_selected);
            male.setBackgroundResource(0);
            sharedViewModel.setGender(false);
            Log.e("RECOMMAND_DEBUG",  "성별" + sharedViewModel.getGender().getValue());
        });
        return view;
    }
}
