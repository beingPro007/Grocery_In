package com.example.onlinegroceryapp.model;

public class RecentlyViewed {
    String email;
    String prodId;
    String name;
    String Description;
    String price;
    String quantity;
    String unit;
    String imageUrl;

    public RecentlyViewed(String prodId, String name, String description, String price, String quantity, String unit, String imageUrl) {
        this.prodId = prodId;
        this.name = name;
        Description = description;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
        this.imageUrl = imageUrl;
    }

    public RecentlyViewed() {

    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public  String getImageurl() {
        return imageUrl;
    }

    public void setImageurl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
