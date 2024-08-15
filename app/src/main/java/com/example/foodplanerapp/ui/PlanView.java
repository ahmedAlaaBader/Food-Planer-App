package com.example.foodplanerapp.ui;



import com.example.foodplanerapp.model.PlanDto;

import java.util.List;

public interface PlanView {
    public void setData(List<PlanDto> planDtos);

    public void setErrorMessage(String message);
}
