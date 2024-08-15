package com.example.foodplanerapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CountryResponse {
    @SerializedName("meals")
    public ArrayList<Country> cuisines;

    public CountryResponse() {
    }
    public ArrayList<Country> getCuisines() {
        return cuisines;
    }

    public void setCuisines(ArrayList<Country> cuisines) {
        this.cuisines = cuisines;
    }
}
