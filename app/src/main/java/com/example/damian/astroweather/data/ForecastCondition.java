package com.example.damian.astroweather.data;

import org.json.JSONException;
import org.json.JSONObject;

public class ForecastCondition implements JSONPopulator{
    private int code;
    private String day;
    private String highest;
    private String lowest;

    public int getCode() {
        return code;
    }

    public String getDay() {
        return day;
    }

    public String getHighest() {
        return highest;
    }

    public String getLowest() {
        return lowest;
    }


    @Override
    public void populate(JSONObject data) {
        code = data.optInt("code");
        day = data.optString("day");
        highest = data.optString("high");
        lowest = data.optString("low");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject data = new JSONObject();

        try {
            data.put("code", code);
            data.put("day", day);
            data.put("high", highest);
            data.put("low", lowest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
}
