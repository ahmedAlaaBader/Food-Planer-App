package com.example.foodplanerapp.presenter;

import android.util.Log;


import com.example.foodplanerapp.model.PlanDto;
import com.example.foodplanerapp.network.Repo;

import com.example.foodplanerapp.ui.PlanView;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanPresenter {
    PlanView view;
    Repo repo;

    public PlanPresenter(PlanView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void getPlanDataByDay(String day) {
        Log.d("TAG", "sendFavData: Method called");

        Flowable<List<PlanDto>> flowable = repo.getPlanMealsbyDay(day);
        Log.d("TAG", "sendFavData: Flowable created");

        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<List<PlanDto>>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        Log.d("TAG", "onSubscribe: Subscribed");
                        s.request(Long.MAX_VALUE);
                    }
                    @Override
                    public void onNext(List<PlanDto> planDtos) {
                        Log.d("TAG", "onNext: Received " + planDtos.size() + " products");
                        view.setData(planDtos);
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
    public void getAllPlanData() {
        Log.d("TAG", "sendFavData: Method called");

        Flowable<List<PlanDto>> flowable = repo.getPlanMeals();
        Log.d("TAG", "sendFavData: Flowable created");

        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<List<PlanDto>>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        Log.d("TAG", "onSubscribe: Subscribed");
                        s.request(Long.MAX_VALUE);
                    }
                    @Override
                    public void onNext(List<PlanDto> planDtos) {
                        Log.d("TAG", "onNext: Received " + planDtos.size() + " products");
                        view.setData(planDtos);
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

    public void deleteFromPlan(PlanDto planDto){
        repo.deleteFromPlan(planDto);
    }
}
