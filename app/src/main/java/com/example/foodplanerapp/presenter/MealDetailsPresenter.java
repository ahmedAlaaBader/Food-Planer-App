package com.example.foodplanerapp.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.foodplanerapp.model.AppDatabase;
import com.example.foodplanerapp.model.ConvertMealToPlan;
import com.example.foodplanerapp.model.MealDao;
import com.example.foodplanerapp.model.MealDto;
import com.example.foodplanerapp.model.MealsListDto;
import com.example.foodplanerapp.model.PlanDto;
import com.example.foodplanerapp.model.Product;
import com.example.foodplanerapp.network.Repo;
import com.example.foodplanerapp.ui.MealDetailsView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenter {
    Repo repo;
    MealDetailsView mealDetailsView;
    //AppDatabase dao;
   // MealDao mealDao;
    public MealDetailsPresenter(Repo repo, MealDetailsView mealDetailsView) {
        this.repo = repo;
        this.mealDetailsView = mealDetailsView;
        //dao=AppDatabase.getInstance(context);
       // mealDao= dao.mealDao();
    }
    public Single<MealsListDto> getMealDeatelsResponseSingle (String id){
       return repo.getMealById(id);
    }
    public void insert(MealDto mealDto){

        repo.insertToFav(mealDto);
    }
    public void delete(MealDto mealDto){
        repo.delete(mealDto);
    }
    public void insertMealToPlan(String selectedDay,MealDto mealDto){
        repo.insertToPlan(ConvertMealToPlan.getMealPlannerFromMealAndDate(mealDto,selectedDay));
    }
//    public void insertMealToPlan(String selectedDay,MealDto mealDto){
//        repo.insertToPlan( ConvertMealToPlan.getMealPlannerFromMealAndDate(mealDto,selectedDay));
//        Log.d("TAG", "insertMealToPlan: "+selectedDay+mealDto.strArea);
//    }
}
