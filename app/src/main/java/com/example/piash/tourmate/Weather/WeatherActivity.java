package com.example.piash.tourmate.Weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piash.tourmate.R;
import com.example.piash.tourmate.Weather.data.Query;
import com.example.piash.tourmate.Weather.data.Weather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity implements WeatherAPI {

    TextView cityText;
    ImageView thumbnailIcon;
    TextView tempText;
    TextView windText;
    TextView updateText;
    TextView cloudText;
    TextView humidText;
    TextView riseText;
    TextView setText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_current);

        cityText = (TextView) findViewById(R.id.cityText);
        tempText = (TextView) findViewById(R.id.tempText);
        windText = (TextView) findViewById(R.id.windText);
        updateText = (TextView) findViewById(R.id.updateText);
        cloudText = (TextView) findViewById(R.id.cloudText);
        humidText = (TextView) findViewById(R.id.humidText);
        riseText = (TextView) findViewById(R.id.riseText);
        setText = (TextView) findViewById(R.id.setText);
        thumbnailIcon = (ImageView) findViewById(R.id.thumbnailIcon);


    }

    @Override
    protected void onResume() {
        super.onResume();

                WeatherAPI.Factory.getInstance().getWeather().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                Query query = response.body().getQuery();
                cityText.setText(query.getResults().getChannel().getLocation().getCity());
                updateText.setText(query.getResults().getChannel().getLastBuildDate());
                tempText.setText(query.getResults().getChannel().getItem().getCondition().getTemp());
                cloudText.setText(query.getResults().getChannel().getItem().getCondition().getText());
                riseText.setText(query.getResults().getChannel().getAstronomy().getSunrise());
                setText.setText(query.getResults().getChannel().getAstronomy().getSunset());
                humidText.setText(query.getResults().getChannel().getAtmosphere().getHumidity()+" %");
                windText.setText(query.getResults().getChannel().getWind().getSpeed()+ " km/h");

                switch (cloudText.getText().toString()){

                    case "Clear":
                        thumbnailIcon.setImageResource(R.mipmap.sun_sunny);
                        break;
                    case "Cloudy":
                        thumbnailIcon.setImageResource(R.mipmap.sun_cloud);
                        break;
                    case "Partly Cloudy":
                        thumbnailIcon.setImageResource(R.mipmap.sun_cloud);
                        break;
                    case "Foggy":
                        thumbnailIcon.setImageResource(R.mipmap.sun_cloud);
                        break;
                    case "Cold":
                        thumbnailIcon.setImageResource(R.mipmap.snowflake);
                        break;
                    case "Mixed Rain And Snow":
                        thumbnailIcon.setImageResource(R.mipmap.sun_rain);
                        break;
                    case "Rain":
                        thumbnailIcon.setImageResource(R.mipmap.sun_rain);
                        break;
                    default:
                        thumbnailIcon.setImageResource(R.mipmap.sun_sunny);

                }

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

                Log.e("Failed",t.getMessage());

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        WeatherAPI.Factory.getInstance().getWeather().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                Query query = response.body().getQuery();
                cityText.setText(query.getResults().getChannel().getLocation().getCity());
                updateText.setText(query.getResults().getChannel().getLastBuildDate());
                cloudText.setText(query.getResults().getChannel().getItem().getCondition().getText());
                riseText.setText(query.getResults().getChannel().getAstronomy().getSunrise());
                setText.setText(query.getResults().getChannel().getAstronomy().getSunset());
                humidText.setText(query.getResults().getChannel().getAtmosphere().getHumidity()+" %");
                windText.setText(query.getResults().getChannel().getWind().getSpeed()+ " km/h");


            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

                Log.e("Failed",t.getMessage());

            }
        });

        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.sample_menu,menu);
        return true;


    }

    public void refresh(MenuItem item) {
        WeatherAPI.Factory.getInstance().getWeather().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                Query query = response.body().getQuery();
                cityText.setText(query.getResults().getChannel().getLocation().getCity());
                updateText.setText(query.getResults().getChannel().getLastBuildDate());
                cloudText.setText(query.getResults().getChannel().getItem().getCondition().getText());
                riseText.setText(query.getResults().getChannel().getAstronomy().getSunrise());
                setText.setText(query.getResults().getChannel().getAstronomy().getSunset());
                humidText.setText(query.getResults().getChannel().getAtmosphere().getHumidity()+" %");
                windText.setText(query.getResults().getChannel().getWind().getSpeed()+ " km/h");

                Toast.makeText(WeatherActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

                Log.e("Failed",t.getMessage());

            }
        });

    }


    @Override
    public Call<Weather> getWeather() {
        return null;
    }


    public void forecast(MenuItem item) {
        startActivity(new Intent(WeatherActivity.this,ForecastActivity.class));
    }
}
