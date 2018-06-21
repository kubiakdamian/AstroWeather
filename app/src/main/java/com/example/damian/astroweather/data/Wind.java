package com.example.damian.astroweather.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Wind implements JSONPopulator {
    private String chill;
    private String speed;

    public String getChill() {
        return chill;
    }

    public String getSpeed() {
        return speed;
    }

    @Override
    public void populate(JSONObject data) {
        chill = data.optString("chill");
        speed = data.optString("speed");
    }

    @Override
    public JSONObject toJSON(){
        JSONObject data = new JSONObject();

        try {
            data.put("chill",chill);
            data.put("speed",speed);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
