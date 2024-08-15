package com.example.foodplanerapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanerapp.databinding.FragmentGalleryBinding;
import com.example.foodplanerapp.databinding.FragmentPlanBinding;
import com.example.foodplanerapp.model.AppDatabase;
import com.example.foodplanerapp.model.MealDto;
import com.example.foodplanerapp.model.PlanDto;
import com.example.foodplanerapp.network.ProductRemoteDataSource;
import com.example.foodplanerapp.network.Repo;
import com.example.foodplanerapp.presenter.FavPresenter;
import com.example.foodplanerapp.presenter.PlanPresenter;
import com.example.foodplanerapp.ui.FavAdapter;
import com.example.foodplanerapp.ui.OnPlanDelete;
import com.example.foodplanerapp.ui.PlanAdapter;
import com.example.foodplanerapp.ui.PlanView;
import com.example.foodplanerapp.ui.gallery.GalleryFragment;
import com.google.android.material.chip.Chip;

import java.util.List;


public class PlanFragment extends Fragment implements PlanView, OnPlanDelete {


    private FragmentPlanBinding binding;
    private RecyclerView recyclerView;
    private PlanPresenter planPresenter;
    private List<PlanDto> planDtos;
    private List<PlanDto> days;
    private List<PlanDto> Monday;
    private TextView textViewForPlan;
    private Chip sunday,monday,tuesday,wednesday,thursday,friday,saturday,allWeekPlan;
    private String checkDay;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentPlanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        recyclerView = binding.planRecyclerView;

        planPresenter = new PlanPresenter(this, Repo.getInstance(ProductRemoteDataSource.getInstance(), AppDatabase.getInstance(requireContext())));
        planPresenter.getAllPlanData();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewForPlan = binding.textViewForPlan;

        sunday=binding.chipSunday;
        monday=binding.chipMonday;
        thursday=binding.chipThursday;
        tuesday=binding.chipTuesday;
        wednesday=binding.chipWednesday;
        friday=binding.chipFriday;
        saturday=binding.chipSaturday;
        allWeekPlan = binding.AllPlans;

        sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDay = (String) sunday.getText();
                textViewForPlan.setText(checkDay+" Plan");
                planPresenter.getPlanDataByDay(checkDay);
            }
        });
        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDay = (String) monday.getText();
                textViewForPlan.setText(checkDay+" Plan");
                planPresenter.getPlanDataByDay(checkDay);
            }
        });
        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDay = (String) thursday.getText();
                textViewForPlan.setText(checkDay+" Plan");
                planPresenter.getPlanDataByDay(checkDay);
            }
        });
        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDay = (String) tuesday.getText();
                textViewForPlan.setText(checkDay+" Plan");
                planPresenter.getPlanDataByDay(checkDay);
            }
        });
        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDay = (String) wednesday.getText();
                textViewForPlan.setText(checkDay+" Plan");
                planPresenter.getPlanDataByDay(checkDay);
            }
        });
        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkDay = (String) friday.getText();
                textViewForPlan.setText(checkDay+" Plan");
                planPresenter.getPlanDataByDay(checkDay);
            }
        });
        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDay = (String) friday.getText();
                textViewForPlan.setText(checkDay+" Plan");
                planPresenter.getPlanDataByDay(checkDay);
            }
        });

        allWeekPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDay = (String) allWeekPlan.getText();
                textViewForPlan.setText(checkDay);
                planPresenter.getAllPlanData();
            }
        });
        // Additional initializations if needed
    }

//    @Override
//    public void setData(List<MealDto> mealDto) {
//        //mealDto.observe(getViewLifecycleOwner(), new Observer<List<MealDto>>() {
//        // @Override
//        // public void onChanged(List<MealDto> mealDto) {
//        Log.d("TAG", "setData: is there any data "+mealDto.size());
//        FavAdapter adapter = new FavAdapter(mealDto, requireContext(), GalleryFragment.this);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
//        //}
//        //});
//    }

//    @Override
//    public void setErrorMessage(String message) {
//        Toast.makeText(requireContext(), "Error while fetching data: " + message, Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    public void onClick(MealDto mealDto) {
//        presenter.delete(mealDto);
//        Toast.makeText(requireContext(), "Removed", Toast.LENGTH_SHORT).show();
//        presenter.getLocalData();  // Refresh the data after deletion
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }





    @Override
    public void onClick(PlanDto planDto) {
        planPresenter.deleteFromPlan(planDto);
        Toast.makeText(requireContext(), "Removed", Toast.LENGTH_SHORT).show();
        planPresenter.getAllPlanData();  // Refresh the data after deletion
    }

    @Override
    public void setData(List<PlanDto> planDtos) {
        Log.d("TAG", "setData: is there any data "+planDtos.size());
        PlanAdapter adapter = new PlanAdapter(planDtos, requireContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }



    @Override
    public void setErrorMessage(String message) {
        Toast.makeText(requireContext(), "Error while fetching data: " + message, Toast.LENGTH_SHORT).show();
    }
}