package com.example.foodplanerapp.network;





import com.example.foodplanerapp.model.CatItemsResponse;
import com.example.foodplanerapp.model.Country;
import com.example.foodplanerapp.model.CountryItemResponse;
import com.example.foodplanerapp.model.CountryResponse;
import com.example.foodplanerapp.model.DailyMealResponse;
import com.example.foodplanerapp.model.MealsListDto;
import com.example.foodplanerapp.model.ProductsResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductService {
    @GET("categories.php")
    Call<ProductsResponse> getAllProducts();
    @GET("random.php")
    Call<DailyMealResponse> getDailyMeal();
    @GET("list.php?a=list")
    public Single<CountryResponse> getCuisines();
    @GET("filter.php")
    public Single<CountryItemResponse> getMealsByCuisine(@Query("a") String country);
    @GET("lookup.php")
    public Single<MealsListDto> getMealById(@Query("i") String id);
    @GET("filter.php")
    public Single<CatItemsResponse> getMealsByCategory(@Query("c") String category);

}
