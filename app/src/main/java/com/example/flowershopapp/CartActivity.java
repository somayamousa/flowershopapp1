package com.example.flowershopapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ListView cartListView;
    Button checkoutBtn, backHomeBtn;
    TextView cartTotal;
    ArrayList<Item> cartItems;
    ItemAdapter adapter;

    Switch switchDiscount, switchTax, switchShowDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView = findViewById(R.id.cartListView);
        checkoutBtn = findViewById(R.id.checkoutBtn);
        cartTotal = findViewById(R.id.cartTotal);

        switchDiscount = findViewById(R.id.switchDiscount);
        switchTax = findViewById(R.id.switchTax);
        switchShowDetails = findViewById(R.id.switchShowDetails);

        cartItems = getCart();
        adapter = new ItemAdapter(this, cartItems);
        cartListView.setAdapter(adapter);
        backHomeBtn = findViewById(R.id.btnBackHome);
        backHomeBtn.setOnClickListener(v -> {
            // العودة إلى LoginActivity مع إعادة عرض المنتجات
            Intent intent = new Intent(CartActivity.this, IntroductoryActivity.class);
            intent.putExtra("showProducts", true);  // إضافة Extra لتمرير الإشارة إلى عرض المنتجات
            startActivity(intent);
            finish();  // لإنهاء الـ Activity الحالية
        });
        updateTotal(); // Show initial total

        checkoutBtn.setOnClickListener(v -> {
            double total = 0;
            StringBuilder summary = new StringBuilder("Items:\n");

            for (Item item : cartItems) {
                summary.append("- ").append(item.getName())
                        .append(" ($").append(item.getPrice()).append(")\n");
                total += item.getPrice();
            }

            double originalTotal = total;

            if (switchDiscount.isChecked()) {
                total *= 0.9; // خصم 10%
            }

            if (switchTax.isChecked()) {
                total *= 1.15; // إضافة ضريبة 15%
            }

            summary.append("\nOriginal Total: $").append(String.format("%.2f", originalTotal));

            if (switchDiscount.isChecked())
                summary.append("\n10% Discount Applied");

            if (switchTax.isChecked())
                summary.append("\n15% Tax Included");

            summary.append("\nFinal Total: $").append(String.format("%.2f", total));

            if (switchShowDetails.isChecked()) {
                Toast.makeText(CartActivity.this, summary.toString(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(CartActivity.this, "Final Total: $" + String.format("%.2f", total), Toast.LENGTH_SHORT).show();
            }

            startActivity(new Intent(CartActivity.this, BillingActivity.class));
        });

        cartListView.setOnItemClickListener((parent, view, position, id) -> {
            new androidx.appcompat.app.AlertDialog.Builder(CartActivity.this)
                    .setTitle("Delete an item")
                    .setMessage("Do you want to delete this item?")
                    .setPositiveButton("yes", (dialog, which) -> {
                        cartItems.remove(position);
                        CartManager.saveCart(CartActivity.this, cartItems);
                        adapter.notifyDataSetChanged();
                        updateTotal();
                    })
                    .setNegativeButton("no", null)
                    .show();
        });

        // Update total when switches change
        switchDiscount.setOnCheckedChangeListener((buttonView, isChecked) -> updateTotal());
        switchTax.setOnCheckedChangeListener((buttonView, isChecked) -> updateTotal());
    }

    private void updateTotal() {
        double total = 0;
        for (Item item : cartItems) {
            total += item.getPrice();
        }

        if (switchDiscount.isChecked()) {
            total *= 0.9;
        }

        if (switchTax.isChecked()) {
            total *= 1.15;
        }

        cartTotal.setText("Total: $" + String.format("%.2f", total));
    }

    private ArrayList<Item> getCart() {
        ArrayList<Item> cart = new ArrayList<>();
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String data = prefs.getString("cart_items", "");
        if (!data.isEmpty()) {
            String[] items = data.split("\\|");
            for (String entry : items) {
                String[] parts = entry.split(",");
                if (parts.length == 4) {
                    String name = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    String quantity = parts[2];
                    int resId = getResources().getIdentifier(parts[3], "drawable", getPackageName());
                    cart.add(new Item(name, price, quantity, resId));
                }
            }
        }
        return cart;
    }
}
