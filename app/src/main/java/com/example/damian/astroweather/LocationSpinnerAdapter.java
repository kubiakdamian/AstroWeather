package com.example.damian.astroweather;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.damian.astroweather.data.Favourites;

import java.util.List;


public class LocationSpinnerAdapter extends ArrayAdapter<Favourites> {
    private Context context;
    private Favourites[] values;

    public LocationSpinnerAdapter(Context context, int textViewResourceId, List<Favourites> values){
        super(context,textViewResourceId,values);

        this.context = context;
        this.values = values.toArray(new Favourites[values.size()]);

    }

    @Override
    public int getCount(){
        return values.length;
    }


    @Override
    public Favourites getItem(int position){
        return values[position];
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TextView label = (TextView)super.getView(position,convertView,parent);
        label.setTextColor(Color.BLACK);

        label.setText(values[position].getLocation());

        return label;
    }

    @Override
    public View getDropDownView(int position,View convertView,ViewGroup parent){
        TextView label =(TextView)super.getDropDownView(position,convertView,parent);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].getLocation());

        return label;
    }

    public boolean checkIfExists(String text){
        for(Favourites val : values){
            if(val.getLocation().toUpperCase().equals(text.toUpperCase())){
                return true;
            }
        }
        return false;
    }


}
