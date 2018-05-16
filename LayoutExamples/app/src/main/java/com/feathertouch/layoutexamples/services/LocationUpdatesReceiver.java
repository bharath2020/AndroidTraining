package com.feathertouch.layoutexamples.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import java.util.ArrayList;

public class LocationUpdatesReceiver extends BroadcastReceiver {

    private LocationUpdateCallback listener;
    public LocationUpdatesReceiver(LocationUpdateCallback callback) {
        this.listener = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ArrayList<Location> locations = intent.getParcelableArrayListExtra(LocationDetector.CURRENT_LOCATION);
        if ( locations.size() >= 1 ){
            Location currentLocation = locations.get(0);
            this.listener.newLocationFound(currentLocation);
        }
    }
}
