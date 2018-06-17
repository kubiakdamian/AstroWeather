package com.example.damian.astroweather.data;

import org.json.JSONObject;

public class Channel implements JSONPopulator {
    public Item getItem() {
        return item;
    }

    public Units getUnits() {
        return units;
    }

    public Atmosphere getAtmosphere() { return atmosphere; }

    public Wind getWind() { return wind; }

    private Item item;
    private Units units;
    private Atmosphere atmosphere;
    private Wind wind;
    @Override
    public void populate(JSONObject data) {
        units = new Units();
        units.populate(data.optJSONObject("units"));

        item = new Item();
        item.populate(data.optJSONObject("item"));

        atmosphere = new Atmosphere();
        atmosphere.populate(data.optJSONObject("atmosphere"));

        wind = new Wind();
        wind.populate(data.optJSONObject("wind"));
    }

}
