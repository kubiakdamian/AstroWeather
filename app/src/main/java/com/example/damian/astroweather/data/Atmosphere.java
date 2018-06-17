package com.example.damian.astroweather.data;

import org.json.JSONObject;

public class Atmosphere implements JSONPopulator {
    private String humidity;
    private String pressure;

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    @Override
    public void populate(JSONObject data) {
        humidity = data.optString("humidity");
        pressure = data.optString("pressure");
    }
}
