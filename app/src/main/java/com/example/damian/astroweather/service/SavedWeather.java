package com.example.damian.astroweather.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.example.damian.astroweather.data.Channel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONObject;

public class SavedWeather {
    private Context context;
    private Exception error;
    private final String CACHED_WEATHER_FILE = "weather.data";
    private SavedWeather savedWeather;

    public SavedWeather(Context context){
        this.context = context;
    }

    @SuppressLint("StaticFieldLeak")
    public void save(Channel channel){
        new AsyncTask<Channel,Void, Void>(){

            @Override
            protected Void doInBackground(Channel... channels) {
                FileOutputStream outputStream;

                try {
                    outputStream = context.openFileOutput(CACHED_WEATHER_FILE,Context.MODE_PRIVATE);
                    outputStream.write(channels[0].toJSON().toString().getBytes());
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(channel);
    }
    @SuppressLint("StaticFieldLeak")
    public void load(final WeatherCallback listener){
        new AsyncTask<WeatherCallback,Void,Channel>(){
            private WeatherCallback weatherServiceCallback;
            @Override
            protected Channel doInBackground(WeatherCallback... weatherServiceCallbacks) {
                weatherServiceCallback = weatherServiceCallbacks[0];

                try {
                    FileInputStream inputStream = context.openFileInput(CACHED_WEATHER_FILE);
                    StringBuilder cache = new StringBuilder();
                    int content;
                    while((content = inputStream.read()) != -1){
                        cache.append((char) content);
                    }
                    inputStream.close();

                    JSONObject jsonCache = new JSONObject(cache.toString());
                    Channel channel = new Channel();
                    channel.populate(jsonCache);
                    return channel;
                } catch (FileNotFoundException e) {
                    error = new CacheException("File not found!");
                }catch (Exception e){
                    error = e;
                }
                return null;
            }
            @Override
            protected void onPostExecute(Channel channel){
                if(channel == null && error != null){
                    weatherServiceCallback.serviceFailure(error);
                }else {
                    weatherServiceCallback.serviceSuccess(channel);
                }
            }
        }.execute(listener);
    }

    private class CacheException extends Exception{
        CacheException(String detailMessage){
            super(detailMessage);
        }
    }
}
