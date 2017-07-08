package com.example.android.recipies;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Evi on 6/12/2017.
 */

public class JsonResponse extends RealmObject{

    @SerializedName("status")
    private String Status;

    @SerializedName("recipe_data")
    private RealmList<Dishes> dishes;


    public JsonResponse(){

    }
    public JsonResponse(String Status, RealmList<Dishes> dishes){
        this.Status= Status;
        this.dishes= dishes;

    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setDishes(RealmList<Dishes> dishes) {
        this.dishes = dishes;
    }


    public String getStatus() {
        return Status;
    }

    public RealmList<Dishes> getDishes() {
        return dishes;
    }
}
