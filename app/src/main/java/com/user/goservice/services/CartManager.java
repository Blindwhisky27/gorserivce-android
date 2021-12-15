package com.user.goservice.services;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.user.goservice.Database;
import com.user.goservice.GetDataFromDatabase;

import java.sql.ResultSet;
import java.util.ArrayList;

public class CartManager {
    public ArrayList<String> cartItems = new ArrayList<String>();

    public void addToCart(String serviceName, int price) {
        Database database = new Database();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String query = "INSERT INTO CART VALUES('" + serviceName + "'," + price + ",'" + uid + "');";
        database.setQuery(query, database.update);
        database.execute();
    }

    public void removeFromCart(String serviceName) {
        Database database = new Database();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String query = "DELETE from cart where uid='" + uid + "' and service='" + serviceName + "';";
        database.setQuery(query, database.update);
        database.execute();
    }

    public void getCartItems() {
        GetDataFromDatabase getCart = new GetDataFromDatabase();
        String query = "select service from cart where uid='" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "'";
        getCart.setQuery(query, getCart.retrieve);

        try {
            ResultSet resultSet = getCart.execute().get();
            while (resultSet.next()) {
                cartItems.add(resultSet.getString("service"));
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());

        }

    }
}
