package com.example.foodplanerapp.model;

public class ConvertMealToPlan {
   // public static PlanDto getMealPlannerFromMealAndDate(MealDto mealDto, String date, String dayOfWeek) {
   public static PlanDto getMealPlannerFromMealAndDate(MealDto mealDto, String dayOfWeek) {
        PlanDto planDto = new PlanDto();
        planDto.idMeal = mealDto.idMeal;
       // planDto.date = date;
        planDto.strMeal = mealDto.strMeal;
        planDto.dayOfWeek = dayOfWeek;
        planDto.strCategory = mealDto.strCategory;
        planDto.strArea = mealDto.strArea;
        planDto.strInstructions = mealDto.strInstructions;
        planDto.strMealThumb = mealDto.strMealThumb;
        planDto.strTags = mealDto.strTags;
        planDto.strYoutube = mealDto.strYoutube;
        planDto.strIngredient1 = mealDto.strIngredient1;
        planDto.strIngredient2 = mealDto.strIngredient2;
        planDto.strIngredient3 = mealDto.strIngredient3;
        planDto.strIngredient4 = mealDto.strIngredient4;
        planDto.strIngredient5 = mealDto.strIngredient5;
        planDto.strIngredient6 = mealDto.strIngredient6;
        planDto.strIngredient7 = mealDto.strIngredient7;
        planDto.strIngredient8 = mealDto.strIngredient8;
        planDto.strIngredient9 = mealDto.strIngredient9;
        planDto.strIngredient10 = mealDto.strIngredient10;
        planDto.strIngredient11 = mealDto.strIngredient11;
        planDto.strIngredient12 = mealDto.strIngredient12;
        planDto.strIngredient13 = mealDto.strIngredient13;
        planDto.strIngredient14 = mealDto.strIngredient14;
        planDto.strIngredient15 = mealDto.strIngredient15;
        planDto.strIngredient16 = mealDto.strIngredient16;
        planDto.strIngredient17 = mealDto.strIngredient17;
        planDto.strIngredient18 = mealDto.strIngredient18;
        planDto.strIngredient19 = mealDto.strIngredient19;
        planDto.strIngredient20 = mealDto.strIngredient20;
        planDto.strMeasure1 = mealDto.strMeasure1;
        planDto.strMeasure2 = mealDto.strMeasure2;
        planDto.strMeasure3 = mealDto.strMeasure3;
        planDto.strMeasure4 = mealDto.strMeasure4;
        planDto.strMeasure5 = mealDto.strMeasure5;
        planDto.strMeasure6 = mealDto.strMeasure6;
        planDto.strMeasure7 = mealDto.strMeasure7;
        planDto.strMeasure8 = mealDto.strMeasure8;
        planDto.strMeasure9 = mealDto.strMeasure9;
        planDto.strMeasure10 = mealDto.strMeasure10;
        planDto.strMeasure11 = mealDto.strMeasure11;
        planDto.strMeasure12 = mealDto.strMeasure12;
        planDto.strMeasure13 = mealDto.strMeasure13;
        planDto.strMeasure14 = mealDto.strMeasure14;
        planDto.strMeasure15 = mealDto.strMeasure15;
        planDto.strMeasure16 = mealDto.strMeasure16;
        planDto.strMeasure17 = mealDto.strMeasure17;
        planDto.strMeasure18 = mealDto.strMeasure18;
        planDto.strMeasure19 = mealDto.strMeasure19;
        planDto.strMeasure20 = mealDto.strMeasure20;
        planDto.id = dayOfWeek + "-" + mealDto.idMeal;
        return planDto;
    }
}
