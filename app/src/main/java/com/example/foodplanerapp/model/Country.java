package com.example.foodplanerapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import kotlin.jvm.internal.SerializedIr;

public class Country implements Serializable {
    @SerializedName("strArea")
    private  String strArea;

    public Country() {
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }
}
