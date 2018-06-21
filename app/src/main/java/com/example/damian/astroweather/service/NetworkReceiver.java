package com.example.damian.astroweather.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.damian.astroweather.NetworkConnection;

public class NetworkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(!checkInternet(context)){
            Toast.makeText(context,"Network is not available",Toast.LENGTH_LONG).show();
        }
    }

    boolean checkInternet(Context context){
        NetworkConnection serviceManager = new NetworkConnection(context);
        if(serviceManager.isOnline()){
            return true;
        }else {
            return false;
        }
    }
}
