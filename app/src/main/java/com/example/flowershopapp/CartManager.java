package com.example.flowershopapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class CartManager {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String CART_KEY = "cart_items";

    public static void addToCart(Context context, Item item) {
        ArrayList<Item> cart = getCart(context);
        cart.add(item);
        saveCart(context, cart);
    }

    public static ArrayList<Item> getCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String data = prefs.getString(CART_KEY, "");
        ArrayList<Item> cart = new ArrayList<>();

        if (!data.isEmpty()) {
            String[] items = data.split("\\|");
            for (String entry : items) {
                String[] parts = entry.split(",");
                if (parts.length == 4) {
                    String name = parts[0];
                    double price;
                    int imageResId;
                    String quantity = parts[2];

                    try {
                        price = Double.parseDouble(parts[1]);
                        imageResId = context.getResources().getIdentifier(parts[3], "drawable", context.getPackageName());
                        cart.add(new Item(name, price, quantity, imageResId));
                    } catch (Exception e) {
                        e.printStackTrace(); // Log and skip faulty item
                    }
                }
            }
        }

        return cart;
    }

    public static void saveCart(Context context, ArrayList<Item> cart) {
        StringBuilder builder = new StringBuilder();
        for (Item item : cart) {
            builder.append(item.getName()).append(",")
                    .append(item.getPrice()).append(",")
                    .append(item.getQuantity()).append(",")
                    .append(context.getResources().getResourceEntryName(item.getImageResId()))
                    .append("|");
        }
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(CART_KEY, builder.toString()).apply();
    }

    public static void clearCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(CART_KEY).apply();
    }
}
