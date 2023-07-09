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
import com.sreeginy.weather.Model.Tommorow;
import com.sreeginy.weather.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TommorowAdapter extends RecyclerView.Adapter<TommorowAdapter.ViewHolder> {

    ArrayList<Tommorow> items;
    Context context;

    public TommorowAdapter(ArrayList<Tommorow> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public TommorowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_tommorow, parent,false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull TommorowAdapter.ViewHolder holder, int position) {

        holder.dayTxt.setText(items.get(position).getDay());
        holder.statusTxt.setText(items.get(position).getStatus());
        holder.lowTxt.setText(items.get(position).getLowTemp());
        holder.highTxt.setText(items.get(position).getHighTemp());

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
