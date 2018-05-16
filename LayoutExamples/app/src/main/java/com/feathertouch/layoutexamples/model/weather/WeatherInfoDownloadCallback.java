package com.feathertouch.layoutexamples.model.weather;

import java.util.ArrayList;

public interface WeatherInfoDownloadCallback {
    public void weatherInfoDownloaded(ArrayList<WeatherInfo> weatherInfo);

}
