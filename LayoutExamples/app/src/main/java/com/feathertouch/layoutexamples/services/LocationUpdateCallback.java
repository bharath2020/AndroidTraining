package com.feathertouch.layoutexamples.services;

import android.location.Location;

public interface LocationUpdateCallback {

    public void newLocationFound(Location location);
}
