package com.example.foodplanerapp.network;



import com.example.foodplanerapp.model.ProductsResponse;

public interface NetworkCallBack {
    void onProductSuccess(ProductsResponse products);
    void onProductFailure(String message);

}
