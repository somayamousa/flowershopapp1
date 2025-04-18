package com.example.flowershopapp;

import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private double price;
    private String quantity;
    private int imageResId; // Drawable resource ID for images

    public Item(String name, double price, String quantity, int imageResId) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getQuantity() { return quantity; }
    public int getImageResId() { return imageResId; }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }@Override
    public String toString() {
        return name + " - $" + price + " x" + quantity;
    }

}