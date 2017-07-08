package com.example.android.recipies;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Realm realm;
    private FloatingActionButton fab;
    private ApiInterface apiInterface;
   // public static final int RESULT= 1;
   // private TextView status;
private int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
realm = Realm.getDefaultInstance();
       // status= (TextView) findViewById(status);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent= new Intent(MainActivity.this,AddDish.class);
                startActivityForResult(intent, 1 );*/
                displayInputDialog();
            }
        });

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        flag = sp.getInt("your_int_key", -1);

        apiInterface= ApiClient.getClient().create(ApiInterface.class);

            Call<JsonResponse> call = apiInterface.getRecepies();
            call.enqueue(new Callback<JsonResponse>() {
                @Override
                public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {

                    // String Status= response.body().getStatus();
                    //status.setText(Status);

                    List<Dishes> dishes = response.body().getDishes();

                    if (flag== -1) {
                        for (Dishes dish : dishes) {
                            realm.beginTransaction();
                            realm.copyToRealm(dish);
                            realm.commitTransaction();
                            SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putInt("your_int_key", 1);
                            editor.commit();


                        }
                    }
                    RealmResults<Dishes> RealmDishes = realm.where(Dishes.class).findAll();
                    List<Dishes> realmDishes = realm.copyFromRealm(RealmDishes);
                    recyclerView.setAdapter(new RecipiesAdapter(realmDishes, R.layout.recepie_item, getApplicationContext()));

                }

                @Override
                public void onFailure(Call<JsonResponse> call, Throwable t) {

                }
            });

       RealmResults<Dishes> RealmDishes= realm.where(Dishes.class).findAll();
        List<Dishes> realmDishes = realm.copyFromRealm(RealmDishes);
        recyclerView.setAdapter(new RecipiesAdapter(realmDishes, R.layout.recepie_item, getApplicationContext()));
    }


   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        if(requestCode==1){
            String rec_name= data.getStringExtra("dish");
            RealmList<Ingredient> ing= null;
            Dishes dish1= new Dishes(14,rec_name,ing);
            realm.beginTransaction();
            realm.copyToRealm(dish1);
            realm.commitTransaction();
            RealmResults<Dishes> RealmDishes= realm.where(Dishes.class).findAll();
            List<Dishes> realmDishes = realm.copyFromRealm(RealmDishes);
            recyclerView.setAdapter(new RecipiesAdapter(realmDishes, R.layout.recepie_item, getApplicationContext()));
        }
    }*/
    private void displayInputDialog() {
        AlertDialog.Builder mBuilder= new AlertDialog.Builder(MainActivity.this);
        View mView= getLayoutInflater().inflate(R.layout.input_dialog, null);

      /* final Dialog d = new Dialog(this);
        d.setTitle("Save To Realm");
        d.setContentView(R.layout.input_dialog);*/
        mBuilder.setView(mView);
        final AlertDialog dialog= mBuilder.create();
        dialog.show();
         final EditText r_name= (EditText)mView.findViewById(R.id.add_dish);
         Button r_button=(Button)mView.findViewById(R.id.button);
        r_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rec_name= r_name.getText().toString().trim();
                Dishes dish = new Dishes(rec_name);
                realm.beginTransaction();
                realm.copyToRealm(dish);
                realm.commitTransaction();
                RealmResults<Dishes> RealmDishes= realm.where(Dishes.class).findAll();
                List<Dishes> realmDishes = realm.copyFromRealm(RealmDishes);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(new RecipiesAdapter(realmDishes, R.layout.recepie_item, getApplicationContext()));
                //d.dismiss();
                dialog.dismiss();


            }
        });

       // d.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        RealmResults<Dishes> RealmDishes= realm.where(Dishes.class).findAll();
        List<Dishes> realmDishes = realm.copyFromRealm(RealmDishes);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(new RecipiesAdapter(realmDishes, R.layout.recepie_item, getApplicationContext()));
    }
}
