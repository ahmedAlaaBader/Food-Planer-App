package com.example.foodplanerapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CountryItems implements Serializable {

    @SerializedName("strMealThumb")
    private String strMealThumb;

    @SerializedName("idMeal")
    private String idMeal;

    @SerializedName("strMeal")
    private String strMeal;

    public CountryItems(String strMealThumb, String idMeal, String strMeal) {
        this.strMealThumb = strMealThumb;
        this.idMeal = idMeal;
        this.strMeal = strMeal;
    }

    public String getStrMealThumbForCountryItem(){
        return strMealThumb;
    }

    public String getIdMealForCountryItem(){
        return idMeal;
    }

    public String getStrMealForCountryItem(){
        return strMeal;
    }
}

