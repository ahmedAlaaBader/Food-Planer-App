package com.example.foodplanerapp.model;

import java.util.List;

public class DailyMealResponse {

        List<DailyMeal> meals;

        public DailyMealResponse() {
        }

        public DailyMealResponse(List<DailyMeal>allmeals) {
            this.meals = allmeals;
        }

        public List<DailyMeal> getMeals() {
            return meals;
        }

        public void setMeals(List<DailyMeal> allmeals) {
            this.meals = allmeals;
        }
    }

