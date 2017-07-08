package com.example.android.recipies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.realm.Realm;


public class AddDish extends AppCompatActivity {
    private EditText r_name;
    private Button r_button;
    private Realm realm;
    private String rec_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        realm=Realm.getDefaultInstance();
        r_name= (EditText)findViewById(R.id.add_dish);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        r_button=(Button)findViewById(R.id.button);
        rec_name= r_name.getText().toString().trim();
        final Dishes dish = new Dishes(rec_name);
        dish.setName(rec_name);
        r_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*  realm.beginTransaction();
                realm.copyToRealm(dish);
                realm.commitTransaction();
                RealmResults<Dishes> RealmDishes = realm.where(Dishes.class).findAll();
                List<Dishes> realmDishes = realm.copyFromRealm(RealmDishes);
                recyclerView.setAdapter(new RecipiesAdapter(realmDishes, R.layout.recepie_item, getApplicationContext()));
                Intent intent = new Intent(AddDish.this,MainActivity.class);
                intent.putExtra("dish", rec_name);
                setResult(1,intent);
                finish();*/

            }
        });

    }
}
