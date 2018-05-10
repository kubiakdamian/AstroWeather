package com.example.damian.astroweather;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class TimeFragment extends Fragment implements SunInfoCallback{

    private TextView timer;
    private TextView coordinates;
    private Handler mHandler;
    private SunInfo info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.time_fragment, container, false);
        info = SunInfo.getSunInfoInstance();
        info.registerForUpdates(this);
        init(view);
        setCoordinates();
        return view;
    }

    void init(View view) {
        timer = view.findViewById(R.id.time);
        this.mHandler = new Handler();
        this.mHandler.postDelayed(m_Runnable, 2000);
        coordinates = view.findViewById(R.id.coordinates);
    }

    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            timer.setText(dateFormat.format(Calendar.getInstance(TimeZone.getDefault()).getTime()));
            TimeFragment.this.mHandler.postDelayed(m_Runnable, 1000);
        }
    };

    private void setCoordinates(){
        coordinates.setText("Lat:" + String.valueOf(info.getLocation().getLatitude()) + " Lon: " + String.valueOf(info.getLocation().getLongitude()));
    }

    public void onSettingsUpdate() {
        setCoordinates();
    }

    public void onDestroy() {
        info.unregisterForUpdates(this);
        super.onDestroy();
    }
}
