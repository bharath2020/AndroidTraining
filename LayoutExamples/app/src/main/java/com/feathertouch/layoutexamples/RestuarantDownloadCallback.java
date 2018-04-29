package com.feathertouch.layoutexamples;

import com.feathertouch.layoutexamples.model.Restaurant;

import java.util.ArrayList;

public interface RestuarantDownloadCallback {

    public void restuarantsInfoDownloaded(ArrayList<Restaurant> restaurants);
}
