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
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.feathertouch.layoutexamples.R;
import com.feathertouch.layoutexamples.model.weather.WeatherDownloadTask;
import com.feathertouch.layoutexamples.model.weather.WeatherInfo;
import com.feathertouch.layoutexamples.model.weather.WeatherInfoDownloadCallback;
import com.feathertouch.layoutexamples.network.JSONDataDownloader;
import com.feathertouch.layoutexamples.services.LocationDetector;
import com.feathertouch.layoutexamples.services.LocationUpdateCallback;
import com.feathertouch.layoutexamples.services.LocationUpdatesReceiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WeatherInfoActivity extends AppCompatActivity implements LocationUpdateCallback,WeatherInfoDownloadCallback {
    private LocationUpdatesReceiver locationUpdatesReceiver;
    static String TAG = "WeatherInfoActivity";
    private RecyclerView weatherInfoView;
    LinearLayoutManager mLayoutManager;
    private WeatherInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppCompatButton downloadButton = (AppCompatButton)findViewById(R.id.downloadButton);
        downloadButton.setText("Download weather info");

        LocationDetector.INSTANCE.setContext(this);


        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeatherInfoActivity.this.dowloadWeatherInfo(LocationDetector.INSTANCE.getCurrentLocation());
            }
        });

        checkForLocationPermission();


        weatherInfoView = findViewById(R.id.ListView);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        weatherInfoView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        adapter = new WeatherInfoAdapter(new ArrayList<WeatherInfo>());
        weatherInfoView.setAdapter(adapter);
        weatherInfoView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));


    }


    public  void registerCurrentLocationUpdate() {
        if ( locationUpdatesReceiver == null ) {
            locationUpdatesReceiver = new LocationUpdatesReceiver(this);
        }
        IntentFilter currentLocationFilter = new IntentFilter(LocationDetector.LISTEN_TO_CURRENT_LOCATION);

        LocalBroadcastManager.getInstance(this).registerReceiver(locationUpdatesReceiver, currentLocationFilter);
        LocationDetector.INSTANCE.startListentingToCurrentLocation();

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

    @Override
    public void newLocationFound(Location location) {
        dowloadWeatherInfo(location);
    }

    public void dowloadWeatherInfo(Location location) {


        //http://samples.openweathermap.org/data/2.5/forecast?lat=35&lon=139&appid=b6907d289e10d714a6e88b30761fae22
        String weatherInfoUrl = "http://api.openweathermap.org/data/2.5/forecast?lat=" +
                location.getLatitude()
                + "&lon=" +
                location.getLongitude() + "&appid=ea8dff27d3d62f8b004ccda10b92de66\n";

        Log.d(TAG, weatherInfoUrl);
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");


        JSONDataDownloader downloader = new JSONDataDownloader(weatherInfoUrl, headers);

        new WeatherDownloadTask(WeatherInfoActivity.this).execute(downloader);

    }

    @Override
    public void weatherInfoDownloaded(ArrayList<WeatherInfo> weatherInfo) {

        Log.d(TAG, "" + weatherInfo);
        adapter.newWeatherInfoFound(weatherInfo);
    }
}
