package com.user.goservice.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.user.goservice.MainActivity;
import com.user.goservice.R;

import org.jetbrains.annotations.NotNull;

public class AccountFragment extends Fragment {
    private TextView logoutTextView, myorderstextview, profileTextview, MyVehicleTextView;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        logoutTextView = v.findViewById(R.id.logoutTextview);
        myorderstextview = v.findViewById(R.id.myOrdersTextview);
        profileTextview = v.findViewById(R.id.profileTextview);
        MyVehicleTextView = v.findViewById(R.id.MyVehicleTextView);

        logoutTextView.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(), MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        });
        myorderstextview.setOnClickListener(view -> startActivity(new Intent(getContext(), NavigationActivity.class)
                .putExtra("Fragment", "Orders")));

        profileTextview.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), ProfileActivity.class));
        });
        MyVehicleTextView.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), VehicleActivity.class));
        });

        return v;

    }
}
