package com.example.android.recipies;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Evi on 6/12/2017.
 */

public interface ApiInterface {

    @GET("android_task.php")
    Call<JsonResponse> getRecepies();
}
