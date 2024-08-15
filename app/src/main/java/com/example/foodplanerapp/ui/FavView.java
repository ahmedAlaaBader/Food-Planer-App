package com.example.foodplanerapp.ui;

import com.example.foodplanerapp.model.MealDto;

import java.util.List;

import io.reactivex.Flowable;

public interface FavView {
    public void setData(List<MealDto> mealDto);
    public void setErrorMessage(String message);

}
