package com.example.foodplanerapp.network;

import com.example.foodplanerapp.model.CountryItemResponse;
import com.example.foodplanerapp.model.CountryResponse;

public interface NetworkCallBackForCountryItems {
    void onCountryItemsSuccess(CountryItemResponse countryItemResponse);
    void onCountryItemsFailure(String message);
}
