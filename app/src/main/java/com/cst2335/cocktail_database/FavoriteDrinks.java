package com.cst2335.cocktail_database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class FavoriteDrinks extends AppCompatActivity {
    RVAdapter adapter;
    RecyclerView recyclerView;

    //FG
    public static final String ITEM_SELECTED = "ITEM";
    public static final String ITEM_POSITION = "POSITION";
    public static final String ITEM_ID = "ID";

    SQLiteDatabase db;
    ArrayList<DrinkInfo> arrayDrinkInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_drinks);

        Button clickBtnFravor = findViewById(R.id.btnBack);
        //loadDataFromDatabase();

        //   Intent favoritePage = new Intent (this, MainActivity.class);
        clickBtnFravor.setOnClickListener(click -> {

            startActivity(new Intent(this, MainActivity.class));
        });

        Intent data = getIntent();
        String price = data.getStringExtra("price");
        String name = data.getStringExtra("name");

    }


    private void loadDataFromDatabase() {
        //get a database connection:
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase(); //This calls onCreate() if you've never built the table before, or onUpgrade if the version here is newer


        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        java.lang.String[] columns = {MyOpener.COL_ID, MyOpener.COL_NAME};
        //query all the results from the database:
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:

        int nameColIndex = results.getColumnIndex(MyOpener.COL_NAME);
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);

        //iterate over the results, return true if there is a next item:
        while (results.moveToNext()) {
            String name = results.getString(nameColIndex);
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            arrayDrinkInfo.add(new DrinkInfo(name));
            adapter = new RVAdapter(this, arrayDrinkInfo);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

        }
    }

}


