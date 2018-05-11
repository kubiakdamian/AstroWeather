package com.example.damian.astroweather;

import android.os.Handler;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class SunInfo {

    private static final SunInfo sunInfoInstance = new SunInfo();
    private Runnable updateAstro;
    private AstroCalculator astroCalculator;
    private AstroCalculator.SunInfo sunInfo;
    private AstroCalculator.Location location;
    private Set<SunInfoCallback> subscribers = new HashSet<>();
    private long timeInterval = 10000;

    final Handler handler = new Handler();

    public static SunInfo getSunInfoInstance() {
        return sunInfoInstance;
    }

    public void setLocation(AstroCalculator.Location location) {
        this.location = location;
    }

    public SunInfo() {
        location = new AstroCalculator.Location(52, 21);
        astroCalculator = new AstroCalculator(setTime(), location);
        astroCalculator.setDateTime(setTime());
        astroCalculator.setLocation(location);
        sunInfo = astroCalculator.getSunInfo();

        updateAstro = new Runnable() {
            @Override
            public void run() {
                update();
                notifySubscribers();
                handler.postDelayed(this, timeInterval);
            }
        };
        handler.post(updateAstro);
    }

    public void update() {
        astroCalculator = new AstroCalculator(setTime(), location);
        astroCalculator.setDateTime(setTime());
        astroCalculator.setLocation(location);
        sunInfo = astroCalculator.getSunInfo();
    }

    public double getAzimuthRise() {
        return sunInfo.getAzimuthRise();
    }

    public double getAzimuthSet() {
        return sunInfo.getAzimuthSet();
    }

    public AstroDateTime getSunrise() {
        return sunInfo.getSunrise();
    }

    public AstroDateTime getSunset() {
        return sunInfo.getSunset();
    }

    public AstroDateTime getTwilightEvening() {
        return sunInfo.getTwilightEvening();
    }

    public AstroDateTime getTwilightMorning() {
        return sunInfo.getTwilightMorning();
    }

    public AstroCalculator.Location getLocation() {
        return location;
    }

    private AstroDateTime setTime(){
        Calendar calendar = Calendar.getInstance();
        AstroDateTime astroDateTime = new AstroDateTime();
        astroDateTime.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        astroDateTime.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        astroDateTime.setMinute(calendar.get(Calendar.MINUTE));
        astroDateTime.setMonth(calendar.get(Calendar.MONTH) + 1);
        astroDateTime.setSecond(calendar.get(Calendar.SECOND));
        astroDateTime.setYear(calendar.get(Calendar.YEAR));
        astroDateTime.setTimezoneOffset((calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET)) / (3600 * 1000));
        astroDateTime.setDaylightSaving(false);
        astroDateTime.setTimezoneOffset(2);

        return astroDateTime;
    }

    public void registerForUpdates(SunInfoCallback subscriber) {
        subscribers.add(subscriber);
    }

    public void unregisterForUpdates(SunInfoCallback subscriber) {
        subscribers.remove(subscriber);
    }

    void notifySubscribers() {
        for (SunInfoCallback subscriber : subscribers) {
            subscriber.onSettingsUpdate();
        }
    }

    public void setTimeInterval(long timeInterval) {
        this.timeInterval = timeInterval;
        update();
        notifySubscribers();
    }

    public long getTimeInterval(){
        return this.timeInterval;
    }

}
