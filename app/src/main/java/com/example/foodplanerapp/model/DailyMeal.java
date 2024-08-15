package com.example.foodplanerapp.model;





public class DailyMeal {

    private String idMeal;
    private String strMeal;
    private String strMealThumb;
    private String strInstructions;

    public DailyMeal() {
    }

    public DailyMeal( String idMeal, String strMeal, String strMealThumb, String strInstructions) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.strInstructions = strInstructions;
    }


    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal( String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }
}
