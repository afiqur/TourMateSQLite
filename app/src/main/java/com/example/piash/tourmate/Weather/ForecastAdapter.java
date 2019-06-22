package com.example.piash.tourmate.Weather;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.piash.tourmate.R;

import java.util.ArrayList;

/**
 * Created by PIASH on 01-May-17.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.RecyclerViewHolder>{
    ArrayList<Forecast> arrayList = new ArrayList<>();
    public ForecastAdapter(ArrayList<Forecast> arrayList){
        this.arrayList = arrayList;

    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_weather_row_layout,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        holder.dateTV.setText(arrayList.get(position).getDate());
        holder.maxTxt.setText(arrayList.get(position).getMaxTemp());
        holder.minTt.setText(arrayList.get(position).getMinTemp());
        holder.sunRTxt.setText(arrayList.get(position).getSunRise());
        holder.sunStxt.setText(arrayList.get(position).getSunSet());

        return;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static  class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView minTt,maxTxt,sunRTxt,sunStxt,dateTV;
        public RecyclerViewHolder(View v) {
            super(v);
            dateTV = (TextView)v.findViewById(R.id.dateTV);
            minTt = (TextView)v.findViewById(R.id.minTempTV);
            maxTxt = (TextView)v.findViewById(R.id.maxTempTV);
            sunRTxt = (TextView)v.findViewById(R.id.sunriseTV);
            sunStxt = (TextView)v.findViewById(R.id.sunsetTV);

        }
    }

}
