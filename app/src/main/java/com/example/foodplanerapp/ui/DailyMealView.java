package com.example.foodplanerapp.ui;

import com.example.foodplanerapp.model.DailyMeal;
import com.example.foodplanerapp.model.Product;

import java.util.List;

public interface DailyMealView {
    public void setData(List<DailyMeal> meals);
    public void setErrorMessage(String message);
}
