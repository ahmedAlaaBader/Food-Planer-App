package com.example.foodplanerapp.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
@Dao
public interface PlanDao {
    // Meal Plans
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMealPlan(PlanDto planDto);

    @Query("DELETE FROM plan WHERE id = :id")
    void deleteMealPlan(String id);
    @Query("DELETE FROM plan ")
    void deleteAllPlan();

    @Query("SELECT * FROM plan WHERE dayOfWeek = :dayOfWeek")
    Flowable<List<PlanDto>> getMealsForDay(String dayOfWeek);

    @Query("SELECT * FROM plan")
    Flowable<List<PlanDto>> getAllMeals();
}
