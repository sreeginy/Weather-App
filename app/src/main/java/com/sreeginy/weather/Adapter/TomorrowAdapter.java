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
import com.sreeginy.weather.Model.Tomorrow;
import com.sreeginy.weather.R;

import java.util.ArrayList;

public class TomorrowAdapter extends RecyclerView.Adapter<TomorrowAdapter.ViewHolder> {

    ArrayList<Tomorrow> items;
    Context context;

    public TomorrowAdapter(ArrayList<Tomorrow> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public TomorrowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_tommorow, parent,false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull TomorrowAdapter.ViewHolder holder, int position) {

        holder.dayTxt.setText(items.get(position).getDay());
        holder.statusTxt.setText(items.get(position).getStatus());
        holder.lowTxt.setText(String.valueOf(items.get(position).getLowTemp()));
        holder.highTxt.setText(String.valueOf(items.get(position).getHighTemp()));


        int drawableResourceId = holder.itemView.getResources()
                .getIdentifier(items.get(position).getPicPath(),"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.pic);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView dayTxt, statusTxt, lowTxt, highTxt;

        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dayTxt = itemView.findViewById(R.id.dayTxtV);
            statusTxt = itemView.findViewById(R.id.weatherType2);
            lowTxt = itemView.findViewById(R.id.lowTxV);
            highTxt = itemView.findViewById(R.id.highTxv);
            pic = itemView.findViewById(R.id.imageView);
        }
    }
}
