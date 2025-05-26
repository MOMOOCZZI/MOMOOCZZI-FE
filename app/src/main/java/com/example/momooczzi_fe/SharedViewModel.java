package com.example.momooczzi_fe;

import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private boolean gender;
    private String emotion;
    private String weather;
    private String heapen;


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

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
    public String getHeapen(){
        return heapen;
    }
    public void setHeapen(String heapen){
        this.heapen = heapen;
    }
}
