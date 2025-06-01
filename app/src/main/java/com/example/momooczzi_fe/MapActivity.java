package com.example.momooczzi_fe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.momooczzi_fe.PlaceAdapter;


import com.example.momooczzi_fe.databinding.ActivityMapBinding;
import com.example.momooczzi_fe.network.PlacesAPI;
import com.example.momooczzi_fe.network.PlacesResponse;
import com.example.momooczzi_fe.network.PlaceResult;
import com.example.momooczzi_fe.network.PlaceGeometry;
import com.example.momooczzi_fe.network.PlaceLocation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapBinding binding;
    private RecyclerView recyclerView;
    private PlaceAdapter adapter;
    private String key;
    private double lat, lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null)
            mapFragment.getMapAsync(this);

        recyclerView = findViewById(R.id.rv_places);
        adapter = new PlaceAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent intent = getIntent();

        lat = intent.getDoubleExtra("latitude", 37.5665);
        lng = intent.getDoubleExtra("longitude", 126.9780);

        LatLng center = new LatLng(lat, lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 15));

        key = intent.getStringExtra("key");
        if (key != null) {
            try {
                fetchNearbyPlaces(center, key);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

    }
    private void fetchNearbyPlaces(LatLng location, String keyword) throws UnsupportedEncodingException {
        Log.e("RECOMMAND_DEBUG", "fetchNearbyPlaces() 호출됨");
        Log.e("RECOMMAND_DEBUG", "keyword: " + keyword);

        String locStr = location.latitude + "," + location.longitude;
        int radius = 2000; // 2km
        String apiKey = "AIzaSyCtaPdLwKlF4vVjl25dEOeZJ4ziFMbn6Xs";

        Log.e("RECOMMAND_DEBUG", "위치: " + locStr + ", 반경: " + radius);

        PlacesAPI.apiService.getNearbyPlaces(
                locStr, radius, keyword, "restaurant", apiKey
        ).enqueue(new Callback<PlacesResponse>() {
            @Override
            public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {
                Log.e("RECOMMAND_DEBUG", "Places API 응답 도착");

                if (response.isSuccessful() && response.body() != null) {
                    List<PlaceResult> places = response.body().getResults();
                    Log.e("RECOMMAND_DEBUG", "검색된 장소 수: " + places.size());

                    if (places.isEmpty()) {
                        Log.e("RECOMMAND_DEBUG", "⚠️ 장소 결과가 비어 있음. keyword: " + keyword);
                    }

                    for (PlaceResult place : places) {
                        try {
                            double lat = place.getGeometry().getLocation().getLat();
                            double lng = place.getGeometry().getLocation().getLng();
                            String name = place.getName();
                            String vicinity = place.getVicinity();

                            Log.e("RECOMMAND_DEBUG", "📍 마커 추가: " + name + " (" + lat + ", " + lng + ")");

                            LatLng pos = new LatLng(lat, lng);
                            mMap.addMarker(new MarkerOptions()
                                    .position(pos)
                                    .title(name)
                                    .snippet(vicinity));

                        } catch (Exception e) {
                            Log.e("RECOMMAND_DEBUG", "❌ 마커 처리 중 오류", e);
                        }
                    }

                    adapter.setPlaceList(places);
                } else {
                    Log.e("RECOMMAND_DEBUG", "❌ Places API 응답 실패: " + response.code());
                    if (response.errorBody() != null) {
                        try {
                            Log.e("RECOMMAND_DEBUG", "응답 에러 내용: " + response.errorBody().string());
                        } catch (IOException e) {
                            Log.e("RECOMMAND_DEBUG", "에러 바디 읽기 실패", e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PlacesResponse> call, Throwable t) {
                Log.e("PlacesAPI", "API 호출 실패: " + t.getMessage());
            }
        });
    }

}
