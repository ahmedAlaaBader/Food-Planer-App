package com.example.foodplanerapp.ui;

import com.example.foodplanerapp.model.Product;

import java.util.List;

public interface AllProductesView {
    public void setData(List<Product> allProducts);
    public void setErrorMessage(String message);
}
