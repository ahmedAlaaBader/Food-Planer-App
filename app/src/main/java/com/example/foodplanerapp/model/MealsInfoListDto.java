package com.example.foodplanerapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MealsInfoListDto {
    @SerializedName("meals")
    public ArrayList<MealInfoDto> mealInfoList;
}
