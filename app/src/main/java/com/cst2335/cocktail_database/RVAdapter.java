package com.cst2335.cocktail_database;

import static com.cst2335.cocktail_database.R.layout.list_items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {
    private ArrayList<String> drinksList = new ArrayList<>();



    public RVAdapter(ArrayList<String> drinks){
        drinksList = drinks;

    }

    public class  MyViewHolder extends RecyclerView.ViewHolder{
        private TextView search;

        public MyViewHolder(@NonNull View v) {
            super(v);
            search = v.findViewById(R.id.list_item_drink);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemSearch = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemSearch);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String drinkName = drinksList.get(position);
        holder.search.setText(drinkName);
    }


    @Override
    public int getItemCount() {
        return drinksList.size();
    }
}
