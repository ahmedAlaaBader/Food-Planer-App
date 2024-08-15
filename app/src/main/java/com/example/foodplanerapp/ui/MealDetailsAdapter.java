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

import com.example.foodplanerapp.model.CountryItems;
import com.example.foodplanerapp.model.IngredientDto;
import com.example.foodplanerapp.model.MealDto;
import com.example.foodplanerapp.model.MealInfoDto;
import com.example.foodplanerapp.model.Product;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class MealDetailsAdapter extends RecyclerView.Adapter<MealDetailsAdapter.MealDetailsHolder>{
    private List<MealDto> mealDtos;
    List<MealInfoDto> mealInfoDtos;
    List<IngredientDto>  ingredientDtoList;
    Context context;
    //OnMealClicked onMealClicked;

    public MealDetailsAdapter( List<IngredientDto>  ingredientDtoList, Context context) {
        this.ingredientDtoList = ingredientDtoList;
        this.context = context;
    }

    @NonNull
    @Override
    public MealDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.ingredients_meal_details, parent, false);
        MealDetailsAdapter.MealDetailsHolder holder = new MealDetailsAdapter.MealDetailsHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MealDetailsHolder holder, int position) {

        IngredientDto ingredientDto= ingredientDtoList.get(position);
        String name = ingredientDtoList.get(holder.getAbsoluteAdapterPosition()).getStrIngredient();
        holder.ingredientNameItem.setText(ingredientDtoList.get(holder.getAbsoluteAdapterPosition()).getStrIngredient());
        Glide.with(holder.itemView.getContext()).load("https://www.themealdb.com/images/ingredients/" + name + "-Small.png").placeholder(R.drawable.meal).error(R.drawable.meal).into(holder.ingredientImage);
        holder.ingredientNameItem.setText(ingredientDto.getStrIngredient());
        //holder.ingredientMeasure.setText(ingredientDto.getStrDescription());

    }

    @Override
    public int getItemCount() {
        return ingredientDtoList.size();
    }
    public void changeData(List<IngredientDto>  ingredientDtoList) {
        this.ingredientDtoList = ingredientDtoList;
        notifyDataSetChanged();
    }

    public void clearData() {
        mealDtos.clear();
        notifyDataSetChanged();
    }
    static class MealDetailsHolder extends RecyclerView.ViewHolder {
        ImageView mealImage,imageCalender,imageFav,ingredientImage ;
        TextView mealName, cat,country,ingredients,procedures,takeLook,textView25,ingredientMeasure,ingredientNameItem;
        YouTubePlayerView youTubePlayerView;
        Button btnAddToCalender;
        CardView cardView,cardViewDetails;

        public MealDetailsHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.recyclerViewIngredientsItemDetails);
            ingredientMeasure=  itemView.findViewById(R.id.textViewIngredientMeasureItem);
            ingredientNameItem=itemView.findViewById(R.id.textViewIngredientNameItem_mealDetails);
            ingredientImage= itemView.findViewById(R.id.imageViewIngredientImageItem_mealDetails);
        }
    }
}
