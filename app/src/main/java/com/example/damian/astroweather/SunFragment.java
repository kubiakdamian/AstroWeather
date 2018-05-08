package com.example.damian.astroweather;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SunFragment extends Fragment {
    View view;
    private TextView sunrise;
    private TextView sunriseAzimuth;
    private TextView sunset;
    private TextView sunsetAzimuth;
    private TextView twilight;
    private TextView dawn;

    private SunInfo sunInfo;

    public SunFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sun_fragment, container, false);
        init(view);
        sunInfo = new SunInfo();
        setData();
        return view;
    }

    private void init(View view){
        sunrise = view.findViewById(R.id.sunriseid);
        sunriseAzimuth = view.findViewById(R.id.sunriseazimuthid);
        sunset = view.findViewById(R.id.sunsetid);
        sunsetAzimuth = view.findViewById(R.id.sunsetazimuthid);
        twilight = view.findViewById(R.id.twilightid);
        dawn = view.findViewById(R.id.dawnid);
    }

    private void setData(){
        String temp;
        temp = String.valueOf(sunInfo.getSunrise());
        sunrise.setText("Sunrise:       " + temp.substring(0, temp.length() - 6));
        temp = String.valueOf(sunInfo.getAzimuthRise());
        sunriseAzimuth.setText("Azimuth:      " + temp);
        temp = String.valueOf(sunInfo.getSunset());
        sunset.setText("Sunset:       " + temp.substring(0, temp.length() - 6));
        temp = String.valueOf(sunInfo.getAzimuthSet());
        sunsetAzimuth.setText("Azimuth:      " + temp);
        temp = String.valueOf(sunInfo.getTwilightEvening());
        twilight.setText("Twilight:       " + temp.substring(0, temp.length() - 6));
        temp = String.valueOf(sunInfo.getTwilightMorning());
        dawn.setText("Dawn:       " + temp.substring(0, temp.length() - 6));

    }
}
