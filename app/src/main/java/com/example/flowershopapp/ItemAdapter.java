package com.example.flowershopapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {
    private final Activity context;
    private final ArrayList<Item> items;

    public ItemAdapter(Activity context, ArrayList<Item> items) {
        super(context, R.layout.list_item, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView name = rowView.findViewById(R.id.itemName);
        TextView price = rowView.findViewById(R.id.itemPrice);
        ImageView image = rowView.findViewById(R.id.itemImage);

        Item item = items.get(position);
        name.setText(item.getName());
        price.setText("Price: $" + item.getPrice());
        image.setImageResource(item.getImageResId());

        return rowView;
    }
}
