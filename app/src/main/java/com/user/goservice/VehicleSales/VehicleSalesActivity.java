package com.user.goservice.VehicleSales;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.user.goservice.Database.GetDataFromDatabase;
import com.user.goservice.R;

import java.sql.ResultSet;
import java.util.ArrayList;

public class VehicleSalesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    ArrayList<VehicleSales> vehicleSales = new ArrayList<>();
    VehicleSaleAdapter vehicleSaleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicel_sales);
        recyclerView = findViewById(R.id.vehicleSalesRecyclerView);
        getVehiclesSaleData();

        vehicleSaleAdapter.setOnItemClickListener(position -> {
            startActivity(new Intent(getApplicationContext(),
                    SalesPageActivity.class)
                    .putExtra("registrationNo", vehicleSales.get(position).registrationNo));
        });

    }

    private void getVehiclesSaleData() {

        GetDataFromDatabase getData = new GetDataFromDatabase();
        String query = "SELECT * from vehiclesales;";

        getData.setQuery(query);
        try {
            ResultSet resultSet = getData.execute().get();
            while (resultSet.next()) {
                VehicleSales vehicleSale = new VehicleSales(resultSet.getString("model"),
                        resultSet.getString("price"), resultSet.getString("manufactureDate"),
                        resultSet.getString("registrationNo"), resultSet.getString("mile"),
                        resultSet.getString("condi"), resultSet.getString("url"),
                        resultSet.getString("description"));
                vehicleSales.add(vehicleSale);

            }
        } catch (Exception e) {
            Log.e("Error", "VSA: " + e.getLocalizedMessage());
        }

        vehicleSaleAdapter = new VehicleSaleAdapter(vehicleSales);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(vehicleSaleAdapter);

    }
}