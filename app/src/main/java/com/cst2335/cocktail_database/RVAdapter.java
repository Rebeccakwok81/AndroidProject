package com.cst2335.cocktail_database;




import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.security.PublicKey;
import java.util.ArrayList;



public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {
    private ArrayList<String> drinksList;
    private LayoutInflater layout;



    public RVAdapter(Context context, ArrayList<String> drinksList){
        this.drinksList = drinksList;
        layout = LayoutInflater.from(context);

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
        View itemSearch = layout.inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemSearch);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.search.setText(drinksList.get(position));
/*
        holder.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(),"Press" + drinksList.get(holder.getLayoutPosition()) + "items", Toast.LENGTH_SHORT).show();

            }


        }); */
        }

    @Override
    public int getItemCount() {
        return drinksList.size();
    }
}