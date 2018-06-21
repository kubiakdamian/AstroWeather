package com.example.damian.astroweather.data;

import org.json.JSONException;
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

    @Override
    public JSONObject toJSON(){
        JSONObject data = new JSONObject();

        try {
            data.put("temperature",temperature);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
