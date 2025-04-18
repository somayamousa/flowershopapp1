package com.example.flowershopapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    TextView nameText, priceText, quantityText;
    ImageView itemImage;
    Button addToCartBtn, backHomeBtn;
    Item currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        nameText = findViewById(R.id.detailName);
        priceText = findViewById(R.id.detailPrice);
        quantityText = findViewById(R.id.detailQuantity);
        itemImage = findViewById(R.id.detailImage);
        addToCartBtn = findViewById(R.id.btnAddToCart);
        backHomeBtn = findViewById(R.id.btnBackHome);

        currentItem = (Item) getIntent().getSerializableExtra("item");

        if (currentItem != null) {
            nameText.setText(currentItem.getName());
            priceText.setText("Price: $" + currentItem.getPrice());
            quantityText.setText("Quantity: " + currentItem.getQuantity());
            itemImage.setImageResource(currentItem.getImageResId());
        }

        addToCartBtn.setOnClickListener(v -> {
            saveItemToCart(currentItem);

            // ➕ بعد الحفظ، الانتقال إلى CartActivity
            Intent intent = new Intent(DetailActivity.this, CartActivity.class);
            startActivity(intent);
        });

        backHomeBtn.setOnClickListener(v -> {
            // العودة إلى LoginActivity مع إعادة عرض المنتجات
            Intent intent = new Intent(DetailActivity.this, IntroductoryActivity.class);
            intent.putExtra("showProducts", true);  // إضافة Extra لتمرير الإشارة إلى عرض المنتجات
            startActivity(intent);
            finish();  // لإنهاء الـ Activity الحالية
        });
    }

    private void saveItemToCart(Item item) {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String data = prefs.getString("cart_items", "");
        String entry = item.getName() + "," + item.getPrice() + "," + item.getQuantity() + "," +
                getResources().getResourceEntryName(item.getImageResId());
        prefs.edit().putString("cart_items", data + entry + "|").apply();
    }
}
