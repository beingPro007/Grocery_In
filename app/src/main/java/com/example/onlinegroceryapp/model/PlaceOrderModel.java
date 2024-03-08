package com.example.onlinegroceryapp.model;

public class PlaceOrderModel {
    String name ;
    String phone;
    String Address;
    String email;

    public PlaceOrderModel(){

    }

    public PlaceOrderModel(String name, String phone, String address, String email) {
        this.name = name;
        this.phone = phone;
        Address = address;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
