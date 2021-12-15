package com.user.goservice.Booking;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.user.goservice.Database.GetDataFromDatabase;
import com.user.goservice.R;
import com.user.goservice.Services.CartManager;
import com.user.goservice.Services.Service;

import java.sql.ResultSet;
import java.util.ArrayList;

public class BookingActivity extends AppCompatActivity {
    private TextView vehicleNameTextView, vehicleNoTextView, serviceCostTextView, serviceCountTextView, totalTextView;
    private ArrayList<Service> cartItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        vehicleNameTextView = findViewById(R.id.vehicleNameTextView);
        vehicleNoTextView = findViewById(R.id.vehicleNoTextView);
        serviceCostTextView = findViewById(R.id.serviceCostTextView);
        serviceCountTextView = findViewById(R.id.serviceCountTextView);
        totalTextView = findViewById(R.id.totalTextView);


        String vehicleName, vehicleNumber;
        vehicleName = getIntent().getStringExtra("vehicle");
        vehicleNumber = getIntent().getStringExtra("vehicleNumber");
        vehicleNameTextView.setText(vehicleName);
        vehicleNoTextView.setText(vehicleNumber);

        CartManager cartManager = new CartManager();
        cartManager.getCartItems();
        int totalCost = 0;
        for (Service service : cartItems) {
            totalCost = totalCost + service.price;
        }
        serviceCostTextView.setText(String.valueOf(totalCost));
        serviceCountTextView.setText(String.valueOf(cartItems.size()));
        totalTextView.setText(String.valueOf(totalCost));

    }

    public void getCartItems() {
        GetDataFromDatabase getCart = new GetDataFromDatabase();
        String query = "select * from cart where uid='" + FirebaseAuth.getInstance().getUid() + "'";
        getCart.setQuery(query, getCart.retrieve);

        try {
            ResultSet resultSet = getCart.execute().get();
            while (resultSet.next()) {
                cartItems.add(new Service(resultSet.getString("service"),
                        Integer.parseInt(resultSet.getString("price"))));


            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());

        }

    }
}