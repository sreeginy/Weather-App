package com.sreeginy.weather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sreeginy.weather.Model.ForecastWeatherData;
import com.sreeginy.weather.R;

import java.util.ArrayList;


    public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

        private ArrayList<ForecastWeatherData> items;
        private Context context;

        public ForecastAdapter(ArrayList<ForecastWeatherData> items) {
            this.items = items;
        }

        public void setItems(ArrayList<ForecastWeatherData> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_tommorow, parent, false);
            context = parent.getContext();
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ForecastWeatherData forecastData = items.get(position);

            holder.dayTxtV.setText(forecastData.getDay());
            holder.weatherType2.setText(forecastData.getWeatherType());
            holder.highTxv.setText(String.valueOf(forecastData.getHighTemp()));
            holder.lowTxV.setText(String.valueOf(forecastData.getLowTemp()));

            // Load the icon using a library like Glide or Picasso
            Glide.with(context)
                    .load(forecastData.getIcon())
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView dayTxtV, weatherType2, lowTxV, highTxv;
            ImageView imageView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                dayTxtV = itemView.findViewById(R.id.dayTxtV);
                weatherType2 = itemView.findViewById(R.id.weatherType2);
                lowTxV = itemView.findViewById(R.id.lowTxV);
                highTxv = itemView.findViewById(R.id.highTxv);
                imageView = itemView.findViewById(R.id.imageView);
            }
        }
    }
