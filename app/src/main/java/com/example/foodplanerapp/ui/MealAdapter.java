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
import com.example.foodplanerapp.model.Country;
import com.example.foodplanerapp.model.CountryItems;
import com.example.foodplanerapp.model.MealDto;
import com.example.foodplanerapp.model.Product;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.CountryItemHolder> {

    private List<CountryItems> countryItemsList;
    public OnMealClicked onMealClicked;
    Context context;


    public MealAdapter(List<CountryItems> countryItemsList, OnMealClicked onMealClicked, Context context) {
        this.countryItemsList = countryItemsList;
        this.onMealClicked = onMealClicked;
        this.context = context;
    }
    public void updateMeals(List<CountryItems> countryItemsList, Context context,OnMealClicked onMealClicked) {
        this.countryItemsList = countryItemsList;
        this.context = context;
        this.onMealClicked = onMealClicked;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CountryItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.country_item, parent, false);
        MealAdapter.CountryItemHolder holder = new MealAdapter.CountryItemHolder(view);
        return  holder;

    }

    @Override
    public void onBindViewHolder(@NonNull CountryItemHolder holder, int position) {

        CountryItems countryItems = countryItemsList.get(position);
        holder.title.setText(countryItems.getStrMealForCountryItem());

        Glide.with(context).load(countryItems.getStrMealThumbForCountryItem()).into(holder.imageView);

       // holder.desc.setText(mealsItem.getIdMealForCountryItem());

//        holder.itemView.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (onmealClickListener != null) {
//                            onmealClickListener.onmealClick(mealsItem);
//                        }
//                    }
//                }
        holder.itemView.setOnClickListener(view->{
            onMealClicked.onClick(countryItems);
        });



        // );

    }

    @Override
    public int getItemCount() {
        return countryItemsList.size();
    }

    public void changeData(List<CountryItems> countryItemsList) {
        this.countryItemsList = countryItemsList;
        notifyDataSetChanged();
    }

    public void clearData() {
        countryItemsList.clear();
        notifyDataSetChanged();
    }

    static class CountryItemHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, desc;
       // Button btnAddToFav;
        CardView cardView;

        public CountryItemHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.countryRecyclerView);
            imageView = itemView.findViewById(R.id.flagImg);
            title = itemView.findViewById(R.id.countryTextViewForCountryItem);
            //desc = itemView.findViewById(R.id.tv_desc2);
           // btnAddToFav = itemView.findViewById(R.id.btn_add_to_fav);
        }
    }
}