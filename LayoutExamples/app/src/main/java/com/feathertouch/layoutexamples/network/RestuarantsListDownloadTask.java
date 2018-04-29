package com.feathertouch.layoutexamples.network;

import android.os.AsyncTask;
import android.util.Log;

import com.feathertouch.layoutexamples.RestuarantDownloadCallback;
import com.feathertouch.layoutexamples.model.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RestuarantsListDownloadTask extends AsyncTask<JSONDataDownloader,Void,ArrayList<Restaurant>> {

    private static String TAG = "RestuarantsListDownloadTask";
    private  RestuarantDownloadCallback restuarantDownloadListener;
    public RestuarantsListDownloadTask(RestuarantDownloadCallback restuarantDownloadListener) {
        this.restuarantDownloadListener = restuarantDownloadListener;
    }


    @Override
    protected ArrayList<Restaurant> doInBackground(JSONDataDownloader... downloaders) {

        JSONDataDownloader downloader = downloaders[0];
        ArrayList<Restaurant> restaurants = null;
        try {
            String responseData = downloader.download();
            restaurants = getRestuarants(responseData);
            Log.d(TAG, responseData);

        } catch (Exception e) {
             Log.d(TAG, e.toString());
        }
        return restaurants;
    }

    @Override
    protected void onPostExecute(ArrayList<Restaurant> results) {

        super.onPostExecute(results);
        restuarantDownloadListener.restuarantsInfoDownloaded(results);
    }


    private ArrayList<Restaurant> getRestuarants(String responseData) throws JSONException {
        JSONObject restuarantJSON = new JSONObject(responseData);

        JSONArray restuarants = restuarantJSON.getJSONArray("restaurants");
        ArrayList<Restaurant> allRestuarants = new ArrayList<>();
        for( int index=0; index < restuarants.length(); index++ ) {
            JSONObject restuarantRootObject = restuarants.getJSONObject(index);
            JSONObject restuarantInfo = restuarantRootObject.getJSONObject("restaurant");
            String name = restuarantInfo.getString("name");
            String cuisines = restuarantInfo.getString("cuisines");

            Restaurant restaurant = new Restaurant(name, cuisines);
            allRestuarants.add(restaurant);

        }


        return  allRestuarants;
    }

}
