package com.feathertouch.layoutexamples.model.weather;

import android.os.AsyncTask;
import android.util.Log;

import com.feathertouch.layoutexamples.network.JSONDataDownloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherDownloadTask  extends AsyncTask<JSONDataDownloader,Void,ArrayList<WeatherInfo>> {

    private static String TAG = "WeatherDownloadTask";
    private WeatherInfoDownloadCallback weatherInfoDownloadCallback;
    public WeatherDownloadTask(WeatherInfoDownloadCallback weatherInfoDownloadCallback) {
        this.weatherInfoDownloadCallback = weatherInfoDownloadCallback;
    }


    @Override
    protected ArrayList<WeatherInfo> doInBackground(JSONDataDownloader... downloaders) {

        JSONDataDownloader downloader = downloaders[0];
        ArrayList<WeatherInfo> weatherInfos= new ArrayList<>();
        try {
            String responseData = downloader.download();
            if ( responseData != null ) {
                weatherInfos = getWeatherInfo(responseData);
            } else {
                Log.e(TAG, "No data found");
            }

        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

        return  weatherInfos;
    }

    @Override
    protected void onPostExecute(ArrayList<WeatherInfo> results) {
        super.onPostExecute(results);
        weatherInfoDownloadCallback.weatherInfoDownloaded(results);
    }

    private ArrayList<WeatherInfo> getWeatherInfo(String responseData) throws JSONException {
        JSONObject restuarantJSON = new JSONObject(responseData);

        JSONArray daysList = restuarantJSON.getJSONArray("list");
        ArrayList<WeatherInfo> allWeatherInfo = new ArrayList<>();
        for( int index=0; index < daysList.length(); index++ ) {
            JSONObject dayInfoObject = daysList.getJSONObject(index);
            String dateString = dayInfoObject.getString("dt_txt");

            JSONArray weatherDetailsArray = dayInfoObject.getJSONArray("weather");
            String weatherDescription = null;
            if (weatherDetailsArray.length() >= 1 ) {
                JSONObject weatherDescInfo = weatherDetailsArray.getJSONObject(0);
                weatherDescription = weatherDescInfo.getString("description");
            }

            if ( weatherDescription != null ) {
                WeatherInfo wetherModel = new WeatherInfo(weatherDescription, dateString);
                allWeatherInfo.add(wetherModel);
            }
        }
        return  allWeatherInfo;
    }


}
