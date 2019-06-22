package com.example.piash.tourmate.Weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.piash.tourmate.R;
import com.example.piash.tourmate.Weather.data.Query;
import com.example.piash.tourmate.Weather.data.Weather;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Forecast> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        WeatherAPI.Factory.getInstance().getWeather().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                for (int i=0; i<5; i++)
                {
                    Query query = response.body().getQuery();
                    String date = query.getResults().getChannel().getItem().getForecast().get(i).getDate();
                    String maxTmp = query.getResults().getChannel().getItem().getForecast().get(i).getHigh();
                    String minTmp = query.getResults().getChannel().getItem().getForecast().get(i).getLow();
                    String sunRise = query.getResults().getChannel().getAstronomy().getSunrise();
                    String sunSet = query.getResults().getChannel().getAstronomy().getSunset();

                    arr.add(new Forecast(date,minTmp,maxTmp,sunRise,sunSet));
                }



                adapter = new ForecastAdapter(arr);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

                Log.e("Failed",t.getMessage());

            }
        });
}
}
