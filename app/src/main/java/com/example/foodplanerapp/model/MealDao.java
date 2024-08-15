package com.example.foodplanerapp.model;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealDao {
    // Favorite Meals
    @Query("SELECT * FROM favorite")
    Flowable<List<MealDto>> getAllFavMeals();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMealToFav(MealDto mealDto);

    @Query("DELETE FROM favorite WHERE idMeal = :idMeal")
    void deleteMealFav(String idMeal);

    @Query("DELETE FROM favorite ")
    void deleteAllFav();


}



