package com.example.foodplanerapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanerapp.R;
import com.example.foodplanerapp.model.MealDto;
import com.example.foodplanerapp.model.Product;

import java.util.List;


public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavItemHolder> {

    List<MealDto> mealDtos;
    Context context;
    onDelete listener;

    public FavAdapter(List<MealDto> mealDtos, Context context,onDelete listener) {
        this.mealDtos = mealDtos;
        this.context = context;
        this.listener = listener;
    }
    

    @NonNull
    @Override
    public FavItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.meal_remove,parent,false);
        FavItemHolder holder = new FavItemHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavItemHolder holder, int position) {
        MealDto currentProduct = mealDtos.get(position);
        holder.title.setText(currentProduct.strMeal);
        holder.country.setText(currentProduct.strArea);
        Glide.with(context).load(currentProduct.strMealThumb).into(holder.imageView);
       // holder.desc.setText(currentProduct.getStrCategoryDescription());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(currentProduct);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mealDtos.size();
    }

    class FavItemHolder extends RecyclerView.ViewHolder{
        ImageView imageView,remove;
        TextView title, country;
        CardView cardView;

        public FavItemHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.meal_imageView);
            title = itemView.findViewById(R.id.titleMeal_textView);
            country = itemView.findViewById(R.id.countryMeal_textView);

            remove = itemView.findViewById(R.id.deleteFromPlan_imageView);
        }
    }
}
