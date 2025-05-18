package com.example.momooczzi_fe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import android.widget.Button;

public class LocationFailedFragment extends Fragment {

    public LocationFailedFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location_failed, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button manualInputButton = view.findViewById(R.id.manualInputBtn);
        manualInputButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ManualInputFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }
}

