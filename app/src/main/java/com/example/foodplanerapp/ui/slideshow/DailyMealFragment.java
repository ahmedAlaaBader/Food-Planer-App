package com.example.foodplanerapp.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanerapp.databinding.FragmentDailyMealBinding;
import com.example.foodplanerapp.model.AppDatabase;
import com.example.foodplanerapp.model.DailyMeal;
import com.example.foodplanerapp.network.ProductRemoteDataSource;
import com.example.foodplanerapp.network.Repo;
import com.example.foodplanerapp.presenter.DailyMealPresnter;
import com.example.foodplanerapp.ui.DailyMealAdapter;
import com.example.foodplanerapp.ui.DailyMealView;

import java.util.List;

public class DailyMealFragment extends Fragment implements DailyMealView {

    private FragmentDailyMealBinding binding;
    private DailyMealPresnter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDailyMealBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize presenter
        presenter = new DailyMealPresnter(this, Repo.getInstance(ProductRemoteDataSource.getInstance(), AppDatabase.getInstance(requireContext())));

        // Set up RecyclerView
        binding.recyclerView3.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Make network request
        presenter.makeNetwork();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void setData(List<DailyMeal> meals) {
        DailyMealAdapter adapter = new DailyMealAdapter(meals, requireContext());
        binding.recyclerView3.setAdapter(adapter);
    }

    @Override
    public void setErrorMessage(String message) {
        Toast.makeText(requireContext(), "Error while fetching the data: " + message, Toast.LENGTH_SHORT).show();
    }
}
