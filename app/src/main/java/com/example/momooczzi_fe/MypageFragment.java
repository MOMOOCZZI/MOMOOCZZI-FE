package com.example.momooczzi_fe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.momooczzi_fe.network.ApiMypageService;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MypageFragment extends Fragment {
    public MypageFragment() {
        super(R.layout.mypage);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ApiMypageService.getMyPage(requireContext(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Mypage", "요청 실패: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Log.d("Mypage", "응답: " + responseBody);

                    try {
                        JSONObject json = new JSONObject(responseBody);
                        String nickname = json.getString("nickname");
                        String email = json.getString("email");
                        String intro = json.getString("introduction");

                        requireActivity().runOnUiThread(() -> {
                            TextView nameTextView = view.findViewById(R.id.name);
                            TextView emailTextView = view.findViewById(R.id.email);
                            EditText introEdit = view.findViewById(R.id.introduce);

                            nameTextView.setText(nickname + " 님");
                            emailTextView.setText(email);
                            introEdit.setText(intro);
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.e("Mypage", "응답 실패: " + response.code());
                }
            }
        });
    }
}
