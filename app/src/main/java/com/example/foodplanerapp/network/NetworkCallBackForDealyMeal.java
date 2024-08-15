package com.example.foodplanerapp.network;

import com.example.foodplanerapp.model.DailyMealResponse;

public interface NetworkCallBackForDealyMeal {
    void onDailyMailSuccess(DailyMealResponse mail);
    void onDailyMailFailure(String message);
}
