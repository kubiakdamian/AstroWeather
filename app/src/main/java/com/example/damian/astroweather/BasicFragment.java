package com.example.damian.astroweather;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damian.astroweather.data.Atmosphere;
import com.example.damian.astroweather.data.Channel;
import com.example.damian.astroweather.data.Item;
import com.example.damian.astroweather.data.Wind;
import com.example.damian.astroweather.service.WeatherCallback;
import com.example.damian.astroweather.service.YahooWeather;

public class BasicFragment extends Fragment implements WeatherCallback {
    View view;
    private ImageView weatherIcon;
    private TextView temperature;
    private TextView condition;
    private TextView location;
    private TextView longitude;
    private TextView latitiude;
    private TextView humidity;
    private TextView pressure;
    private TextView speed;

    private YahooWeather yahooWeather;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.basic_fragment, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        weatherIcon = view.findViewById(R.id.weatherIconid);
        temperature = view.findViewById(R.id.temperatureid);
        condition = view.findViewById(R.id.conditionid);
        location = view.findViewById(R.id.locationid);
        longitude = view.findViewById(R.id.longid);
        latitiude = view.findViewById(R.id.latid);
        humidity = view.findViewById(R.id.humidityid);
        pressure = view.findViewById(R.id.pressureid);
        speed = view.findViewById(R.id.speedid);

        yahooWeather = new YahooWeather(this);
        yahooWeather.refreshWeather("Łódź, Poland");
    }

    @Override
    public void serviceSuccess(Channel channel) {
        Item item = channel.getItem();
        Atmosphere atmosphere = channel.getAtmosphere();
        Wind wind = channel.getWind();
        int resourceId = getResources().getIdentifier("drawable/icon_" + item.getCondition().getCode(), null, getActivity().getPackageName()); //icons: http://vclouds.deviantart.com/gallery/#/d2ynulp
        Drawable weatherIconDrawable = getResources().getDrawable(resourceId, null);
        weatherIcon.setImageDrawable(weatherIconDrawable);

        temperature.setText(item.getCondition().getTemperature() + "\u00B0" + channel.getUnits().getTemperature());
        condition.setText(item.getCondition().getDescription());
        location.setText(yahooWeather.getLocation());
        longitude.setText(item.getLongitude());
        latitiude.setText(item.getLatitude());
        humidity.setText(atmosphere.getHumidity());
        pressure.setText(atmosphere.getPressure());
        speed.setText(wind.getSpeed());
    }

    @Override
    public void serviceFailure(Exception exception) {
        Activity activity = getActivity();
        Toast.makeText(activity, exception.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
