package com.example.momooczzi_fe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocationViewModel extends ViewModel {
    private final MutableLiveData<Double> latitude = new MutableLiveData<>();
    private final MutableLiveData<Double> longitude = new MutableLiveData<>();

    public void setLocation(double lat, double lng) {
        latitude.setValue(lat);
        longitude.setValue(lng);
    }

    public LiveData<Double> getLatitude() {
        return latitude;
    }

    public LiveData<Double> getLongitude() {
        return longitude;
    }
}
