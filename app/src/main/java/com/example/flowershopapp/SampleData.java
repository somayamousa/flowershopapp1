package com.example.flowershopapp;

import java.util.ArrayList;

public class SampleData {

    public static ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();

        items.add(new Item("Red Roses", 19.99, "A bouquet of 12 fresh red roses.", R.drawable.roses));
        items.add(new Item("Tulips", 15.49, "Colorful tulips perfect for spring.", R.drawable.tulips));
        items.add(new Item("Lilies", 22.99, "Elegant white lilies in full bloom.", R.drawable.lilies));
        items.add(new Item("Mixed Bouquet", 24.99, "A vibrant mix of seasonal flowers.", R.drawable.mixed_bouquet));

        return items;
    }
}
