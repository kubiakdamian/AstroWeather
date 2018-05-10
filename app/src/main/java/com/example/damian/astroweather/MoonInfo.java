package com.example.damian.astroweather;

import android.os.Handler;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class MoonInfo {

    private static final MoonInfo moonInfoInstance = new MoonInfo();
    private AstroCalculator astroCalculator;
    private AstroCalculator.Location location;
    private AstroCalculator.MoonInfo moonInfo;
    private Runnable updateInfo;
    private Set<MoonInfoCallback> subscribers = new HashSet<>();
    private long timeInterval = 10000;

    final Handler handler = new Handler();

    public void setLocation(AstroCalculator.Location location) {
        this.location = location;
    }

    public static MoonInfo getMoonInfoInstance() {
        return moonInfoInstance;
    }

    public MoonInfo() {
        location = new AstroCalculator.Location(52, 21);
        astroCalculator = new AstroCalculator(setTime(), location);
        astroCalculator.setDateTime(setTime());
        astroCalculator.setLocation(location);
        moonInfo = astroCalculator.getMoonInfo();

        updateInfo = new Runnable() {
            @Override
            public void run() {
                update();
                notifySubscribers();
                handler.postDelayed(this, timeInterval);
            }
        };
        handler.post(updateInfo);
    }

    public double getAge() {
        return moonInfo.getAge();
    }

    public double getIllumination() {
        return moonInfo.getIllumination();
    }

    public AstroDateTime getMoonrise() {
        return moonInfo.getMoonrise();
    }

    public AstroDateTime getMoonset() {
        return moonInfo.getMoonset();
    }

    public AstroDateTime getFullMoon() {
        return moonInfo.getNextFullMoon();
    }

    public AstroDateTime getNewMoon() {
        return moonInfo.getNextNewMoon();
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

        return astroDateTime;
    }

    public void update() {
        astroCalculator = new AstroCalculator(setTime(), location);
        astroCalculator.setDateTime(setTime());
        astroCalculator.setLocation(location);
        moonInfo = astroCalculator.getMoonInfo();
    }

    public void registerForUpdates(MoonInfoCallback subscriber) {
        subscribers.add(subscriber);
    }

    public void unregisterForUpdates(MoonInfoCallback subscriber) {
        subscribers.remove(subscriber);
    }

    void notifySubscribers() {
        for (MoonInfoCallback subscriber : subscribers) {
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
