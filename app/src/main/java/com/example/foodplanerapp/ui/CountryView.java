package com.example.foodplanerapp.ui;

import com.example.foodplanerapp.model.Country;
import com.example.foodplanerapp.model.Product;

import java.util.List;

public interface CountryView {
    public void setDataForCountry(List<Country> allCountries);
    public void setErrorMessageForCountry(String message);
}
