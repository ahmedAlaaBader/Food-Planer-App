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
import com.example.foodplanerapp.model.DailyMeal;
import com.example.foodplanerapp.model.Product;

import java.util.List;

public class DailyMealAdapter extends RecyclerView.Adapter<DailyMealAdapter.MealHolder>{
    List<DailyMeal> meals;
    Context context;
    onFavClickListener listener;

    //public DailyMealAdapter(List<DailyMeal> meals, Context context, onFavClickListener listener){
        public DailyMealAdapter(List<DailyMeal> meals, Context context){
        this.context = context;
        this.meals = meals;
       // this.listener = listener;
    }


    @NonNull
    @Override
    public DailyMealAdapter.MealHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.product_item,parent,false);
        DailyMealAdapter.MealHolder holder = new DailyMealAdapter.MealHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DailyMealAdapter.MealHolder holder, int position) {
        DailyMeal currentMeal = meals.get(position);
        holder.title.setText(currentMeal.getStrMeal());
        Glide.with(context).load(currentMeal.getStrMealThumb()).into(holder.imageView);
        holder.desc.setText(currentMeal.getStrInstructions());

//        holder.btnAddToFav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onClick(currentMeal);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class MealHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title, desc;
        Button btnAddToFav;
        CardView cardView;



        public MealHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.recyclerView3);
            imageView = itemView.findViewById(R.id.imageView2);
            title = itemView.findViewById(R.id.tv_title2);
            desc = itemView.findViewById(R.id.tv_desc2);
            btnAddToFav = itemView.findViewById(R.id.btn_add_to_fav);
        }
    }
}
