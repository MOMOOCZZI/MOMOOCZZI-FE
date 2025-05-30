package com.example.momooczzi_fe;

import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private Boolean gender;
    private String emotion;
    private String happen;
    private Double latitude;
    private Double longitude;

    public void setGender(Boolean g) { this.gender = g; }
    public void setEmotion(String e) { this.emotion = e; }
    public void setHappen(String h) { this.happen = h; }
    public void setLatitude(Double lat) { this.latitude = lat; }
    public void setLongitude(Double lon) { this.longitude = lon; }

    public Boolean getGender() { return gender; }
    public String getEmotion() { return emotion; }
    public String getHappen() { return happen; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
}
