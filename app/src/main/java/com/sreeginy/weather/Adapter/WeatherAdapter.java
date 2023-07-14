package com.sreeginy.weather.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sreeginy.weather.Model.ForecastWeatherData;
import com.sreeginy.weather.R;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {


    private List<ForecastWeatherData> forecastDataList;

    public WeatherAdapter(List<ForecastWeatherData> forecastDataList) {
        this.forecastDataList = forecastDataList;
    }

    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, int position) {
        ForecastWeatherData forecastData = forecastDataList.get(position);

        holder.dateTextView.setText(forecastData.getDate());
        holder.temperatureTextView.setText(String.valueOf(forecastData.getHighTemp()));



    }

    @Override
    public int getItemCount() {
        return forecastDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView;
        public TextView temperatureTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            temperatureTextView = itemView.findViewById(R.id.temperatureTextView);
            // Initialize other views
        }
    }
}
