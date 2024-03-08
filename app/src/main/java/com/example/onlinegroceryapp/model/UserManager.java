package com.example.onlinegroceryapp.model;

public class UserManager {
    public static String userEmail;
    private static String prodName;

    String Type;

    int total;

    public static String getProdName() {
        return prodName;
    }

    public static void setProdName(String prodName) {
        UserManager.prodName = prodName;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static void setUserEmail(String email) {
        userEmail = email;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }
}
