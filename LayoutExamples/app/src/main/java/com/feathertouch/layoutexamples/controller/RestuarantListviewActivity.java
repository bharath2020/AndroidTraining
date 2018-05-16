package com.feathertouch.layoutexamples.controller;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.feathertouch.layoutexamples.R;
import com.feathertouch.layoutexamples.model.restuarant.Restaurant;
import com.feathertouch.layoutexamples.model.restuarant.RestaurantDataProvider;
import com.feathertouch.layoutexamples.model.restuarant.RestuarantDownloadCallback;
import com.feathertouch.layoutexamples.network.JSONDataDownloader;
import com.feathertouch.layoutexamples.model.restuarant.RestuarantsListDownloadTask;
import com.feathertouch.layoutexamples.services.LocationDetector;
import com.feathertouch.layoutexamples.services.LocationUpdateCallback;
import com.feathertouch.layoutexamples.services.LocationUpdatesReceiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RestuarantListviewActivity extends AppCompatActivity implements RestuarantDownloadCallback, LocationUpdateCallback {

    LinearLayoutManager mLayoutManager;
    RestuarantAdapter adapter;
    private RecyclerView restaurantListView;

    private static String TAG = "RestuarantListviewActivity";

    LocationUpdatesReceiver locationUpdatesReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        LocationDetector.INSTANCE.setContext(this);

        restaurantListView = findViewById(R.id.ListView);
        //restaurantListView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        restaurantListView.setLayoutManager(mLayoutManager);

       // specify an adapter (see also next example)
        adapter = new RestuarantAdapter(new RestaurantDataProvider().loadRestaurants());
        restaurantListView.setAdapter(adapter);
        restaurantListView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));


        Button downloadButton = (Button)findViewById(R.id.downloadButton);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dowloadNearestRestuarants(LocationDetector.INSTANCE.getCurrentLocation());
            }
        });


        Button updateLocationButton = (Button) findViewById(R.id.updateLocation);
        updateLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationDetector.INSTANCE.startListentingToCurrentLocation();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        checkForLocationPermission();

    }

    public  void registerCurrentLocationUpdate() {
        if ( locationUpdatesReceiver == null ) {
            locationUpdatesReceiver = new LocationUpdatesReceiver(this);
        }
        IntentFilter currentLocationFilter = new IntentFilter(LocationDetector.LISTEN_TO_CURRENT_LOCATION);

        LocalBroadcastManager.getInstance(this).registerReceiver(locationUpdatesReceiver, currentLocationFilter);
        LocationDetector.INSTANCE.startListentingToCurrentLocation();

    }

    public void dowloadNearestRestuarants(Location location) {


        String zomatoUrl = "https://developers.zomato.com/api/v2.1/search?lat=" +
                location.getLatitude()
                + "&lon=" +
                location.getLongitude() + "&radius=3000";

        Log.d(TAG, zomatoUrl);
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("user-key", "257eecf05629c1a77f23337fb8c83f80");


        JSONDataDownloader downloader = new JSONDataDownloader(zomatoUrl, headers);

        new RestuarantsListDownloadTask(RestuarantListviewActivity.this).execute(downloader);

    }

    @Override
    public void restuarantsInfoDownloaded(ArrayList<Restaurant> restaurants) {
        Log.d(TAG, restaurants.toString());
adapter.newRestuarantsFound(restaurants);


    }

    @Override
    public void newLocationFound(Location location) {
        dowloadNearestRestuarants(location);
    }

    private void checkForLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION }, 99);
        } else {
            registerCurrentLocationUpdate();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if( requestCode == 99 ) {
            registerCurrentLocationUpdate();
        }
    }
}
