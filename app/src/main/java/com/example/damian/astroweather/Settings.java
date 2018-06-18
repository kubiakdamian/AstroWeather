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
import com.example.damian.astroweather.data.Channel;
import com.example.damian.astroweather.service.WeatherCallback;
import com.example.damian.astroweather.service.YahooWeather;


import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity implements WeatherCallback{

    private MoonInfo moonInfo;
    private SunInfo sunInfo;
    private Spinner refreshTimeSpinner;
    private Spinner unitsSpinner;
    private EditText longitude;
    private EditText latitude;
    private EditText newlocation;
    private Button buttonSave;
    private Button buttonRefresh;
    private YahooWeather yahooWeather;
    private List<String> names;
    private List<String> units;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        names = new ArrayList<>();
        units = new ArrayList<>();
        setRefreshNames();
        setUnitsNames();
        sunInfo = sunInfo.getSunInfoInstance();
        moonInfo = moonInfo.getMoonInfoInstance();

        setContentView(R.layout.activity_settings);
        initSpinner();
        initUnitsSpinner();
        init();
    }

    void init() {
        longitude = findViewById(R.id.newlongitude);
        latitude = findViewById(R.id.newlatitude);
        newlocation = findViewById(R.id.newlocation);
        buttonSave = findViewById(R.id.buttonsave);
        buttonRefresh = findViewById(R.id.buttonrefresh);
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

    private void setUnitsSpinnerValues(){
        if(YahooWeather.getUnit() == "c"){
            refreshTimeSpinner.setSelection(0);
        }else if(YahooWeather.getUnit() == "f"){
            refreshTimeSpinner.setSelection(1);
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

    void initUnitsSpinner(){
        unitsSpinner = findViewById(R.id.spinnerUnits);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_layout, units);
        unitsSpinner.setAdapter(adapter);
        setUnitsSpinnerValues();

        unitsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int id, long position) {

                switch ((int) position) {
                    case 0:
                        YahooWeather.setUnit("c");
                        break;
                    case 1:
                        YahooWeather.setUnit("f");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                setUnitsSpinnerValues();
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

    public void onRefresh(View view){
        yahooWeather = new YahooWeather(this);
        yahooWeather.refreshWeather(YahooWeather.getLocation());

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

    private void setUnitsNames(){
        units.add("c");
        units.add("f");
    }

    @Override
    public void serviceSuccess(Channel channel) {
        Toast.makeText(Settings.this, "Forecast refreshed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void serviceFailure(Exception exception) {
        Toast.makeText(Settings.this, "Couldn't refresh data", Toast.LENGTH_SHORT).show();
    }
}
