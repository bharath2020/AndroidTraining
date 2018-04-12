package com.feathertouch.layoutexamples;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feathertouch.layoutexamples.model.Restaurant;

import java.util.ArrayList;

public class RestuarantAdapter extends RecyclerView.Adapter<RestuarantAdapter.RestaurantViewHolder> {

    private ArrayList<Restaurant> restaurants;


    RestuarantAdapter(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_row_item, parent, false);
        return  new RestaurantViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        holder.titleView.setText(restaurants.get(position).getName());
        holder.reviewLabel.setText(restaurants.get(position).getLatestReview());
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        ViewGroup viewGroup;
        TextView titleView;
        TextView reviewLabel;
        RestaurantViewHolder(ViewGroup t) {
            super(t);
            this.viewGroup = t;
            titleView = t.findViewById(R.id.titleView);
            reviewLabel = t.findViewById(R.id.reviewLabel);

        }
    }


}


