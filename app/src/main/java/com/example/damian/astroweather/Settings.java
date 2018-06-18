package com.example.damian.astroweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.astrocalculator.AstroCalculator;
import com.example.damian.astroweather.service.YahooWeather;


import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {

    private MoonInfo moonInfo;
    private SunInfo sunInfo;
    private Spinner refreshTimeSpinner;
    private EditText longitude;
    private EditText latitude;
    private EditText newlocation;
    private Button buttonSave;
    private List<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        names = new ArrayList<>();
        setRefreshNames();
        sunInfo = sunInfo.getSunInfoInstance();
        moonInfo = moonInfo.getMoonInfoInstance();

        setContentView(R.layout.activity_settings);
        initSpinner();
        init();
    }

    void init() {
        longitude = findViewById(R.id.newlongitude);
        latitude = findViewById(R.id.newlatitude);
        newlocation = findViewById(R.id.newlocation);
        buttonSave = findViewById(R.id.buttonsave);
        newlocation.setText(String.valueOf(YahooWeather.getLocation()));
    }

    private void setSpinnerValues(){
        if(sunInfo.getTimeInterval() == 5000){
            refreshTimeSpinner.setSelection(0);
        }else if(sunInfo.getTimeInterval() == 10000){
            refreshTimeSpinner.setSelection(1);
        }else if(sunInfo.getTimeInterval() == 15000){
            refreshTimeSpinner.setSelection(2);
        }
    }

    void initSpinner() {
        refreshTimeSpinner = findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_layout, names);
        refreshTimeSpinner.setAdapter(adapter);
        setSpinnerValues();
        refreshTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int id, long position) {

                switch ((int) position) {
                    case 0:
                        sunInfo.setTimeInterval(5000);
                        moonInfo.setTimeInterval(5000);
                        break;
                    case 1:
                        sunInfo.setTimeInterval(10000);
                        moonInfo.setTimeInterval(10000);
                        break;
                    case 2:
                        sunInfo.setTimeInterval(15000);
                        moonInfo.setTimeInterval(15000);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                setSpinnerValues();
            }
        });
    }

    public void onSave(View view) {
        try {
            if(latitude.getText().length() <= 0 || longitude.getText().length() <= 0){
                YahooWeather.setLocation(newlocation.getText().toString());
                Toast.makeText(Settings.this, "Saved", Toast.LENGTH_SHORT).show();
            }else {
                double mlatitude = Double.parseDouble(latitude.getText().toString());
                double mlongitude = Double.parseDouble(longitude.getText().toString());
                if ((mlatitude > 90 || mlatitude < -90) || (mlongitude < -180 || mlongitude > 180)) {
                    throw new Exception();
                }
                sunInfo.setLocation(new AstroCalculator.Location(mlatitude, mlongitude));
                moonInfo.setLocation(new AstroCalculator.Location(mlatitude, mlongitude));
                Toast.makeText(Settings.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ParseException) {
            Toast.makeText(Settings.this, "Wrong location", Toast.LENGTH_SHORT).show();
            clearCoordinates();
        }
    }

    private void clearCoordinates(){
        longitude.setText("");
        latitude.setText("");
    }


    private void setRefreshNames(){
        names.add("5s");
        names.add("10s");
        names.add("15s");
    }
}
