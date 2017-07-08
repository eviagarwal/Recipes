package com.example.android.recipies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RecipeDetails extends AppCompatActivity {

    private TextView recipeName;
    private List<Ingredient> ingredients;
   // private Bundle bundle;
    private LinearLayout list;
    private Realm realm;
    private ImageButton img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        realm= Realm.getDefaultInstance();
        img= (ImageButton)findViewById(R.id.img_btn);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayInputDialog();
            }
        });


        recipeName= (TextView)findViewById(R.id.recipe_name);
        recipeName.setText(getIntent().getStringExtra("name"));
       // bundle= getIntent().getExtras();
        //ingredients= (ArrayList<Ingredient>) bundle.getSerializable("ingredients");
        RealmResults<Dishes> result2 = realm.where(Dishes.class)
                .equalTo("Name", getIntent().getStringExtra("name"))
                .findAll();
        //Dishes dish= (Dishes) realm.copyFromRealm(result2);
        for(Dishes dish: result2){
        ingredients= dish.getIngredients();
        }
        list= (LinearLayout)findViewById(R.id.list);
        char a= 'a';

        for(int i=0; i<ingredients.size(); i++){


            TextView txt= new TextView(this);
            txt.setTextSize(20);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,15,0,15);

            txt.setLayoutParams(params);
            txt.setText(a+".  "+ ingredients.get(i).getName());
            list.addView(txt);

            int ascii= ((int) a)+1;
            a= (char)ascii;

        }

    }

    private void displayInputDialog() {
        AlertDialog.Builder mBuilder= new AlertDialog.Builder(RecipeDetails.this);
        View mView= getLayoutInflater().inflate(R.layout.item_delete, null);
        mBuilder.setView(mView);
        final AlertDialog dialog= mBuilder.create();
        dialog.show();
        final EditText r_name= (EditText)mView.findViewById(R.id.edit_dish);
        r_name.setText(recipeName.getText());
        Button d_button=(Button)mView.findViewById(R.id.button2);
        d_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Dishes> result = realm.where(Dishes.class).equalTo("Name",(String) recipeName.getText() ).findAll();
                        result.deleteAllFromRealm();
                    }
                });
                Intent intent = new Intent(RecipeDetails.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Button r_button=(Button)mView.findViewById(R.id.button3);
        r_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rec_name= r_name.getText().toString().trim();
              //  Dishes dish = new Dishes(rec_name);
                Dishes dish = realm.where(Dishes.class).equalTo("Name", (String) recipeName.getText()).findFirst();
                realm.beginTransaction();
                dish.setName(rec_name);
                realm.commitTransaction();
                recipeName.setText(rec_name);
                dialog.dismiss();
               /* RealmResults<Dishes> RealmDishes= realm.where(Dishes.class).findAll();
                List<Dishes> realmDishes = realm.copyFromRealm(RealmDishes);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(new RecipiesAdapter(realmDishes, R.layout.recepie_item, getApplicationContext()));
                //d.dismiss();*/



            }
        });

        // d.show();

    }
}
