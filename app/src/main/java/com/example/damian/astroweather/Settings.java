package com.example.damian.astroweather;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.example.damian.astroweather.data.Favourites;
import com.example.damian.astroweather.service.WeatherCallback;
import com.example.damian.astroweather.service.YahooWeather;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class Settings extends AppCompatActivity implements WeatherCallback{

    private MoonInfo moonInfo;
    private SunInfo sunInfo;
    private YahooWeather yahooWeather;
    private Realm realm;
    private LocationSpinnerAdapter adapter;

    private Spinner refreshTimeSpinner;
    private Spinner unitsSpinner;
    private Spinner favouritesSpinner;
    private EditText longitude;
    private EditText latitude;
    private EditText newlocation;
    private Button buttonSave;
    private Button buttonRefresh;

    private List<String> names;
    private List<String> units;
    private List<Favourites> favouriteLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        names = new ArrayList<>();
        units = new ArrayList<>();
        favouriteLocations = new ArrayList<>();
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        yahooWeather = new YahooWeather(this);
        setRefreshNames();
        setUnitsNames();
        sunInfo = sunInfo.getSunInfoInstance();
        moonInfo = moonInfo.getMoonInfoInstance();

        setContentView(R.layout.activity_settings);
        init();
        initSpinner();
        initUnitsSpinner();
        initFavouritesSpinner();
    }

    void init() {
        longitude = findViewById(R.id.newlongitude);
        latitude = findViewById(R.id.newlatitude);
        newlocation = findViewById(R.id.newlocation);
        buttonSave = findViewById(R.id.buttonsave);
        buttonRefresh = findViewById(R.id.buttonrefresh);
        newlocation.setText(String.valueOf(YahooWeather.getLocation()));
        favouritesSpinner = findViewById(R.id.spinnerFavourites);
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
        unitsSpinner.setSelection(getIndex(unitsSpinner, YahooWeather.getUnit()));
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

    private int getIndex(Spinner spinner, String myString){
        int index = 0;

        for (int i=0; i < spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equals(myString)){
                index = i;
            }
        }
        return index;
    }

    void initFavouritesSpinner(){
        getFavouritesFromDatabase();
        adapter = new LocationSpinnerAdapter(this, R.layout.spinner_layout, favouriteLocations);
        favouritesSpinner.setAdapter(adapter);
        favouritesSpinner.setSelection(getIndex(favouritesSpinner, YahooWeather.getLocation()));
        favouritesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Favourites favouriteLocation = adapter.getItem(i);
                newlocation.setText(favouriteLocation.getLocation());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void getFavouritesFromDatabase(){
        RealmQuery query = realm.where(Favourites.class);
        RealmResults results = query.findAll();
        favouriteLocations = new ArrayList<>();

        for(int i=0; i < results.size(); i++){
            Favourites item = (Favourites) results.get(i);
            favouriteLocations.add(item);
        }
    }

    public void onSave(View view) {
        try {
            if(latitude.getText().length() <= 0 || longitude.getText().length() <= 0){
                YahooWeather.setLocation(newlocation.getText().toString());
                YahooWeather.setUnit(unitsSpinner.getSelectedItem().toString());
                yahooWeather.refreshWeather(newlocation.getText().toString());
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
            Toast.makeText(Settings.this, ParseException.getMessage(), Toast.LENGTH_SHORT).show();
            clearCoordinates();
        }
    }

    public void addLocation(View view){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        addNewLocation();
                        YahooWeather.setLocation(newlocation.getText().toString());
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to add \"" + newlocation.getText().toString()+ "\" ?. App will find most similar result").setPositiveButton("add", dialogClickListener)
                .setNegativeButton("quit", dialogClickListener).show();

    }

    void addNewLocation(){
        final Favourites favouriteLocation = new Favourites(newlocation.getText().toString());
        if(adapter.checkIfExists(favouriteLocation.getLocation())){
            Toast.makeText(this,"This location already exists in database",Toast.LENGTH_SHORT).show();
        }else {
            realm.beginTransaction();
            Favourites itemInDb = realm.createObject(Favourites.class);
            itemInDb.setLocation(favouriteLocation.getLocation());
            realm.commitTransaction();
            showToastAdded(favouriteLocation);

            initFavouritesSpinner();
        }
    }

    void showToastAdded(Favourites favouriteLocation){
        Toast.makeText(this, "Added Location " + favouriteLocation.getLocation(), Toast.LENGTH_SHORT).show();
    }

    public void onRefresh(View view){
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
//        Toast.makeText(Settings.this, "Forecast refreshed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void serviceFailure(Exception exception) {
//        Toast.makeText(Settings.this, "Couldn't refresh data", Toast.LENGTH_SHORT).show();
    }
}
