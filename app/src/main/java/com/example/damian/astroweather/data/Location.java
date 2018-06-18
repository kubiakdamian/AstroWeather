package com.example.damian.astroweather.data;

import org.json.JSONObject;

public class Location implements JSONPopulator {
    private String city;

    public String getCity() {
        return city;
    }

    @Override
    public void populate(JSONObject data) {
        city = data.optString("city");
    }

}

