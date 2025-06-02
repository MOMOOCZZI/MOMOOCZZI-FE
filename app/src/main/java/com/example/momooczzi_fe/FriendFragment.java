package com.example.momooczzi_fe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.momooczzi_fe.network.ApiFriendService;
import com.example.momooczzi_fe.network.ApiMypageService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FriendFragment extends Fragment {
    public FriendFragment() {
        super(R.layout.friend_page);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout container = view.findViewById(R.id.friend_list_container);
        TextView nicknameText = view.findViewById(R.id.friend_title);
        TextView nicknameOneText = view.findViewById(R.id.my_nickname);

        // ✅ 내 nickname 가져오기
        ApiMypageService.getMyPage(requireContext(), new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String body = response.body().string();
                    try {
                        JSONObject json = new JSONObject(body);
                        String nickname = json.optString("nickname", "사용자");

                        requireActivity().runOnUiThread(() -> {
                            nicknameText.setText(nickname + " 님의 친구목록");
                            nicknameOneText.setText(nickname);
                        });

                    } catch (JSONException e) {
                        Log.e("FriendFragment", "JSON 파싱 오류", e);
                    }
                } else {
                    Log.e("FriendFragment", "❌ /me 응답 실패: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("FriendFragment", "❌ /me 요청 실패", e);
            }
        });

        // ✅ 친구 목록 가져오기
        ApiFriendService.getFriendList(requireContext(), new ApiFriendService.FriendListCallback() {
            @Override
            public void onSuccess(List<FriendItem> friends) {
                requireActivity().runOnUiThread(() -> {
                    container.removeAllViews();
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    int i = 1;

                    for (FriendItem friend : friends) {
                        View item = inflater.inflate(R.layout.friend_list_item, container, false);

                        ((TextView) item.findViewById(R.id.friend_number)).setText(String.valueOf(i++));
                        ((TextView) item.findViewById(R.id.friend_nickname)).setText(friend.nickname);
                        ((TextView) item.findViewById(R.id.friend_count)).setText(String.valueOf(friend.friend_count));
                        ((ImageView) item.findViewById(R.id.friend_heart))
                                .setImageResource(friend.liked_by_me ? R.drawable.active_heart : R.drawable.inactive_heart);

                        container.addView(item);
                    }
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("FriendFragment", "API 오류: " + errorMessage);
            }
        });
    }
}
