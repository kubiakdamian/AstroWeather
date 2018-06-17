package com.example.damian.astroweather.service;

import com.example.damian.astroweather.data.Channel;

public interface WeatherCallback {
    void serviceSuccess(Channel channel);
    void serviceFailure(Exception exception);
}
