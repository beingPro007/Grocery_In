package com.example.onlinegroceryapp.model;

public class OrdersModel {
    private String prodId;
    private String productName;
    private String quantity;
    private String price;
    String imageUrl;  // Added field for image URL

    // Default constructor required for DataSnapshot.getValue(Orders.class)
    public OrdersModel() {
    }

    public OrdersModel(String prodId, String productName, String quantity, String price, String imageUrl) {
        this.prodId = prodId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProductName() {
        return productName;
    }


    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
