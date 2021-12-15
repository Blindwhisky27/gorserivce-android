package com.user.goservice.services;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.user.goservice.R;

public class GeneralServiceActivity extends AppCompatActivity {
    private Button proceedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_service);
        proceedButton = findViewById(R.id.addToCartButton);

        CartManager cartManager = new CartManager();
        cartManager.getCartItems();
        proceedButton.setEnabled(!cartManager.cartItems.contains("General service"));


        proceedButton.setOnClickListener(view -> {
            proceedButton.setEnabled(false);
            cartManager.addToCart("General service", 2500);
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
        });
    }

}