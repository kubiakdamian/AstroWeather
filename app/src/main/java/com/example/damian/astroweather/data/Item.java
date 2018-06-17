package com.example.damian.astroweather.data;

import org.json.JSONObject;

public class Item implements JSONPopulator {
    public Condition getCondition() {
        return condition;
    }

    private Condition condition;
    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }
}
