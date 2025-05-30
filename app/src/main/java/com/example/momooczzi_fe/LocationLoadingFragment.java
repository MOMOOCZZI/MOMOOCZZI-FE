package com.example.momooczzi_fe;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Looper;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import android.location.Location;

import android.Manifest;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

public class LocationLoadingFragment extends Fragment {

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

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

            LocationRequest locationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(1000) // 1초마다 위치 요청

                    // .setNumUpdates(1)도 가능하지만 deprecated 상황이 있어서 사용 X
                    ;

            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) return;

                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
                        viewModel.setLocation(location.getLatitude(), location.getLongitude());

                        ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
                        viewPager.setCurrentItem(5, true); // 성공

                        // ✅ 더 이상 위치 업데이트 필요 없으면 중지
                        fusedLocationClient.removeLocationUpdates(locationCallback);
                    } else {
                        ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
                        viewPager.setCurrentItem(6, true); // 실패
                    }
                }
            };

            // 위치 요청 시작
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

        } else {
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
