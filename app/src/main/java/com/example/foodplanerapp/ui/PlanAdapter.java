package com.example.foodplanerapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanerapp.R;
import com.example.foodplanerapp.model.MealDto;
import com.example.foodplanerapp.model.PlanDto;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanItemHolder> {
    List<PlanDto> planDtos;
    Context context;
    OnPlanDelete listener;

    public PlanAdapter(List<PlanDto> planDtos, Context context, OnPlanDelete listener) {
        this.planDtos = planDtos;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlanItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.meal_remove,parent,false);
        PlanAdapter.PlanItemHolder  holder = new PlanAdapter.PlanItemHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlanItemHolder holder, int position) {
        PlanDto currentPlan = planDtos.get(position);
        holder.title.setText(currentPlan.strMeal);
        holder.country.setText(currentPlan.strArea);
        Glide.with(context).load(currentPlan.strMealThumb).into(holder.mealimageView);
        // holder.desc.setText(currentProduct.getStrCategoryDescription());
        holder.removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(currentPlan);
            }
        });
    }

    @Override
    public int getItemCount() {
        return planDtos.size();
    }

    class PlanItemHolder extends RecyclerView.ViewHolder{
        ImageView mealimageView,removeImage;
        TextView title, country;

        public PlanItemHolder(@NonNull View itemView) {
            super(itemView);
            mealimageView = itemView.findViewById(R.id.meal_imageView);
            removeImage = itemView.findViewById(R.id.deleteFromPlan_imageView);
            title = itemView.findViewById(R.id.titleMeal_textView);
            country = itemView.findViewById(R.id.countryMeal_textView);

        }
    }
}
