package com.example.momooczzi_fe.network;

public class FoodItem {
    private String food;
    private String description;
    private String image_url;

    public FoodItem(String food, String description, String image_url) {
        this.food = food;
        this.description = description;
        this.image_url = image_url;
    }

    public String getFood() {
        return food;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return image_url;
    }
}
