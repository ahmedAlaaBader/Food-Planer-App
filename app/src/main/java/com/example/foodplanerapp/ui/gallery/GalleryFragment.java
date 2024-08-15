package com.example.foodplanerapp.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanerapp.databinding.FragmentGalleryBinding;
import com.example.foodplanerapp.model.AppDatabase;
import com.example.foodplanerapp.model.MealDto;
import com.example.foodplanerapp.network.ProductRemoteDataSource;
import com.example.foodplanerapp.network.Repo;
import com.example.foodplanerapp.presenter.FavPresenter;
import com.example.foodplanerapp.ui.FavAdapter;
import com.example.foodplanerapp.ui.FavView;
import com.example.foodplanerapp.ui.onDelete;

import java.util.List;

import io.reactivex.Flowable;

public class GalleryFragment extends Fragment implements FavView, onDelete {

    private FragmentGalleryBinding binding;
    private RecyclerView recyclerView;
    private FavPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

       // final TextView textView = binding.textGallery;


        recyclerView = binding.recyclerView;

        presenter = new FavPresenter(this, Repo.getInstance(ProductRemoteDataSource.getInstance(), AppDatabase.getInstance(requireContext())));
        presenter.getLocalData();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Additional initializations if needed
    }

    @Override
    public void setData(List<MealDto> mealDto) {
        //mealDto.observe(getViewLifecycleOwner(), new Observer<List<MealDto>>() {
           // @Override
           // public void onChanged(List<MealDto> mealDto) {
        Log.d("TAG", "setData: is there any data "+mealDto.size());
                FavAdapter adapter = new FavAdapter(mealDto, requireContext(), GalleryFragment.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            //}
        //});
    }

    @Override
    public void setErrorMessage(String message) {
        Toast.makeText(requireContext(), "Error while fetching data: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(MealDto mealDto) {
        presenter.delete(mealDto);
        Toast.makeText(requireContext(), "Removed", Toast.LENGTH_SHORT).show();
        presenter.getLocalData();  // Refresh the data after deletion
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
