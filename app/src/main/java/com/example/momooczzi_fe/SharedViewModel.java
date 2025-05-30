package com.example.momooczzi_fe;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SharedViewModel extends ViewModel {
    private boolean gender;
    private String emotion;
    private String happen;
    private final MutableLiveData<Double> latitude = new MutableLiveData<>();
    private final MutableLiveData<Double> longitude = new MutableLiveData<>();



    public boolean getGender() {
        return gender;
    }
    public void setGender(boolean gender) {
        this.gender = gender;
    }
    public String getEmotion() {
        return emotion;
    }
    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getHappen(){
        return happen;
    }
    public void setHappen(String happen){
        this.happen = happen;
    }

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