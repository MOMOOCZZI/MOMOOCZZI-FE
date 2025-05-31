package com.example.momooczzi_fe;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Boolean> gender = new MutableLiveData<>();
    private final MutableLiveData<String> emotion = new MutableLiveData<>();
    private final MutableLiveData<String> happen = new MutableLiveData<>();

    private final MutableLiveData<Double> latitude = new MutableLiveData<>();
    private final MutableLiveData<Double> longitude = new MutableLiveData<>();



    public LiveData<Boolean> getGender() {
        return gender;
    }

    public void setGender(boolean genderValue) {
        gender.setValue(genderValue);
    }

    public LiveData<String> getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotionValue) {
        emotion.setValue(emotionValue);
    }

    public LiveData<String> getHappen() {
        return happen;
    }

    public void setHappen(String happenValue) {
        happen.setValue(happenValue);
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
