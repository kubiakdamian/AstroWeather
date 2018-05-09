package com.example.damian.astroweather;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.util.Calendar;

public class SunInfo {

    private AstroCalculator astroCalculator;
    private AstroCalculator.SunInfo sunInfo;
    private AstroCalculator.Location location;

    public SunInfo() {
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

        location = new AstroCalculator.Location(52, 21);
        astroCalculator = new AstroCalculator(astroDateTime, location);
        astroCalculator.setDateTime(astroDateTime);
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

}
