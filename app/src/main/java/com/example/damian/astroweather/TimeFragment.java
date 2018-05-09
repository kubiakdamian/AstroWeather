package com.example.damian.astroweather;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class TimeFragment extends Fragment {

    private TextView timer;
    private Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.time_fragment, container, false);
        initTime(view);
        return view;
    }

    void initTime(View view) {
        timer = view.findViewById(R.id.time);
        this.mHandler = new Handler();
        this.mHandler.postDelayed(m_Runnable, 2000);
    }

    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            timer.setText(dateFormat.format(Calendar.getInstance(TimeZone.getDefault()).getTime()));
            TimeFragment.this.mHandler.postDelayed(m_Runnable, 1000);
        }
    };
}
