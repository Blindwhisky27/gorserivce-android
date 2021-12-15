package com.user.goservice.Navigation;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.user.goservice.R;

import org.jetbrains.annotations.NotNull;

public class NavigationActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(onItemSelectedListener);
        String fragment = "";
        if (getIntent().getStringExtra("Fragment") != null)
            fragment = getIntent().getStringExtra("Fragment");
        Fragment selectedFragment = new HomeFragment();
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        switch (fragment) {

            case "Cart":
                selectedFragment = new CartFragment();
                bottomNavigationView.setSelectedItemId(R.id.nav_cart);
                break;
            case "Orders":
                selectedFragment = new OrdersFragment();
                bottomNavigationView.setSelectedItemId(R.id.nav_cart);
                break;
            case "Account":
                selectedFragment = new AccountFragment();
                bottomNavigationView.setSelectedItemId(R.id.nav_account);
                break;

        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit();
    }

    private final NavigationBarView.OnItemSelectedListener onItemSelectedListener
            = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_cart:
                    selectedFragment = new CartFragment();
                    break;
                case R.id.nav_orders:
                    selectedFragment = new OrdersFragment();
                    break;
                case R.id.nav_account:
                    selectedFragment = new AccountFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };
}