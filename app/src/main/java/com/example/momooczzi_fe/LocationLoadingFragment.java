package com.example.momooczzi_fe;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import android.Manifest;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

public class LocationLoadingFragment extends Fragment {

    private FusedLocationProviderClient fusedLocationClient;

    public LocationLoadingFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location_loading, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        // 딜레이 후 위치 가져오기 시도
        new Handler().postDelayed(this::fetchLocation, 1000);  // 1초 후 시도
    }

    private void fetchLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            try {
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(location -> {
                            ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
                            if (location != null) {
                                LocationViewModel viewModel = new ViewModelProvider(requireActivity()).get(LocationViewModel.class);
                                viewModel.setLocation(location.getLatitude(), location.getLongitude());
                                viewPager.setCurrentItem(5, true); // LocationFoundFragment
                            } else {
                                viewPager.setCurrentItem(6, true); // LocationFailedFragment
                            }
                        })
                        .addOnFailureListener(e -> {
                            ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
                            viewPager.setCurrentItem(6, true); // LocationFailedFragment
                        });
            } catch (SecurityException e) {
                e.printStackTrace();
                ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
                viewPager.setCurrentItem(6, true); // LocationFailedFragment
            }

        } else {
            // 이 경우는 이 함수 자체를 호출하면 안 되는 상황이지만 방어 로직 추가
            Toast.makeText(requireContext(), "위치 권한이 필요합니다", Toast.LENGTH_SHORT).show();
        }
    }




    private void goToFailedFragment() {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new LocationFailedFragment())
                .addToBackStack(null)
                .commit();
    }
}
