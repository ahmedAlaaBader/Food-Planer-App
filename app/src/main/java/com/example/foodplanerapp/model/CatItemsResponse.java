package com.example.foodplanerapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CatItemsResponse {
    @SerializedName("meals")
    private List<CountryItems> meals;

    public List<CountryItems> getCatListDetails(){
        return meals;
    }
}
