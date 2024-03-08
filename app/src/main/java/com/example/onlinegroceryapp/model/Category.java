package com.example.onlinegroceryapp.model;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinegroceryapp.R;

public class Category {
    Integer id ;
    int imageurl;

    public Category(Integer id, int imageurl) {
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
