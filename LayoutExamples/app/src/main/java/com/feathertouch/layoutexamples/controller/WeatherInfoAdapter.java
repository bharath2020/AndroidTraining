package com.feathertouch.layoutexamples.controller;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feathertouch.layoutexamples.R;
import com.feathertouch.layoutexamples.model.weather.WeatherInfo;

import java.util.ArrayList;

public class WeatherInfoAdapter extends RecyclerView.Adapter<WeatherInfoAdapter.WeatherInfoViewHolder> {


    private ArrayList<WeatherInfo> weatherInfos;
    WeatherInfoAdapter(ArrayList<WeatherInfo> weatherInfos) {
        this.weatherInfos = weatherInfos;
    }




    public static class WeatherInfoViewHolder extends RecyclerView.ViewHolder {
        ViewGroup viewGroup;
        TextView weatherDescriptionLabel;
        TextView dateLabel;
        WeatherInfoViewHolder(ViewGroup t) {
            super(t);
            this.viewGroup = t;
            weatherDescriptionLabel = t.findViewById(R.id.titleView);
            dateLabel = t.findViewById(R.id.subTitle);

        }
    }

    @Override
    public int getItemCount() {
        return weatherInfos.size();
    }

    @NonNull
    @Override
    public WeatherInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_item, parent, false);
        return  new WeatherInfoViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherInfoViewHolder holder, int position) {
        holder.weatherDescriptionLabel.setText(weatherInfos.get(position).getDescription());
        holder.dateLabel.setText(weatherInfos.get(position).getDate());
    }


    public void newWeatherInfoFound(ArrayList<WeatherInfo> weatherInfos) {
        this.weatherInfos = weatherInfos;
        notifyDataSetChanged();
    }


}
