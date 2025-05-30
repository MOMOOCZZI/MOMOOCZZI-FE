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

import java.util.List;

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


        ApiFriendService.getFriendList(requireContext(), new ApiFriendService.FriendListCallback() {
            @Override
            public void onSuccess(List<FriendItem> friends) {
                requireActivity().runOnUiThread(() -> {
                    container.removeAllViews();
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    int i = 1;

                    if (!friends.isEmpty()) {
                        nicknameText.setText(friends.get(0).nickname + " 님의 친구목록");
                        nicknameOneText.setText(friends.get(0).nickname);
                    }

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
