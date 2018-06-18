package com.example.damian.astroweather;


import android.annotation.SuppressLint;
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

import com.example.damian.astroweather.data.Channel;
import com.example.damian.astroweather.data.Item;
import com.example.damian.astroweather.service.WeatherCallback;
import com.example.damian.astroweather.service.YahooWeather;

public class ForecastFragment extends Fragment implements WeatherCallback {
    View view;
    private YahooWeather yahooWeather;
    private TextView location;
    private TextView firstDay;
    private ImageView firstImg;
    private TextView firstTemp;
    private TextView secondDay;
    private ImageView secondImg;
    private TextView secondTemp;
    private TextView thirdDay;
    private ImageView thirdImg;
    private TextView thirdTemp;
    private TextView fourthDay;
    private ImageView fourthImg;
    private TextView fourthTemp;
    private TextView fifthDay;
    private ImageView fifthImg;
    private TextView fifthTemp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forecast_fragment, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        location = view.findViewById(R.id.location);
        firstDay = view.findViewById(R.id.firstDay);
        firstImg = view.findViewById(R.id.firstImg);
        firstTemp = view.findViewById(R.id.firstTemp);
        secondDay = view.findViewById(R.id.secondDay);
        secondImg = view.findViewById(R.id.secondImg);
        secondTemp = view.findViewById(R.id.secondTemp);
        thirdDay = view.findViewById(R.id.thirdDay);
        thirdImg = view.findViewById(R.id.thirdImg);
        thirdTemp = view.findViewById(R.id.thirdTemp);
        fourthDay = view.findViewById(R.id.fourthDay);
        fourthImg = view.findViewById(R.id.fourthImg);
        fourthTemp = view.findViewById(R.id.fourthTemp);
        fifthDay = view.findViewById(R.id.fifthDay);
        fifthImg = view.findViewById(R.id.fifthImg);
        fifthTemp = view.findViewById(R.id.fifthTemp);

        yahooWeather = new YahooWeather(this);
        yahooWeather.refreshWeather(YahooWeather.getLocation());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void serviceSuccess(Channel channel) {
        Item item = channel.getItem();

        firstImg.setImageDrawable(getWeatherIcon(item, 0));
        secondImg.setImageDrawable(getWeatherIcon(item, 1));
        thirdImg.setImageDrawable(getWeatherIcon(item, 2));
        fourthImg.setImageDrawable(getWeatherIcon(item, 3));
        fifthImg.setImageDrawable(getWeatherIcon(item, 4));

        location.setText(yahooWeather.getLocation());
        firstDay.setText(item.getForecast()[0].getDay());
        firstTemp.setText(item.getForecast()[0].getLowest() + "\u00B0" + channel.getUnits().getTemperature() + "/" + item.getForecast()[0].getHighest() + "\u00B0" + channel.getUnits().getTemperature());
        secondDay.setText(item.getForecast()[1].getDay());
        secondTemp.setText(item.getForecast()[1].getLowest() + "\u00B0" + channel.getUnits().getTemperature() + "/" + item.getForecast()[1].getHighest() + "\u00B0" + channel.getUnits().getTemperature());
        thirdDay.setText(item.getForecast()[2].getDay());
        thirdTemp.setText(item.getForecast()[2].getLowest() + "\u00B0" + channel.getUnits().getTemperature() + "/" + item.getForecast()[2].getHighest() + "\u00B0" + channel.getUnits().getTemperature());
        fourthDay.setText(item.getForecast()[3].getDay());
        fourthTemp.setText(item.getForecast()[3].getLowest() + "\u00B0" + channel.getUnits().getTemperature() + "/" + item.getForecast()[3].getHighest() + "\u00B0" + channel.getUnits().getTemperature());
        fifthDay.setText(item.getForecast()[4].getDay());
        fifthTemp.setText(item.getForecast()[4].getLowest() + "\u00B0" + channel.getUnits().getTemperature() + "/" + item.getForecast()[4].getHighest() + "\u00B0" + channel.getUnits().getTemperature());
    }

    @Override
    public void serviceFailure(Exception exception) {
        Activity activity = getActivity();
        Toast.makeText(activity, exception.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private Drawable getWeatherIcon(Item item, int number){
        int resourceId;
        resourceId = getResources().getIdentifier("drawable/icon_" + item.getForecast()[number].getCode(), null, getActivity().getPackageName());
        Drawable weatherIconDrawable = getResources().getDrawable(resourceId, null);

        return weatherIconDrawable;
    }
}
