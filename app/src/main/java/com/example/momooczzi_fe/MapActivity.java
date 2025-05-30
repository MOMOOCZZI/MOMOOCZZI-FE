package com.example.momooczzi_fe;

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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapBinding binding;
    private RecyclerView recyclerView;
    private PlaceAdapter adapter;


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

        // 서울 시청 예시 위치
        LatLng center = new LatLng(37.5665, 126.9780);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 15));

        String keyword = getIntent().getStringExtra("food_keyword");
        if (keyword != null) {
            fetchNearbyPlaces(center, keyword);
        }

    }
    private void fetchNearbyPlaces(LatLng location, String keyword) {
        String locStr = location.latitude + "," + location.longitude;
        int radius = 1000; // 1km
        String apiKey = "AIzaSyCtaPdLwKlF4vVjl25dEOeZJ4ziFMbn6Xs"; // 실제 API Key

        PlacesAPI.apiService.getNearbyPlaces(
                locStr, radius, keyword, "restaurant", apiKey
        ).enqueue(new Callback<PlacesResponse>() {
            @Override
            public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PlaceResult> places = response.body().getResults();
                    for (PlaceResult place : places) {
                        LatLng pos = new LatLng(
                                place.getGeometry().getLocation().getLat(),
                                place.getGeometry().getLocation().getLng()
                        );
                        mMap.addMarker(new MarkerOptions()
                                .position(pos)
                                .title(place.getName())
                                .snippet(place.getVicinity()));
                    }

                    adapter.setPlaceList(places);

                }
            }

            @Override
            public void onFailure(Call<PlacesResponse> call, Throwable t) {
                Log.e("PlacesAPI", "API 호출 실패: " + t.getMessage());
            }
        });
    }

}
