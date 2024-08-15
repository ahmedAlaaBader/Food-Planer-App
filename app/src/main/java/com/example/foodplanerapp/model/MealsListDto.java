package com.example.foodplanerapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MealsListDto
{
    @SerializedName("meals")
    public ArrayList<MealDto> meals;
    public List<MealDto> getMealDetails() {
        return meals;
    }
}
