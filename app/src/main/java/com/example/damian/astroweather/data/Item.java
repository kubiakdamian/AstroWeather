package com.example.damian.astroweather.data;

import org.json.JSONObject;

public class Item implements JSONPopulator {
    public Condition getCondition() {
        return condition;
    }

    private Condition condition;
    private String latitude;
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    @Override

    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
        latitude = data.optString("lat");
        longitude = data.optString("long");
    }
}
