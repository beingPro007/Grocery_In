package com.example.onlinegroceryapp.model;

public class SubCategoryModel {
    String imageUrl;
    String ProductName;
    String quantity;
    String price;

    public  SubCategoryModel(){

    }

    public SubCategoryModel(String imageUrl, String productName, String quantity, String price) {
        this.imageUrl = imageUrl;
        ProductName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
