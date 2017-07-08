package com.example.android.recipies;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Evi on 6/12/2017.
 */

public class Dishes extends RealmObject {

    @SerializedName("id")
    private int Id;

    @SerializedName("name")
    private String Name;

    @SerializedName("ingredient_data")
    private RealmList<Ingredient> Ingredients;

    public Dishes(){

    }
    public Dishes(String name){
        Name= name;

    }

    public Dishes(int id, String name, RealmList<Ingredient> ingredients){
        Id= id;
        Name= name;
        Ingredients= ingredients;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public RealmList<Ingredient> getIngredients() {
        return Ingredients;
    }

    public void setIngredients(RealmList<Ingredient> ingredients) {
        Ingredients = ingredients;
    }
}
