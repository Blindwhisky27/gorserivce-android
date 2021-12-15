package com.user.goservice.services;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.goservice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ServicesActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    public static ArrayList<Service> brakeServicesList = new ArrayList<Service>();
    public static ArrayList<Service> electricServicesList = new ArrayList<Service>();
    public static ArrayList<Service> engineServicesList = new ArrayList<Service>();
    public static ArrayList<Service> gearServicesList = new ArrayList<Service>();
    public static ArrayList<Service> wheelsServicesList = new ArrayList<Service>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewpager2);

        setTabLayout();
        retrieveServicesFromDatabase();

    }

    private void retrieveServicesFromDatabase() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Services");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.child("breakservice").getChildren()) {
                    brakeServicesList.add(new Service(dataSnapshot.getKey(), Integer.parseInt(dataSnapshot.getValue().toString())));
                    System.out.println("SOUT:" + dataSnapshot.getKey() + " " + Integer.parseInt(dataSnapshot.getValue().toString()));
                }
                for (DataSnapshot dataSnapshot : snapshot.child("electricservice").getChildren()) {
                    electricServicesList.add(new Service(dataSnapshot.getKey(), Integer.parseInt(dataSnapshot.getValue().toString())));
                    System.out.println("SOUT:" + dataSnapshot.getKey() + " " + Integer.parseInt(dataSnapshot.getValue().toString()));
                }
                for (DataSnapshot dataSnapshot : snapshot.child("engineservice").getChildren()) {
                    engineServicesList.add(new Service(dataSnapshot.getKey(), Integer.parseInt(dataSnapshot.getValue().toString())));
                    System.out.println("SOUT:" + dataSnapshot.getKey() + " " + Integer.parseInt(dataSnapshot.getValue().toString()));
                }
                for (DataSnapshot dataSnapshot : snapshot.child("gearservice").getChildren()) {
                    gearServicesList.add(new Service(dataSnapshot.getKey(), Integer.parseInt(dataSnapshot.getValue().toString())));
                    System.out.println("SOUT:" + dataSnapshot.getKey() + " " + Integer.parseInt(dataSnapshot.getValue().toString()));
                }
                for (DataSnapshot dataSnapshot : snapshot.child("wheelservice").getChildren()) {
                    wheelsServicesList.add(new Service(dataSnapshot.getKey(), Integer.parseInt(dataSnapshot.getValue().toString())));
                    System.out.println("SOUT:" + dataSnapshot.getKey() + " " + Integer.parseInt(dataSnapshot.getValue().toString()));
                }


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    private void setTabLayout() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SectionsPagerAdapter fragmentStateAdapter = new SectionsPagerAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(fragmentStateAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Brakes"));
        tabLayout.addTab(tabLayout.newTab().setText("Electric"));
        tabLayout.addTab(tabLayout.newTab().setText("Engine"));
        tabLayout.addTab(tabLayout.newTab().setText("Gears"));
        tabLayout.addTab(tabLayout.newTab().setText("Wheels"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
}
















