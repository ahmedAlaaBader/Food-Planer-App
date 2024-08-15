package com.example.foodplanerapp.network;

import android.content.Context;


import com.example.foodplanerapp.model.CatItemsResponse;
import com.example.foodplanerapp.model.CountryItemResponse;
import com.example.foodplanerapp.model.CountryResponse;
import com.example.foodplanerapp.model.DailyMealResponse;
import com.example.foodplanerapp.model.MealsListDto;
import com.example.foodplanerapp.model.ProductsResponse;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductRemoteDataSource {
    public static ProductRemoteDataSource instance = null;
    Context context;
    private ProductService productService;

    private ProductRemoteDataSource()
    {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        productService = retrofit.create(ProductService.class);

    }


    public static ProductRemoteDataSource getInstance(){
        if (instance == null)
        {
            instance = new ProductRemoteDataSource();
        }
        return  instance;
    }


    public void makeNetworkCall(NetworkCallBack networkCallBack)
    {
        productService.getAllProducts().enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                networkCallBack.onProductSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable throwable) {
                networkCallBack.onProductFailure(throwable.getMessage());
            }
        });

    }
    public void makeNetworkCall(NetworkCallBackForDealyMeal networkCallBack)
    {
        productService.getDailyMeal().enqueue(new Callback<DailyMealResponse>() {
            @Override
            public void onResponse(Call<DailyMealResponse> call, Response<DailyMealResponse> response) {
                networkCallBack.onDailyMailSuccess(response.body());
            }

            @Override
            public void onFailure(Call<DailyMealResponse> call, Throwable throwable) {
                networkCallBack.onDailyMailFailure(throwable.getMessage());
            }
        });

    }
//    public void makeNetworkCall(NetworkCallBackForCountry networkCallBack)
//    {
//        productService.getCuisines().enqueue(new Callback<CountryResponse>() {
//            @Override
//            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
//                networkCallBack.onCountrySuccess(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<CountryResponse> call, Throwable throwable) {
//                networkCallBack.onCountryFailure(throwable.getMessage());
//            }
//        });
//
//    }
        public Single<CountryResponse>  netCountryResponseSingle() {
            return productService.getCuisines()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

    public Single<CountryItemResponse> NeCountryItemResponseSingle(String country) {
        return productService.getMealsByCuisine(country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<CatItemsResponse>  netCatResponseSingle(String cat) {
        return productService.getMealsByCategory(cat)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<MealsListDto> netMealDetilsResponseSingle(String id) {
        return productService.getMealById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }





}