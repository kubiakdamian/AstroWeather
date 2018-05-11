package com.example.damian.astroweather;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MoonFragment extends Fragment implements MoonInfoCallback {
    View view;
    private TextView moonrise;
    private TextView moonset;
    private TextView newmoon;
    private TextView fullmoon;
    private TextView moonphase;
    private TextView age;
    private MoonInfo moonInfo;
    public MoonFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        moonInfo = new MoonInfo();
        moonInfo = MoonInfo.getMoonInfoInstance();
        moonInfo.registerForUpdates(this);
        view = inflater.inflate(R.layout.moon_fragment, container, false);
        init(view);
        setData();
        return view;
    }

    private void init(View view){
        moonrise = view.findViewById(R.id.moonriseid);
        moonset = view.findViewById(R.id.moonsetid);
        newmoon = view.findViewById(R.id.newmoonid);
        fullmoon = view.findViewById(R.id.fullmoonid);
        moonphase = view.findViewById(R.id.moonphaseid);
        age = view.findViewById(R.id.dayofmonthid);
    }

    private void setData(){
        String temp;
//        moonInfo.getMoonrise().setTimezoneOffset(2);
//        moonInfo.getMoonset().setTimezoneOffset(2);
        temp = String.valueOf(moonInfo.getMoonrise());
        moonrise.setText(temp.substring(0, temp.length() - 6));
        temp = String.valueOf(moonInfo.getMoonset());
        moonset.setText(temp.substring(0, temp.length() - 6));
        temp = String.valueOf(moonInfo.getNewMoon());
        newmoon.setText(temp.substring(0, temp.length() - 6));
        temp = String.valueOf(moonInfo.getFullMoon());
        fullmoon.setText(temp.substring(0, temp.length() - 6));
        moonphase.setText(String.valueOf(Math.ceil((moonInfo.getIllumination() * 100))) + "%");
        age.setText(String.valueOf((int) moonInfo.getAge()));
    }

    @Override
    public void onSettingsUpdate() {
        setData();
    }

    public void onDestroy() {
        moonInfo.unregisterForUpdates(this);
        super.onDestroy();
    }
}
