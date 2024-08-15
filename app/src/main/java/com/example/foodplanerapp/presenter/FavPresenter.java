package com.example.foodplanerapp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.foodplanerapp.model.AppDatabase;
import com.example.foodplanerapp.model.MealDao;
import com.example.foodplanerapp.model.MealDto;
import com.example.foodplanerapp.model.PlanDto;
import com.example.foodplanerapp.ui.FavView;
import com.example.foodplanerapp.model.Product;
import com.example.foodplanerapp.network.Repo;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavPresenter {
    FavView view;
    Repo repo;
   // AppDatabase dao;
   // MealDao mealDao;

    public FavPresenter(FavView view, Repo repo){
        this.repo = repo;
        this.view = view;
       // dao=AppDatabase.getInstance(context);
        //mealDao= dao.mealDao();
    }

    public void getLocalData() {
        Log.d("TAG", "sendFavData: Method called");

        Flowable<List<MealDto>> flowable = repo.getFavMeals();
        Log.d("TAG", "sendFavData: Flowable created");

        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<List<MealDto>>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        Log.d("TAG", "onSubscribe: Subscribed");
                        s.request(Long.MAX_VALUE);
                    }
                    @Override
                    public void onNext(List<MealDto> detailsMealItems) {
                        Log.d("TAG", "onNext: Received " + detailsMealItems.size() + " products");
                        view.setData(detailsMealItems);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("TAG", "onError: Error occurred", t);
                        view.setErrorMessage(t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TAG", "onComplete: Completed");
                    }
                });
    }

//    public void getLocalData(){
//        view.setData(repo.getLocalData());
//    }

    public void delete(MealDto mealDto){
        repo.delete(mealDto);
    }



}
