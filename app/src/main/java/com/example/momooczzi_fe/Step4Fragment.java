package com.example.momooczzi_fe;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class Step4Fragment extends Fragment {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private Button shareLocationBtn;
    private FusedLocationProviderClient fusedLocationClient;

    public Step4Fragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step4, container, false);

        // 위치 클라이언트 초기화
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // 버튼 초기화 및 클릭 이벤트 처리
        shareLocationBtn = view.findViewById(R.id.shareLocationBtn);
        shareLocationBtn.setOnClickListener(v -> requestLocationPermission());

        return view;
    }

    // 위치 권한 요청
    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            fetchLocationAndProceed(); // 권한이 이미 있으면 위치 요청
        }
    }

    // 위치를 받아서 ViewModel에 저장하고 다음 프래그먼트로 이동
    private void fetchLocationAndProceed() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        double lat = location.getLatitude();
                        double lng = location.getLongitude();

                        // ViewModel에 위치 저장
                        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
                        viewModel.setLocation(lat, lng);
                    }

                    // 다음 프래그먼트(Step5)로 이동
                    ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
                    if (viewPager != null) {
                        viewPager.setCurrentItem(4, true);
                    }
                });
    }

    // 권한 요청 결과 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocationAndProceed();
            } else {
                Toast.makeText(getContext(), "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
