package com.example.damian.astroweather.service;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.damian.astroweather.data.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class YahooWeather {
    private WeatherCallback weatherCallback;
    private static String location = "Lodz";
    private static String unit = "c";
    private Exception error;

    public YahooWeather(WeatherCallback weatherCallback) {
        this.weatherCallback = weatherCallback;
    }

    @SuppressLint("StaticFieldLeak")
    public void refreshWeather(String loc){
        this.location = loc;
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='" + unit + "'", location);

                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

                try {
                    URL url = new URL(endpoint);
                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null){
                        result.append(line);
                    }

                    return result.toString();
                } catch (Exception e) {
                    error = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s){
                if(s == null && error != null){
                    weatherCallback.serviceFailure(error);
                    return;
                }

                try {
                    JSONObject data = new JSONObject(s);

                    JSONObject queryResults = data.optJSONObject("query");
                    int count = queryResults.optInt("count");
                    if(count == 0){
                        weatherCallback.serviceFailure(new LocationWatherEcception("No weather information found for " + location));
                        return;
                    }

                    Channel channel = new Channel();
                    channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));

                    weatherCallback.serviceSuccess(channel);
                } catch (JSONException e) {
                    weatherCallback.serviceFailure(e);
                }
            }
        }.execute(location);
    }

    public class LocationWatherEcception extends Exception{
        public LocationWatherEcception(String message) {
            super(message);
        }
    }

    public static String getUnit() { return unit; }
    public static void setUnit(String unit) { YahooWeather.unit = unit; }
    public static void setLocation(String newLocation) {
        location = newLocation;
    }
    public static String getLocation() {
        return location;
    }
}
