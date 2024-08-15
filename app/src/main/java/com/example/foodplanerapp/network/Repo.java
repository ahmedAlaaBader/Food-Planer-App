package com.example.foodplanerapp.network;

import android.util.Log;

import com.example.foodplanerapp.model.AppDatabase;

import com.example.foodplanerapp.model.CatItemsResponse;
import com.example.foodplanerapp.model.MealDao;
import com.example.foodplanerapp.model.CountryItemResponse;
import com.example.foodplanerapp.model.CountryResponse;
import com.example.foodplanerapp.model.MealDto;
import com.example.foodplanerapp.model.MealsListDto;
import com.example.foodplanerapp.model.PlanDao;
import com.example.foodplanerapp.model.PlanDto;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Repo {
    ProductRemoteDataSource remoteDataSource;
    MealDao mealDao;
    PlanDao planDao;
    AppDatabase roomDatabase;
    RemoteDatabaseImp remoteDatabaseImp;

    private static Repo instance = null;

    public Repo() {
    }



    private Repo(ProductRemoteDataSource productRemoteDataSource, AppDatabase roomDatabase){
        this.remoteDataSource = productRemoteDataSource;
        this.roomDatabase = roomDatabase;
        mealDao = roomDatabase.mealDao() ;
        planDao=roomDatabase.planDao();
        remoteDatabaseImp = new RemoteDatabaseImp();

    }



    public Flowable<List<MealDto>> getFavMeals(){
        return mealDao.getAllFavMeals();
    }
    public void insertToFav(MealDto mealDto){
        new Thread()
        {
            @Override
            public void run() {
                super.run();
                mealDao.insertMealToFav(mealDto);
                remoteDatabaseImp.insertToFavorite(mealDto);
            }
        }.start();

    }
    public void delete(MealDto mealDto)
    {
        new Thread()
        {
            @Override
            public void run() {
                super.run();
                mealDao.deleteMealFav(mealDto.idMeal);
                remoteDatabaseImp.deleteFromFavorite(mealDto);
            }
        }.start();
    }
    public void deleteAllFav()
    {
        new Thread()
        {
            @Override
            public void run() {
                super.run();
                mealDao.deleteAllFav();
            }
        }.start();
    }
    public void insertToPlan(PlanDto planDto){
        Log.d("TAG", "insertToPlan: "+planDto.dayOfWeek);

        new Thread()
        {
            @Override
            public void run() {
                super.run();
                planDao.insertMealPlan(planDto);
                remoteDatabaseImp.insertToWeekPlan(planDto);
            }
        }.start();
    }
    public void deleteFromPlan(PlanDto planDto)
    {
        new Thread()
        {
            @Override
            public void run() {
                super.run();
                planDao.deleteMealPlan(planDto.id);
                remoteDatabaseImp.deleteFromWeekPlane(planDto);
            }
        }.start();
    }
    public void deleteAllPlan()
    {
        new Thread()
        {
            @Override
            public void run() {
                super.run();
                planDao.deleteAllPlan();
            }
        }.start();
    }
    public Flowable<List<PlanDto>> getPlanMealsbyDay(String Day){
        return planDao.getMealsForDay(Day);
    }
    public Flowable<List<PlanDto>> getPlanMeals(){
        return planDao.getAllMeals();
    }



//    public void insertToPlan(PlanDto planDto)
//    {
//        Log.d("TAG", "insertToPlan: "+planDto.dayOfWeek);
//        new Thread()
//        {
//            @Override
//            public void run() {
//                super.run();
//                dao.insertMealPlan(planDto);
//
//            }
//        }.start();
//
//    }
//    public void insert(Product product){
//        Thread t = new Thread(){
//            @Override
//            public void run() {
//                dao.insertProduct(product);
//            }
//        };
//        t.start();
//    }
//    public void insert(MealDto mealDto){
//        Thread t = new Thread(){
//            @Override
//            public void run() {
//                mealDao.insertFavMeal(mealDto);
//            }
//        };
//        t.start();
//    }
//
//    public void delete(Product product){
//        Thread t = new Thread(){
//            @Override
//            public void run() {
//                dao.deleteProduct(product);
//            }
//        };
//        t.start();
//    }



//    public void insert(MealDto mealDto){
//        dao.insertMealToFav(mealDto).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//                    @Override
//                    public void onComplete() {
//                        Log.i("TAG", "onComplete: inserted");
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//                });
//    }

//    public void delete(MealDto mealDto){
//        Thread t = new Thread(){
//            @Override
//            public void run() {
//                dao.deleteMealFav(mealDto);
//            }
//        };
//        t.start();
//    }

//    public void delete(Product product){
//        Thread t = new Thread(){
//            @Override
//            public void run() {
//                dao.deleteProduct(product);
//            }
//        };
//        t.start();
//    }

    public static Repo getInstance(ProductRemoteDataSource productRemoteDataSource, AppDatabase roomDatabase){
        if (instance == null){
            instance = new Repo(productRemoteDataSource, roomDatabase);
        }
        return instance;
    }

    public void getAllProducts(NetworkCallBack networkCallBack){
        remoteDataSource.makeNetworkCall(networkCallBack);
    }
//    public void getCuisines(NetworkCallBackForCountry networkCallBackForCountry){
//        remoteDataSource.makeNetworkCall(networkCallBackForCountry);
//    }
        public Single<CountryResponse> getCuisines() {
            if (remoteDataSource == null) {
                throw new IllegalStateException("ProductRemoteDataSource is not initialized");}
            return remoteDataSource.netCountryResponseSingle().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    public void getDailyMeal(NetworkCallBackForDealyMeal networkCallBack){
        remoteDataSource.makeNetworkCall(networkCallBack);
    }
    public Single<CountryItemResponse> getMealsByCuisine(String country) {
        if (remoteDataSource == null) {
            throw new IllegalStateException("ProductRemoteDataSource is not initialized");}
        return remoteDataSource.NeCountryItemResponseSingle(country).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<CatItemsResponse> getMealsByCategory(String cat) {
        if (remoteDataSource == null) {
            throw new IllegalStateException("catagory items is not initialized");}
        return remoteDataSource.netCatResponseSingle(cat).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<MealsListDto> getMealById(String id) {
        if (remoteDataSource == null) {
            throw new IllegalStateException("ProductRemoteDataSource is not initialized");}
        return remoteDataSource.netMealDetilsResponseSingle(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}