package com.example.foodplanerapp.model;

import java.util.List;

public class ProductsResponse {
    List<Product> categories;


    public ProductsResponse() {
    }

    public ProductsResponse(List<Product>allProducts) {
        this.categories = allProducts;
    }

    public List<Product> getAllProducts() {
        return categories;
    }

    public void setAllProducts(List<Product> allProducts) {
        this.categories = allProducts;
    }
}
