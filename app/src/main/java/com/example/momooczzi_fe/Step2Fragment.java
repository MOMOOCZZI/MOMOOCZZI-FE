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

public class Step2Fragment extends Fragment {


    private ImageButton happy, worried, sleepy, full, confused, blank, angry, smile;
    private ImageButton[] emojiButtons;
    private SharedViewModel sharedViewModel;

    public Step2Fragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step2, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        happy = view.findViewById(R.id.emojiHappy);
        worried = view.findViewById(R.id.emojiWorried);
        sleepy = view.findViewById(R.id.emojiSleepy);
        full = view.findViewById(R.id.emojiFull);
        confused = view.findViewById(R.id.emojiConfused);
        blank = view.findViewById(R.id.emojiBlank);
        angry = view.findViewById(R.id.emojiAngry);
        smile = view.findViewById(R.id.emojiSmile);

        emojiButtons = new ImageButton[]{happy, worried, sleepy, full, confused, blank, angry, smile};

        for (ImageButton button : emojiButtons) {
            button.setBackgroundResource(0);  // 초기화
            button.setOnClickListener(v -> {
                clearSelection();
                v.setBackgroundResource(R.drawable.bg_selected); // 테두리 drawable 지정
                String emotion = getEmotionById(button.getId());
                sharedViewModel.setEmotion(emotion);
                Log.e("RECOMMAND_DEBUG",  "감정" + sharedViewModel.getEmotion().getValue()+
                        ", 성별" + sharedViewModel.getGender().getValue());
            });
        }

        return view;
    }
    private void clearSelection() {
        for (ImageButton button : emojiButtons) {
            button.setBackgroundResource(0);  // 선택 해제
        }
    }

    private String getEmotionById(int id) {
        if (id == R.id.emojiHappy) return "happy";
        else if (id == R.id.emojiWorried) return "worried";
        else if (id == R.id.emojiSleepy) return "sleepy";
        else if (id == R.id.emojiFull) return "full";
        else if (id == R.id.emojiConfused) return "confused";
        else if (id == R.id.emojiBlank) return "blank";
        else if (id == R.id.emojiAngry) return "angry";
        else if (id == R.id.emojiSmile) return "smile";
        else return "";
    }
}
