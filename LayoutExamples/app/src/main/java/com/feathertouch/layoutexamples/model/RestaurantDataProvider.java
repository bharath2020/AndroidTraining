package com.feathertouch.layoutexamples.model;

import java.util.ArrayList;

public class RestaurantDataProvider {

    public ArrayList<Restaurant> loadRestaurants() {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant("Nandini Hotel", "This is awesome non veg restaurant. This is awesome non veg restaurant This is awesome non veg restaurant"));
        restaurants.add(new Restaurant("Mc Donalds", "I like the burger and fries here"));
        restaurants.add(new Restaurant("Dominos", "the Mexican wrap and veggie pizza is yummy"));
        restaurants.add(new Restaurant("Halli mane", "Very authentic south indian restaurant"));
        return  restaurants;
    }
}
