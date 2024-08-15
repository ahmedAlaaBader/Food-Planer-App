package com.example.foodplanerapp.presenter;

import com.example.foodplanerapp.model.CountryResponse;
import com.example.foodplanerapp.model.DailyMealResponse;
import com.example.foodplanerapp.model.MealsListDto;
import com.example.foodplanerapp.network.NetworkCallBackForCountry;
import com.example.foodplanerapp.network.Repo;
import com.example.foodplanerapp.ui.CountryView;

import io.reactivex.rxjava3.core.Single;

public class CountryPresenter implements NetworkCallBackForCountry {
    CountryView countryView;
    Repo repo;

    public CountryPresenter(Repo repo,CountryView countryView) {
        this.countryView = countryView;
        this.repo = repo;
    }
//    public void makeNetwork(){
//        repo.getCuisines(this);
//    }
        public Single<CountryResponse>  getCountryResponseSingle(){
            return repo.getCuisines();
        }


    @Override
    public void onCountrySuccess(CountryResponse countryResponse) {
        countryView.setDataForCountry(countryResponse.getCuisines());
    }

    @Override
    public void onCountryFailure(String message) {
     countryView.setErrorMessageForCountry(message);
    }

}
