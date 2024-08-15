package com.example.foodplanerapp.model;
import android.content.Context;

import androidx.annotation.NonNull;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;
public class DBHelper {
    private DatabaseReference databaseReference;

    public DBHelper() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void addMealToFavorite(String email, MealDto mealDto) {
        databaseReference.child("favMeal").child(email).child(mealDto.idMeal).setValue(mealDto);
    }

    public void getAllFavorite( Context context) {
        String encodeEmail = encodeEmailForFirebase(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        databaseReference.child("favMeal")
                .child(encodeEmail)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<MealDto>favItemList = new ArrayList<>();

                        for(DataSnapshot data: snapshot.getChildren()){
                            favItemList.add(data.getValue(MealDto.class));
                        }
                        new Thread(()->{  for (MealDto item : favItemList){
                            AppDatabase.getInstance(context).mealDao().insertMealToFav(item);
                        }}).start();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void getAllWeekPlan( Context context) {
        String encodeEmail = encodeEmailForFirebase(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        databaseReference.child("planMeal")
                .child(encodeEmail)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<PlanDto>weekPlanItemList = new ArrayList<>();

                        for(DataSnapshot data: snapshot.getChildren()){
                            weekPlanItemList.add(data.getValue(PlanDto.class));
                        }
                        new Thread(()->{  for (PlanDto weekPlan  : weekPlanItemList){
                            AppDatabase.getInstance(context).planDao().insertMealPlan(weekPlan);
                        }}).start();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    public void deleteMealFromFavorite(String email, MealDto mealDto) {
        databaseReference.child("favMeal").child(email).child(mealDto.idMeal).removeValue();
    }

    public void addMealToPlan(String email, PlanDto planDto) {
        databaseReference.child("planMeal").child(email).child(planDto.idMeal).setValue(planDto);
    }

        public void deleteMealPlan(String email, PlanDto planDto) {
        databaseReference.child("planMeal").child(email).child(planDto.idMeal).removeValue();
    }
    private String encodeEmailForFirebase(String email) {
        return email.replace(".", ",");
    }
}