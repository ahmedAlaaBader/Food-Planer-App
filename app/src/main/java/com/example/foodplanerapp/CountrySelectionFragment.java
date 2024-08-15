package com.example.foodplanerapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodplanerapp.model.AppDatabase;
import com.example.foodplanerapp.model.Country;
import com.example.foodplanerapp.model.CountryItems;
import com.example.foodplanerapp.model.Product;
import com.example.foodplanerapp.network.ProductRemoteDataSource;
import com.example.foodplanerapp.network.Repo;
import com.example.foodplanerapp.presenter.MealPresenter;
import com.example.foodplanerapp.ui.MealAdapter;
import com.example.foodplanerapp.ui.MealView;
import com.example.foodplanerapp.ui.OnMealClicked;
import com.jakewharton.rxbinding4.widget.RxTextView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CountrySelectionFragment extends Fragment implements MealView, OnMealClicked {
    private Country country;
    private Product cat;
    private MealAdapter mealAdapter;
    private EditText editText;
    private List<CountryItems> countryItemsList = new ArrayList<>();
    private List<CountryItems> categoryItemsList = new ArrayList<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView recyclerView;
    private MealPresenter mealPresenter;
    private boolean isCountrySearch = true;  // Track if we are searching by country or category

    public CountrySelectionFragment() {
        // Required empty public constructor
    }

    public static CountrySelectionFragment newInstance(String param1, String param2) {
        CountrySelectionFragment fragment = new CountrySelectionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize any required variables
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Serializable retrievedItem = getArguments().getSerializable("country");

        if (retrievedItem instanceof Country) {
            country = (Country) retrievedItem;
            isCountrySearch = true;
        } else if (retrievedItem instanceof Product) {
            cat = (Product) retrievedItem;
            isCountrySearch = false;
        }

        recyclerView = view.findViewById(R.id.recyclerViewForCountrySelection);
        editText = view.findViewById(R.id.editTextCountrySelection);
        mealAdapter = new MealAdapter(new ArrayList<>(), this, requireContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(mealAdapter);
        mealPresenter = new MealPresenter(this, Repo.getInstance(ProductRemoteDataSource.getInstance(), AppDatabase.getInstance(requireContext())));

        if (country != null) {
            fetchCountryItems();
        } else if (cat != null) {
            fetchCategoryItems();
        }

        setupTextChangeObserver();
    }

    private void fetchCountryItems() {
        compositeDisposable.add(
                mealPresenter.geCountryItemResponseSingle(country.getStrArea())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                countryResponse -> {
                                    if (countryResponse != null && countryResponse.getListDetails() != null) {
                                        countryItemsList.clear();
                                        countryItemsList.addAll(countryResponse.getListDetails());
                                        if (isCountrySearch) {
                                            mealAdapter.updateMeals(countryItemsList, requireContext(), this);
                                        }
                                    } else {
                                        Log.i("TAG", "No data found for country.");
                                    }
                                },
                                throwable -> {
                                    Log.e("TAG", "Error fetching country items: ", throwable);
                                    Toast.makeText(requireContext(), "Error fetching country items", Toast.LENGTH_SHORT).show();
                                }
                        )
        );
    }

    private void fetchCategoryItems() {
        compositeDisposable.add(
                mealPresenter.getCatItemResponseSingle(cat.getStrCategory())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                item -> {
                                    if (item != null && item.getCatListDetails() != null) {
                                        categoryItemsList.clear();
                                        categoryItemsList.addAll(item.getCatListDetails());
                                        if (!isCountrySearch) {
                                            mealAdapter.updateMeals(categoryItemsList, requireContext(), this);
                                        }
                                    } else {
                                        Log.i("TAG", "No data found for category.");
                                    }
                                },
                                throwable -> {
                                    Log.e("TAG", "Error fetching category items: ", throwable);
                                    Toast.makeText(requireContext(), "Error fetching category items", Toast.LENGTH_SHORT).show();
                                }
                        )
        );
    }

    private void setupTextChangeObserver() {
        Disposable textObservableDisposable = RxTextView.textChanges(editText)
                .debounce(1, TimeUnit.SECONDS)
                .map(CharSequence::toString)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        query -> {
                            List<CountryItems> filteredItems = filterProducts(query);
                            mealAdapter.updateMeals(filteredItems, requireContext(), this);
                        },
                        throwable -> {
                            Toast.makeText(requireContext(), "Error while filtering: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("TAG", "Error while filtering: ", throwable);
                        }
                );
        compositeDisposable.add(textObservableDisposable);
    }

    private List<CountryItems> filterProducts(String query) {
        if (isCountrySearch) {
            return countryItemsList.stream()
                    .filter(product -> product.getStrMealForCountryItem().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        } else {
            return categoryItemsList.stream()
                    .filter(product -> product.getStrMealForCountryItem().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void onClick(CountryItems countryItems) {
        if (countryItems != null && countryItems.getIdMealForCountryItem() != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("mealId", countryItems);
            Navigation.findNavController(requireView()).navigate(R.id.action_countrySelectionFragment_to_mealDetailsFragment, bundle);
        } else {
            Toast.makeText(requireContext(), "Meal data is missing!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setDataForCountryItems(List<CountryItems> meals) {
        // Implement if needed
    }

    @Override
    public void setErrorMessageCountryItems(String message) {
        // Implement if needed
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
    }
}
