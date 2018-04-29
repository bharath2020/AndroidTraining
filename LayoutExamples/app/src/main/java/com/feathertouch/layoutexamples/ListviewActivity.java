package com.feathertouch.layoutexamples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.feathertouch.layoutexamples.model.Restaurant;
import com.feathertouch.layoutexamples.model.RestaurantDataProvider;
import com.feathertouch.layoutexamples.network.JSONDataDownloader;
import com.feathertouch.layoutexamples.network.RestuarantsListDownloadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListviewActivity extends AppCompatActivity implements RestuarantDownloadCallback {

    LinearLayoutManager mLayoutManager;
    RestuarantAdapter adapter;
    private RecyclerView restaurantListView;

    private static String TAG = "ListviewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        restaurantListView = findViewById(R.id.restaurantListView);
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
                String zomatoUrl = "https://developers.zomato.com/api/v2.1/search?lat=17.3850&lon=78.4867&radius=3000";
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("user-key", "257eecf05629c1a77f23337fb8c83f80");


                JSONDataDownloader downloader = new JSONDataDownloader(zomatoUrl, headers);

                new RestuarantsListDownloadTask(ListviewActivity.this).execute(downloader);
            }
        });

    }

    @Override
    public void restuarantsInfoDownloaded(ArrayList<Restaurant> restaurants) {
        Log.d(TAG, restaurants.toString());
adapter.newRestuarantsFound(restaurants);


    }
}
