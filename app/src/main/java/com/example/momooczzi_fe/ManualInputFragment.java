package com.example.momooczzi_fe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import android.widget.Button;

public class ManualInputFragment extends Fragment {

    public ManualInputFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manual_input, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        EditText inputField = view.findViewById(R.id.manualAddressField);
        Button submitBtn = view.findViewById(R.id.submitManualBtn);

        submitBtn.setOnClickListener(v -> {
            String address = inputField.getText().toString();
            if (!address.isEmpty()) {
                Toast.makeText(getContext(), "입력한 주소: " + address, Toast.LENGTH_SHORT).show();
                // 이후 처리 로직 추가
            }
        });
    }
}
