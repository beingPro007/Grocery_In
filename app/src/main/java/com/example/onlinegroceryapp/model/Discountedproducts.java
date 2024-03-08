package com.example.onlinegroceryapp.model;

public class Discountedproducts {
    Integer id ;
    int imageurl;

    public Discountedproducts(Integer id, int imageurl) {
        this.id = id;
        this.imageurl = imageurl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImageurl() {

        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = Integer.parseInt(imageurl);
    }
}
