package com.example.foodplanerapp.presenter;


import com.example.foodplanerapp.model.CatItemsResponse;
import com.example.foodplanerapp.model.CountryItemResponse;
import com.example.foodplanerapp.network.NetworkCallBackForCountryItems;
import com.example.foodplanerapp.network.Repo;
import com.example.foodplanerapp.ui.MealView;

import io.reactivex.rxjava3.core.Single;

public class MealPresenter implements NetworkCallBackForCountryItems {
   MealView mealView;
   Repo repo;
    public MealPresenter(MealView mealView, Repo repo){
        this.mealView = mealView;
        this.repo = repo;
    }


    public Single<CountryItemResponse> geCountryItemResponseSingle(String country) {
        return repo.getMealsByCuisine(country);
    }
    public Single<CatItemsResponse> getCatItemResponseSingle(String cat) {
        return repo.getMealsByCategory(cat);
    }


    //    @Override
//    public void onSuccessResult(List<CategoriesItem> categoriesItems) {
//        mealView.showMealSuccessMessage(meali);
//    }
//
//    @Override
//    public void onCategoryFailure(String error) {
//
//    }
    @Override
    public void onCountryItemsSuccess(CountryItemResponse countryItemResponse) {

    }

    @Override
    public void onCountryItemsFailure(String message) {

    }
}
