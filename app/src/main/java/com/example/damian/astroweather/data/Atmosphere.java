package com.example.damian.astroweather.data;

import org.json.JSONException;
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

    @Override
    public JSONObject toJSON(){
        JSONObject data = new JSONObject();

        try {
            data.put("humidity",humidity);
            data.put("pressure",pressure);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
}
