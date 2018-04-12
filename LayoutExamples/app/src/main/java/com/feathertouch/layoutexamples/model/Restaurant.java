package com.feathertouch.layoutexamples.model;

public class Restaurant {
    public String name;
    public String latestReview;

    Restaurant(String name, String latestReview) {
        this.name = name;
        this.latestReview = latestReview;
    }

    public String getName() {
        return name;
    }

    public String getLatestReview() {
        return latestReview;
    }
}
