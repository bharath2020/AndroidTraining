package com.feathertouch.layoutexamples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.feathertouch.layoutexamples.model.RestaurantDataProvider;

public class ListviewActivity extends AppCompatActivity {

    LinearLayoutManager mLayoutManager;
    RestuarantAdapter adapter;
    private RecyclerView restaurantListView;

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

    }


}
