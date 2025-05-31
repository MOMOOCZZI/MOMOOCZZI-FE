package com.example.momooczzi_fe;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationFoundFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SharedViewModel viewModel;

    public LocationFoundFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location_found, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.googleMap);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        view.findViewById(R.id.btn_next_recommand).setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), RecomandedList.class);
            intent.putExtra("gender", viewModel.getGender().getValue());
            intent.putExtra("emotion", viewModel.getEmotion().getValue());
            intent.putExtra("happen", viewModel.getHappen().getValue());
            intent.putExtra("lat", viewModel.getLatitude().getValue());
            intent.putExtra("lng", viewModel.getLongitude().getValue());
            startActivity(intent);
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // 초기값 체크
        Double lat = viewModel.getLatitude().getValue();
        Double lng = viewModel.getLongitude().getValue();
        if (lat != null && lng != null) {
            showMarkerOnMap(lat, lng);
        }

        // LiveData observe
        viewModel.getLatitude().observe(getViewLifecycleOwner(), l -> updateMapIfReady());
        viewModel.getLongitude().observe(getViewLifecycleOwner(), l -> updateMapIfReady());
    }

    private void updateMapIfReady() {
        if (mMap == null) return;
        Double lat = viewModel.getLatitude().getValue();
        Double lng = viewModel.getLongitude().getValue();
        if (lat != null && lng != null) {
            showMarkerOnMap(lat, lng);
        }
    }

    private void showMarkerOnMap(double lat, double lng) {
        LatLng userLocation = new LatLng(lat, lng);
        mMap.clear(); // 기존 마커 제거 (중복 방지)
        mMap.addMarker(new MarkerOptions().position(userLocation).title("현재 위치"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
    }
}
