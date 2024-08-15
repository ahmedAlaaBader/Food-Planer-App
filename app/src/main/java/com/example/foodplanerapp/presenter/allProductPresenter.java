package com.example.foodplanerapp.presenter;

import com.example.foodplanerapp.model.DailyMealResponse;
import com.example.foodplanerapp.ui.AllProductesView;
import com.example.foodplanerapp.model.Product;
import com.example.foodplanerapp.model.ProductsResponse;
import com.example.foodplanerapp.network.NetworkCallBack;
import com.example.foodplanerapp.network.Repo;
import com.example.foodplanerapp.ui.DailyMealView;

public class allProductPresenter implements  NetworkCallBack {


    AllProductesView AllProductesView;
    Repo repo;

    public allProductPresenter(Repo repo, AllProductesView AllProductesView){
        this.AllProductesView = AllProductesView;
        this.repo = repo;
    }

    public void makeNetwork(){
        repo.getAllProducts(this);
    }

    @Override
    public void onProductSuccess(ProductsResponse products) {
        AllProductesView.setData(products.getAllProducts());
    }

//    public void insert(Product product){
//        repo.insert(product);
//    }

    @Override
    public void onProductFailure(String message) {
        AllProductesView.setErrorMessage(message);
    }


}
