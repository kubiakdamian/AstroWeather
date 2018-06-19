package com.example.damian.astroweather.data;

import io.realm.RealmObject;

public class Favourites extends RealmObject {
    private String location;

    public Favourites(String location) {
        this.location = location;
    }

    public Favourites(){}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString(){
        return location;
    }
}
