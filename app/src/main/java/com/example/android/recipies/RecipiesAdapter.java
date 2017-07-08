package com.example.android.recipies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Evi on 6/12/2017.
 */

public class RecipiesAdapter extends RecyclerView.Adapter<RecipiesAdapter.MyViewHolder> {

    private List<Dishes> dishes;
    private int rowLayout;
    private Context context;


    public RecipiesAdapter(List<Dishes> dishes, int rowLayout, Context context){

        this.dishes= dishes;
        this.rowLayout= rowLayout;
        this.context= context;

    }


    @Override
    public RecipiesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyViewHolder(view, context, dishes);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        int num= position+1;
        holder.recepie_name.setText(num+". "+dishes.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Realm realm;


        private TextView recepie_name;
        private Context context;
        private List<Dishes> dishes;
        public MyViewHolder(View itemView, Context context, List<Dishes> dishes) {
            super(itemView);
            this.dishes= dishes;
            this.context= context;
            itemView.setOnClickListener(this);
            recepie_name= (TextView) itemView.findViewById(R.id.recipie_name);


        }

        @Override
        public void onClick(View v) {
            int position= getAdapterPosition();
            Dishes dish= this.dishes.get(position);
            String recipeName= dish.getName();
            //List<Ingredient> ingredients= dish.getIngredients();

            Intent intent= new Intent(this.context, RecipeDetails.class);
            //Bundle bundle= new Bundle();
            //bundle.putSerializable("ingredients",ingredients);

            intent.putExtra("name", recipeName);
            //intent.putExtras(bundle);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.context.startActivity(intent);

        }


    }


}
