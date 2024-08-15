package com.example.foodplanerapp;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodplanerapp.model.AppDatabase;
import com.example.foodplanerapp.model.Country;
import com.example.foodplanerapp.model.CountryItemResponse;
import com.example.foodplanerapp.model.CountryItems;
import com.example.foodplanerapp.model.AppDatabase;
import com.example.foodplanerapp.model.IngredientDto;
import com.example.foodplanerapp.model.MealDto;
import com.example.foodplanerapp.model.MealsListDto;
import com.example.foodplanerapp.network.ProductRemoteDataSource;
import com.example.foodplanerapp.network.Repo;
import com.example.foodplanerapp.presenter.MealDetailsPresenter;
import com.example.foodplanerapp.presenter.MealPresenter;
import com.example.foodplanerapp.ui.MealAdapter;
import com.example.foodplanerapp.ui.MealDetailsAdapter;
import com.example.foodplanerapp.ui.MealDetailsView;
import com.example.foodplanerapp.ui.OnFavMealClickListner;
import com.example.foodplanerapp.ui.onFavClickListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MealDetailsFragment extends Fragment implements MealDetailsView {
    private static final String PREFS_NAME = "mealDetailsPrefs";
    private static final String KEY_IS_FAV = "isFav_";
    CountryItems countryItems;
    private MealDetailsAdapter mealDetailsAdapter;
    private RecyclerView recyclerView;
    private List<IngredientDto> ingredientDtoList;
    Single<MealsListDto> mealsListDtoSingle;
    private MealDetailsPresenter mealDetailsPresenter;
    ImageView mealImage,imageCalender ;
    static ImageView imageFav;
    TextView mealName, cat,country,ingredients,procedures,takeLook,textView25;
    YouTubePlayerView youTubePlayerView;
    Button btnAddToCalender;
    CardView cardViewDetails;
    MealDto mealDto;
    static Boolean isFav=true;
    //OnFavMealClickListner listner;
    public MealDetailsFragment() {
        // Required empty public constructor
    }


    public static MealDetailsFragment newInstance(String param1, String param2) {
        MealDetailsFragment fragment = new MealDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      //  mealsListDtoSingle = mealDetailsPresenter.getMealDeatelsResponseSingle(countryItems.getIdMealForCountryItem());
        Serializable retrievedItem = getArguments().getSerializable("mealId");

        if (retrievedItem instanceof CountryItems) {
            countryItems = (CountryItems) retrievedItem;
        }

        cardViewDetails = view.findViewById(R.id.details_item_card);
        mealImage = view.findViewById(R.id.mealImage);
        imageCalender = view.findViewById(R.id.imageViewAddToCalendarItemDetails);
        imageFav = view.findViewById(R.id.imageViewAddToFavITemDetails);
        mealName = view.findViewById(R.id.txtViewMealNameItemDetails);
        cat = view.findViewById(R.id.textViewMealCateItemDetails);
        country = view.findViewById(R.id.textViewMealCountryItemDetails);
        ingredients = view.findViewById(R.id.tv_ingredients_meal_details);
        procedures = view.findViewById(R.id.textViewProcedures);
        youTubePlayerView = view.findViewById(R.id.ytPlayer);

        textView25 = view.findViewById(R.id.textView25);


        recyclerView = view.findViewById(R.id.recyclerViewIngredientsItemDetails);
        mealDetailsAdapter = new MealDetailsAdapter(new ArrayList<>(), requireContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        mealDetailsPresenter = new MealDetailsPresenter(
                Repo.getInstance(ProductRemoteDataSource.getInstance(), AppDatabase.getInstance(requireContext())), this
     );
        imageCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDayPickerDialog();

            }
        });
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        isFav = sharedPreferences.getBoolean(KEY_IS_FAV + countryItems.getIdMealForCountryItem(), true); // Default is true
        if (isFav) {
            imageFav.setImageResource(R.drawable.ic_not_fav);
        } else {
            imageFav.setImageResource(R.drawable.ic_fav);
        }
        imageFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFav){
                Toast.makeText(requireContext(), "Inserted successfully", Toast.LENGTH_SHORT).show();
                mealDetailsPresenter.insert(mealDto);
                imageFav.setImageResource(R.drawable.ic_fav);
                isFav=false;
                }
                else{
                    mealDetailsPresenter.delete(mealDto);
                    Toast.makeText(requireContext(), "Removed successfully", Toast.LENGTH_SHORT).show();
                    imageFav.setImageResource(R.drawable.ic_not_fav);
                    isFav=true;

                }
                // Save the favorite status in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(KEY_IS_FAV + countryItems.getIdMealForCountryItem(), isFav);
                editor.apply();

            }
        });

        if (countryItems != null) {
            mealsListDtoSingle = mealDetailsPresenter.getMealDeatelsResponseSingle(countryItems.getIdMealForCountryItem());
            Log.i("TAG", "s()mealDetailsList " + mealsListDtoSingle);
            mealsListDtoSingle.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(item -> {
                                if (item.meals != null && !item.meals.isEmpty()) {
                                     mealDto = item.meals.get(0); // Get the first (and likely only) meal
                                    displayMealDetails(mealDto); // Display meal details
                                } else {
                                    Log.e("TAG", "No meals found in response");
                                }
                            },
                            throwable -> {
                                Log.e("TAG", "showCategoryDetail: unable to show products because: " + throwable.getMessage());
                            });
        }
    }

    private void displayMealDetails(MealDto meal) {
        mealName.setText(meal.strMeal);
        cat.setText(meal.strCategory);
        country.setText(meal.strArea);
        procedures.setText(meal.strInstructions);
        Glide.with(requireContext()).load(meal.strMealThumb).into(mealImage);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                if (meal.strYoutube != null && !meal.strYoutube.isEmpty()) {
                    // Extract the video ID from the URL
                    String videoId = extractVideoIdFromUrl(meal.strYoutube);
                    if (videoId != null) {
                        youTubePlayer.loadVideo(videoId, 0);
                    }
                }
            }
        });



        List<IngredientDto> ingredientDtoList = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            try {
                Field field = meal.getClass().getDeclaredField("strIngredient" + i);
                field.setAccessible(true);
                String ingredientName = (String) field.get(meal);

                if (ingredientName != null && !ingredientName.isEmpty()) {
                    IngredientDto ingredientDto = new IngredientDto();
                    ingredientDto.setStrIngredient(ingredientName);
                    ingredientDtoList.add(ingredientDto);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }




        mealDetailsAdapter.changeData(ingredientDtoList);
        recyclerView.setAdapter(mealDetailsAdapter);
    }
    private String extractVideoIdFromUrl(String url) {
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?v%3D|watch\\?v=|\\/videos\\/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?v%3D|youtu.be\\/|\\/v\\/|\\/embed\\/|\\/e\\/|v=|\\/v\\/|\\/e\\/|\\/embed\\/|v%3D|\\/v\\/|\\/e\\/|v=)([^#&?\\n]+)";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url); //url is youtube url for which you want to extract video id.
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
    private void showDayPickerDialog() {
        String[] days = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose a day")
                .setItems(days, (dialog, which) -> {
                    String selectedDay = days[which];
                  mealDetailsPresenter.insertMealToPlan(selectedDay, mealDto);
                    Toast.makeText(requireContext(), "Added to plan successfully", Toast.LENGTH_SHORT).show();
                });
        builder.create().show();

    }

//    @Override
//    public void onClick(MealDto mealDto) {
//        Toast.makeText(requireContext(), "Inserted successfully", Toast.LENGTH_SHORT).show();
//        mealDetailsPresenter.insert(mealDto);
//    }
}