package com.example.onlinegroceryapp.model;

import android.net.Uri;

public class adminPanelModel {
    Uri imageUrl;
    String prodType;


    public adminPanelModel(Uri imageUrl, String prodType) {
        this.imageUrl = imageUrl;
        this.prodType = prodType;
    }

    public Uri getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Uri imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }
}
