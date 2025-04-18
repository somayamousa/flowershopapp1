package com.example.flowershopapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class BillingActivity extends AppCompatActivity {

    TextView billSummary;
    EditText inputName, inputAddress, inputPhone;
    Button btnConfirmOrder, backHomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        billSummary = findViewById(R.id.billSummary);
        inputName = findViewById(R.id.inputName);
        inputAddress = findViewById(R.id.inputAddress);
        inputPhone = findViewById(R.id.inputPhone);
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);
        backHomeBtn = findViewById(R.id.btnBackHome);
        backHomeBtn.setOnClickListener(v -> {
            // العودة إلى LoginActivity مع إعادة عرض المنتجات
            Intent intent = new Intent(
                    BillingActivity.this, IntroductoryActivity.class);
            intent.putExtra("showProducts", true);  // إضافة Extra لتمرير الإشارة إلى عرض المنتجات
            startActivity(intent);
            finish();  // لإنهاء الـ Activity الحالية
        });
        ArrayList<Item> cartItems = getCart();
        double total = 0;
        StringBuilder summary = new StringBuilder("Items:\n");

        for (Item item : cartItems) {
            summary.append("- ").append(item.getName())
                    .append(" ($").append(item.getPrice()).append(")\n");
            total += item.getPrice();
        }

        summary.append("\nTotal: $").append(String.format("%.2f", total));
        billSummary.setText(summary.toString());

        btnConfirmOrder.setOnClickListener(v -> {
            if (inputName.getText().toString().trim().isEmpty()
                    || inputAddress.getText().toString().trim().isEmpty()
                    || inputPhone.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Order Confirmed! Thank you.", Toast.LENGTH_LONG).show();
            getSharedPreferences("MyPrefs", MODE_PRIVATE).edit().remove("cart_items").apply();
            finish();
        });
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
