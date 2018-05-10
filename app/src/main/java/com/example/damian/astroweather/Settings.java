package com.example.damian.astroweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.astrocalculator.AstroCalculator;


import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {

    private static final String LATITUDE = "Latitude";
    private static final String LONGITUDE = "Longitude";
    private static final String INCORECT_DATA = "Incorect Data";
    private static final String SAVE_CHANGES = "Changes saved";

    private MoonInfo moonInfo;
    private SunInfo sunInfo;
    private Spinner refreshTimeSpinner;
    private EditText longitude;
    private EditText latitude;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sunInfo = sunInfo.getSunInfoInstance();
        moonInfo = moonInfo.getMoonInfoInstance();
        setContentView(R.layout.activity_settings);
//        initSpinner();
        init();
    }

//    List<String> getIntervalNames() {
//        List<String> intervalNames = new ArrayList<>();
//        for (UpdateTimeIntervalValues value : UpdateTimeIntervalValues.values()) {
//            intervalNames.add(value.name());
//        }
//        return intervalNames;
//    }

    void init() {
        longitude = findViewById(R.id.newlongitude);
        latitude = findViewById(R.id.newlatitude);
        buttonSave = findViewById(R.id.buttonsave);
    }

//    private void setDefaultSpinner(){
//        if(astroWeatherConfig.getTimeInterval() == 5000){
//            refreshTimeSpinner.setSelection(0);
//        }else if(astroWeatherConfig.getTimeInterval() == 10000){
//            refreshTimeSpinner.setSelection(1);
//        }else if(astroWeatherConfig.getTimeInterval() == 30000){
//            refreshTimeSpinner.setSelection(2);
//        }else if(astroWeatherConfig.getTimeInterval() == 1000*60){
//            refreshTimeSpinner.setSelection(3);
//        }else if(astroWeatherConfig.getTimeInterval() == 15*1000*60){
//            refreshTimeSpinner.setSelection(4);
//        }
//    }
//
//    void initSpinner() {
//        refreshTimeSpinner = (Spinner) findViewById(R.id.refresh_time);
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getIntervalNames());
//        refreshTimeSpinner.setAdapter(adapter);
//        setDefaultSpinner();
//        refreshTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int id, long position) {
//
//                switch ((int) position) {
//                    case 0:
//                        astroWeatherConfig.setTimeInterval(5000);
//                        break;
//                    case 1:
//                        astroWeatherConfig.setTimeInterval(10000);
//                        break;
//                    case 2:
//                        astroWeatherConfig.setTimeInterval(30000);
//                        break;
//                    case 3:
//                        astroWeatherConfig.setTimeInterval(60000);
//                        break;
//                    case 4:
//                        astroWeatherConfig.setTimeInterval(900000);
//                        break;
//                }
//            }
//
//
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//                setDefaultSpinner();
//            }
//        });
//    }

    public void saveHandler(View view) {
        try {
            double mlatitude = Double.parseDouble(latitude.getText().toString());
            double mlongitude = Double.parseDouble(longitude.getText().toString());
            if ((mlatitude > 90 || mlatitude < -90) || (mlongitude < 0 || mlongitude > 180)) {
                throw new Exception();
            }
            sunInfo.setLocation(new AstroCalculator.Location(mlatitude, mlongitude));
            moonInfo.setLocation(new AstroCalculator.Location(mlatitude, mlongitude));
            Toast.makeText(Settings.this, SAVE_CHANGES, Toast.LENGTH_SHORT).show();
        } catch (Exception ParseException) {
            Toast.makeText(Settings.this, INCORECT_DATA, Toast.LENGTH_SHORT).show();
        }
    }
}
