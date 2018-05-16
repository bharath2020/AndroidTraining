package com.feathertouch.layoutexamples.model.restuarant;

import java.util.ArrayList;
import java.util.IllegalFormatException;

public class Restaurant {
    public String name;
    public String latestReview;

    public Restaurant(String name, String latestReview) {
        this.name = name;
        this.latestReview = latestReview;
    }

    public String getName() {
        return name;
    }

    public String getLatestReview() {
        return latestReview;
    }

    public static ArrayList<Restaurant> jsonToRestaurants(String json) throws IllegalFormatException {
        return null;
    }
}
