package com.example.firebasedemo.Model;

import java.util.List;

public class Combo {
    private String name;
    private String description;
    private double price;
    private String imageUrl;

    public Combo(String name, String description, double price, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl(){
        return imageUrl;
    }
}

