package com.example.foodplanerapp.presenter;

import com.example.foodplanerapp.model.DailyMealResponse;
import com.example.foodplanerapp.network.NetworkCallBackForDealyMeal;
import com.example.foodplanerapp.network.Repo;
import com.example.foodplanerapp.ui.AllProductesView;
import com.example.foodplanerapp.ui.DailyMealView;

public class DailyMealPresnter implements NetworkCallBackForDealyMeal {
    DailyMealView dailyMealView;
    Repo repo;
    public DailyMealPresnter() {
    }

    public DailyMealPresnter(DailyMealView dailyMealView, Repo repo) {
        this.dailyMealView = dailyMealView;
        this.repo = repo;
    }
    public void makeNetwork(){
        repo.getDailyMeal(this);
    }

    @Override
    public void onDailyMailSuccess(DailyMealResponse meal) {
        dailyMealView.setData(meal.getMeals());
    }

    @Override
    public void onDailyMailFailure(String message) {
     dailyMealView.setErrorMessage(message);
    }
}
