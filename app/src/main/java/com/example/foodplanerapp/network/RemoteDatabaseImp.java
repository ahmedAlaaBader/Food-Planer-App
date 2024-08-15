package com.example.foodplanerapp.network;

import com.example.foodplanerapp.model.DBHelper;
import com.example.foodplanerapp.model.MealDto;
import com.example.foodplanerapp.model.PlanDto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import io.reactivex.rxjava3.core.Completable;

public class RemoteDatabaseImp {
    private DBHelper dbHelper;
    private FirebaseAuth firebaseAuth;

    public RemoteDatabaseImp() {
        dbHelper = new DBHelper();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public Completable insertToFavorite(MealDto mealDto) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            String encodeEmail = encodeEmailForFirebase(email);
            dbHelper.addMealToFavorite(encodeEmail, mealDto);
            return Completable.complete();
        }
        else {
            return Completable.error(new IllegalStateException("User is not authenticated"));
        }
    }



    public Completable insertToWeekPlan(PlanDto planDto) {
        String email = firebaseAuth.getCurrentUser().getEmail();
        String encodeEmail = encodeEmailForFirebase(email);
        dbHelper.addMealToPlan(encodeEmail, planDto);
        return Completable.complete();
    }


    public void deleteFromWeekPlane(PlanDto planDto) {

        String email = firebaseAuth.getCurrentUser().getEmail();
        String encodeEmail = encodeEmailForFirebase(email);
        dbHelper.deleteMealPlan(encodeEmail,planDto);

    }


    public void deleteFromFavorite(MealDto mealDto) {
        String email = firebaseAuth.getCurrentUser().getEmail();
        String encodeEmail = encodeEmailForFirebase(email);
        dbHelper.deleteMealFromFavorite(encodeEmail,mealDto);
    }

    private String encodeEmailForFirebase(String email) {
        return email.replace(".", ",");
    }
}