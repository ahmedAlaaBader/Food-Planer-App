package com.example.foodplanerapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanerapp.R;
import com.example.foodplanerapp.databinding.FragmentHomeBinding;
import com.example.foodplanerapp.model.AppDatabase;
import com.example.foodplanerapp.model.Country;
import com.example.foodplanerapp.model.CountryItems;
import com.example.foodplanerapp.model.CountryResponse;
import com.example.foodplanerapp.model.AppDatabase;
import com.example.foodplanerapp.model.Product;
import com.example.foodplanerapp.network.ProductRemoteDataSource;
import com.example.foodplanerapp.network.Repo;
import com.example.foodplanerapp.presenter.CountryPresenter;
import com.example.foodplanerapp.presenter.allProductPresenter;
import com.example.foodplanerapp.ui.AllProductesView;
import com.example.foodplanerapp.ui.AllProductsAdapter;
import com.example.foodplanerapp.ui.CountryAdapter;
import com.example.foodplanerapp.ui.CountryView;
import com.example.foodplanerapp.ui.OnCountryClicked;
import com.example.foodplanerapp.ui.onFavClickListener;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeFragment extends Fragment implements AllProductesView, CountryView, OnCountryClicked,onFavClickListener {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewCountry;
    private allProductPresenter presenter;
    private CountryPresenter countryPresenter;
    private AllProductsAdapter allProductsAdapter;
    private CountryAdapter countryAdapter;
    private EditText editText;
    private List<Product> products = new ArrayList<>();
    private List<Country> countries = new ArrayList<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = binding.recyclerView;
        recyclerViewCountry = binding.countryRecyclerView;
        editText = binding.editText3;

        presenter = new allProductPresenter(Repo.getInstance(ProductRemoteDataSource.getInstance(), AppDatabase.getInstance(requireContext())), this);
        countryPresenter = new CountryPresenter(Repo.getInstance(ProductRemoteDataSource.getInstance(), AppDatabase.getInstance(requireContext())), this);

        // Initializing the adapters
        allProductsAdapter = new AllProductsAdapter(products, requireContext(),this); //**
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(allProductsAdapter);

        countryAdapter = new CountryAdapter(new ArrayList<>(), requireContext(), this);
        recyclerViewCountry.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCountry.setAdapter(countryAdapter);
        presenter.makeNetwork();

        compositeDisposable.add(
                countryPresenter.getCountryResponseSingle()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                countryResponse -> {
                                    countries.clear();
                                    countries.addAll(countryResponse.getCuisines());
                                    countryAdapter.updateCountries(countries, requireContext(), this);
                                },
                                throwable -> Log.i("TAG", "showCategoryDetail: unable to show products because: " + throwable.getMessage())
                        )
        );



        // RxJava Observable for text changes
        Disposable textObservableDisposable = RxTextView.textChanges(editText)
                .debounce(1, TimeUnit.SECONDS)
                .map(CharSequence::toString)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                    List<Product> filteredProducts = filterProducts(query);
                    List<Country> filteredCountries = filterCountries(query);

                    allProductsAdapter.updateCategories(filteredProducts, requireContext(), this);
                    countryAdapter.updateCountries(filteredCountries, requireContext(), this);
                }, throwable -> {
                    Toast.makeText(requireContext(), "Error while filtering: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    throwable.printStackTrace();
                });

        // Adding to CompositeDisposable to handle lifecycle
        compositeDisposable.add(textObservableDisposable);
    }

    private List<Product> filterProducts(String query) {
        return products.stream()
                .filter(product -> product.getStrCategory().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<Country> filterCountries(String query) {
        return countries.stream()
                .filter(country -> country.getStrArea().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void setData(List<Product> allProducts) {
        this.products.clear();
        this.products.addAll(allProducts);
        allProductsAdapter.updateCategories(allProducts, requireContext(), this);
        allProductsAdapter.notifyDataSetChanged();
    }

    @Override
    public void setErrorMessage(String message) {
        Toast.makeText(requireContext(), "Error while fetching the data: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(Product product) {
//        Toast.makeText(requireContext(), "Inserted successfully", Toast.LENGTH_SHORT).show();
//        presenter.insert(product);
    }

    @Override
    public void setDataForCountry(List<Country> allCountries) {
        this.countries.clear();
        this.countries.addAll(allCountries);
        countryAdapter.updateCountries(allCountries, requireContext(), this);
        countryAdapter.notifyDataSetChanged();
    }

    @Override
    public void setErrorMessageForCountry(String message) {
        Toast.makeText(requireContext(), "Error while fetching the countries: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        compositeDisposable.clear(); // Dispose of all RxJava subscriptions
    }

    @Override
    public void onCountryClicked(Country country) {
        if (country != null && country.getStrArea() != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("country", country);
            Navigation.findNavController(requireView()).navigate(R.id.action_nav_home_to_countrySelectionFragment, bundle);
        } else {
            Toast.makeText(requireContext(), "Country data is missing!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCatClicked(Product cat) {
        if (cat != null && cat.getStrCategory() != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("country", cat);
            Navigation.findNavController(requireView()).navigate(R.id.action_nav_home_to_countrySelectionFragment, bundle);
            Toast.makeText(requireContext(), "cat data send", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "cat data is missing!", Toast.LENGTH_SHORT).show();
        }
    }
}

