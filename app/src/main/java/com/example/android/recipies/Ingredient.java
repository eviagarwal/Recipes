package com.example.android.recipies;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by Evi on 6/12/2017.
 */

public class Ingredient extends RealmObject implements Serializable  {


    @SerializedName("ingredient_id")
    private int id;

    @SerializedName("ingredient_name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
