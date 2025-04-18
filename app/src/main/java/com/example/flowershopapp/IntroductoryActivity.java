package com.example.flowershopapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class IntroductoryActivity extends AppCompatActivity {

    LinearLayout listLayout;
    ListView listView;
    Spinner spinnerType;
    RadioGroup radioGroupPrice;
    Button addToCartBtn;
    ArrayList<Item> items;
    ArrayList<Item> filteredItems;

    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);
        addToCartBtn = findViewById(R.id.btnAddToCart);
        listLayout = findViewById(R.id.listLayout);
        listView = findViewById(R.id.listView);
        spinnerType = findViewById(R.id.spinnerType);
        radioGroupPrice = findViewById(R.id.radioGroupPrice);
        addToCartBtn.setOnClickListener(v -> {


            // ➕ بعد الحفظ، الانتقال إلى CartActivity
            Intent intent = new Intent(IntroductoryActivity.this, CartActivity.class);
            startActivity(intent);
        });

        showProducts();
    }

    private void showProducts() {
        listLayout.setVisibility(View.VISIBLE);

        items = new ArrayList<>();
        items.add(new Item("Red Roses", 19.99, "A bouquet of 12 fresh red roses.", R.drawable.roses));
        items.add(new Item("Tulips", 15.49, "Colorful tulips perfect for spring.", R.drawable.tulips));
        items.add(new Item("Lilies", 22.99, "Elegant white lilies in full bloom.", R.drawable.lilies));
        items.add(new Item("Mixed Bouquet", 24.99, "A vibrant mix of seasonal flowers.", R.drawable.mixed_bouquet));
        items.add(new Item("Sunflowers", 17.99, "Bright and cheerful sunflowers to brighten your day.", R.drawable.sunflowers));
        items.add(new Item("Orchids", 29.99, "Exotic and long-lasting orchid flowers.", R.drawable.orchids));
        items.add(new Item("Daisies", 12.99, "Simple and sweet white daisies bouquet.", R.drawable.daisies));
        items.add(new Item("Peonies", 26.49, "Soft and romantic peonies in pink shades.", R.drawable.peonies));


        filteredItems = new ArrayList<>(items);

        adapter = new ItemAdapter(this, filteredItems);
        listView.setAdapter(adapter);

        setupSpinner();
        setupRadioGroup();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent detail = new Intent(IntroductoryActivity.this, DetailActivity.class);
            detail.putExtra("item", filteredItems.get(position));
            startActivity(detail);
        });
    }

    private void setupSpinner() {
        ArrayList<String> types = new ArrayList<>();
        types.add("All");
        for (Item item : items) {
            if (!types.contains(item.getName())) {
                types.add(item.getName());
            }
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(spinnerAdapter);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupRadioGroup() {
        radioGroupPrice.setOnCheckedChangeListener((group, checkedId) -> applyFilter());
    }

    private void applyFilter() {
        String selectedType = spinnerType.getSelectedItem().toString();
        int selectedPriceId = radioGroupPrice.getCheckedRadioButtonId();

        filteredItems.clear();

        for (Item item : items) {
            boolean matchesType = selectedType.equals("All") || item.getName().equals(selectedType);
            boolean matchesPrice = true;

            if (selectedPriceId == R.id.radioUnder20) {
                matchesPrice = item.getPrice() < 20;
            } else if (selectedPriceId == R.id.radioAbove20) {
                matchesPrice = item.getPrice() >= 20;
            }

            if (matchesType && matchesPrice) {
                filteredItems.add(item);
            }
        }

        adapter.notifyDataSetChanged();
    }
}
