package com.example.damian.astroweather.data;

import org.json.JSONObject;

public class Units implements JSONPopulator {
    public String getTemperature() {
        return temperature;
    }

    private String temperature;
    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }
}
