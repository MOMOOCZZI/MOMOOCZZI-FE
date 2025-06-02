package com.example.momooczzi_fe;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.momooczzi_fe.network.ApiMypageService;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private TextView usernameText;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        usernameText = view.findViewById(R.id.usernameText);
        fetchNickname();

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
                Intent intent = new Intent(requireContext(), MenuRecommandActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void fetchNickname() {
        ApiMypageService.getMyPage(requireContext(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(requireContext(), "유저 정보 요청 실패", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    try {
                        JSONObject obj = new JSONObject(json);
                        String nickname = obj.getString("nickname");

                        requireActivity().runOnUiThread(() -> {
                            usernameText.setText(nickname + " 님");
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(requireContext(), "닉네임 가져오기 실패", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }
}
