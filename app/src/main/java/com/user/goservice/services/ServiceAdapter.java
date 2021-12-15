package com.user.goservice.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.user.goservice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    private ArrayList<Service> service;
    private Context context;

    public ServiceAdapter(ArrayList<Service> service, Context context) {
        this.service = service;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.serivce_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ServiceAdapter.ViewHolder holder, int position) {
        holder.serviceName.setText(service.get(position).serviceName);
        String priceString="₹"+ service.get(position).price;
        holder.price.setText(priceString);
    }

    @Override
    public int getItemCount() {
        return service.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView serviceName, price;
        private android.widget.Button add, remove;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.serviceName);
            price = itemView.findViewById(R.id.priceTextView);
            add = itemView.findViewById(R.id.addItemButton);
            remove = itemView.findViewById(R.id.removeItemButton);

        }
    }
}