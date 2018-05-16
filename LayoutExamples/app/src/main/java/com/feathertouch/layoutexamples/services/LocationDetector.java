package com.feathertouch.layoutexamples.services;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class LocationDetector implements LocationListener {

    public static LocationDetector INSTANCE = new LocationDetector();
    private Context context;
    public static String LISTEN_TO_CURRENT_LOCATION = "LISTEN_TO_CURRENT_LOCATION";
    public static String CURRENT_LOCATION = "CURRENT_LOCATION";
    FusedLocationProviderClient client;

    private Location currentLocation = new Location("No Location");

    private LocationDetector() {
        Location hyderabad = new Location("Default Location");
        hyderabad.setLatitude(17.3850);
        hyderabad.setLongitude(78.4867);
        this.currentLocation = hyderabad;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Location getCurrentLocation() {
       return currentLocation;
    }

    public  void startListentingToCurrentLocation() {

        client = LocationServices.getFusedLocationProviderClient(context);
        try {
            Task<Location> getLocationTask = client.getLastLocation();
            getLocationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    LocationDetector.this.currentLocation = location;
                    broadcastCurrentLocation(location);
                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }



    }


    private void broadcastCurrentLocation(Location location) {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
        Intent newLocationIntent = new Intent(LISTEN_TO_CURRENT_LOCATION);
        ArrayList<Location> currentLocationArray = new ArrayList<>();
        currentLocationArray.add(location);
        newLocationIntent.putParcelableArrayListExtra(CURRENT_LOCATION, currentLocationArray);

        broadcastManager.sendBroadcast(newLocationIntent);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.currentLocation = location;
        broadcastCurrentLocation(location);
        Log.d("LocationDetector", location.toString());


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
