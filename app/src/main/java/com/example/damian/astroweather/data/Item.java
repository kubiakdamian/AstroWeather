package com.example.damian.astroweather.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Item implements JSONPopulator {
    public Condition getCondition() {
        return condition;
    }

    private Condition condition;

    private ForecastCondition[] forecast;
    private JSONArray forecastData;
    private String latitude;
    private String longitude;

    public ForecastCondition[] getForecast() { return forecast; }

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

        forecastData = data.optJSONArray("forecast");
        forecast = new ForecastCondition[forecastData.length()];

        for (int i = 0; i < forecastData.length(); i++) {
            forecast[i] = new ForecastCondition();
            try {
                forecast[i].populate(forecastData.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public JSONObject toJSON(){
        JSONObject data = new JSONObject();
        try {
            data.put("lat", latitude);
            data.put("long", longitude);
            data.put("condition",condition.toJSON());
            JSONArray arr = new JSONArray();
            for(int i=0; i < forecast.length; i++){
                arr.put(forecast[i].toJSON());
            }
            data.put("forecast",arr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
