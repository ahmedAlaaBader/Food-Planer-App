package com.example.foodplanerapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryItemResponse {
    @SerializedName("meals")
    private List<CountryItems> meals;

    public List<CountryItems> getListDetails(){
        return meals;
    }
}
