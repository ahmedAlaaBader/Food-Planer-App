package com.example.foodplanerapp.ui;

import com.example.foodplanerapp.model.CountryItems;
import com.example.foodplanerapp.model.DailyMeal;

import java.util.List;

public interface MealView {
    public void setDataForCountryItems(List<CountryItems> meals);
    public void setErrorMessageCountryItems(String message);
}
