package com.example.damian.astroweather.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Condition implements JSONPopulator {
    public String getCode() {
        return code;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    private String code;
    private String temperature;
    private String description;


    @Override
    public void populate(JSONObject data) {
        code = data.optString("code");
        temperature = data.optString("temp");
        description = data.optString("text");
    }

    @Override
    public JSONObject toJSON(){
        JSONObject data = new JSONObject();

        try {
            data.put("code",code);
            data.put("temp",temperature);
            data.put("text",description);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
}
