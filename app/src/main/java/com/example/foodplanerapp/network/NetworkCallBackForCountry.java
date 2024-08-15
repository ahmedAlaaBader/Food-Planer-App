package com.example.foodplanerapp.network;

import com.example.foodplanerapp.model.CountryResponse;
import com.example.foodplanerapp.model.DailyMealResponse;

public interface NetworkCallBackForCountry {
    void onCountrySuccess(CountryResponse countryResponse);
    void onCountryFailure(String message);
}

