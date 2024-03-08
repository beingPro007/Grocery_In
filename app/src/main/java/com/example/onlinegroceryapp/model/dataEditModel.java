package com.example.onlinegroceryapp.model;

public class dataEditModel {
    String imageUrl;
    String name;
    String phoneNo;
    String Address;

    public dataEditModel(){

    }

    public dataEditModel(String imageUrl, String name, String phoneNo, String address) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.phoneNo = phoneNo;
        Address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
