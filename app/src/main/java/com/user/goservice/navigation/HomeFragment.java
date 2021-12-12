package com.user.goservice.navigation;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.auth.FirebaseAuth;
import com.makeramen.roundedimageview.RoundedImageView;
import com.user.goservice.GetDataFromDatabase;
import com.user.goservice.R;

import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private final Handler sliderHandler = new Handler();
    private ViewPager2 viewpager;
    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
        }
    };
    private TextView usernameTextView, vehicleTextView;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        viewpager = v.findViewById(R.id.viewpagerSlider);
        usernameTextView = v.findViewById(R.id.helloTextView);
        vehicleTextView = v.findViewById(R.id.vehicleTextView);

        imageSlider();
        getUserDetails();


        return v;
    }

    private void getUserDetails() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        GetDataFromDatabase retrieveData = new GetDataFromDatabase();
        String query = "SELECT users.username, vehicles.model, vehicles.regno\n" +
                "FROM users\n" +
                "INNER JOIN vehicles ON users.defaultVehicle=vehicles.vid " +
                "where users.uid='" + uid + "';";

        retrieveData.setQuery(query, retrieveData.retrieve);

        try {
            ResultSet resultSet = retrieveData.execute().get();

            while (resultSet != null && resultSet.next()) {
                String userName = resultSet.getString("username");
                String vehicleName = resultSet.getString("model");

                vehicleName = vehicleName.concat(" " + resultSet.getString("regno"));
                String hello = "Hello, ";
                usernameTextView.setText(String.format("%s%s", hello, userName));
                String vehicle = "Vehicle: ";
                vehicleTextView.setText(String.format("%s%s", vehicle, vehicleName));

            }

        } catch (Exception e) {
            Toast.makeText(getContext(), "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    private void imageSlider() {

        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.slide1));
        sliderItems.add(new SliderItem(R.drawable.slide2));
        sliderItems.add(new SliderItem(R.drawable.slide3));

        viewpager.setAdapter(new SliderAdapter(sliderItems, viewpager));

        viewpager.setClipToPadding(false);
        viewpager.setClipChildren(false);
        viewpager.setOffscreenPageLimit(3);
        viewpager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_ALWAYS);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
            }
        });
        viewpager.setPageTransformer(compositePageTransformer);
        viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 2500);
            }
        });
    }

}

class SliderItem {
    public int image;

    public SliderItem(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }
}

class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private final List<SliderItem> sliderItems;
    private final ViewPager2 viewpager;
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (sliderItems.size() > 6) {
                for (int i = 0; i <= 2; i++) {
                    sliderItems.remove(i);
                }
            }
            sliderItems.addAll(sliderItems);
            notifyDataSetChanged();
        }
    };

    public SliderAdapter(List<SliderItem> sliderItems, ViewPager2 viewpager) {
        this.sliderItems = sliderItems;
        this.viewpager = viewpager;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slider_items,
                        parent,
                        false
                )
        );


    }

    @Override
    public void onBindViewHolder(@NonNull final SliderViewHolder holder, final int position) {
        try {
            holder.setImageView(sliderItems.get(position));
            if (position == sliderItems.size() - 2) {
                viewpager.post(runnable);
            }
        } catch (OutOfMemoryError e) {
            System.runFinalization();
            Runtime.getRuntime().gc();
            System.gc();
        }

    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    static class SliderViewHolder extends RecyclerView.ViewHolder {
        private final RoundedImageView imageView;


        SliderViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageSlide);
        }

        void setImageView(SliderItem sliderItem) {
            try {
                imageView.setImageResource(sliderItem.getImage());
            } catch (OutOfMemoryError e) {
                System.runFinalization();
                Runtime.getRuntime().gc();
                System.gc();
            }

            //HomeFragment homeFragment = new HomeFragment();
            // Picasso.with(homeFragment.getContext()).load(sliderItem.image).into(imageView);
        }
    }

}

