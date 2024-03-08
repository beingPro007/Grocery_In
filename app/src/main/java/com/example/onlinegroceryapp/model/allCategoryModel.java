package com.example.onlinegroceryapp.model;

public class allCategoryModel{
    String imageUrl;
    String Type;


    public allCategoryModel(){

    }

    public allCategoryModel(String imageUrl, String Type) {
        this.imageUrl = imageUrl;
        this.Type = Type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = Type;
    }
}
