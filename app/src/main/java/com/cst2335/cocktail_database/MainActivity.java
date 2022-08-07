package com.cst2335.cocktail_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
    Your application should have an EditText for entering the name of a drink. There should also be a “search”
button which sends the search term to the server and returns a list of drinks that match that name.

    The URL for searching is “https://www.thecocktaildb.com/api/json/v1/1/search.php?s=....”
where … is the name that the user typed in.

    Your application must show a list of results in a RecyclerView, with each row showing a different result returned.

    If the user selects a drink from the list, your application will show the thumbnail picture,
the instructions for making the drink, and the first 3 ingredients from the list of ingredients.

    Also in the detail fragment, the user can save the data to the device for offline viewing.
The user must be able to view a list of their saved data and remove them from the database of favourites if they choose.

The SharedPreferences should save the user’s search words so that the next time you start the application,
the previous search term is shown.
*/

public class MainActivity extends AppCompatActivity {

    //RecyclerView Objects
    ArrayList<Contact> contactsList = new ArrayList<>();

    //Android Class
    RecyclerView recyclerView;
    //End of RecyclerView Objects

    //hold search input
    EditText search;

    //hold search input with plus sign
    String editSearch;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //rvDrink is in activity_main.xml
        recyclerView = findViewById(R.id.rvDrink);


        //btnSearch(search button) is in activity_main
        Button clickBtnSearch = findViewById(R.id.btnSearch);

        //When search is click text in the EditText is pass to the list with plus sign in space
        clickBtnSearch.setOnClickListener(click -> {


            //etSearch(EditText for search) is in activity_main
            search = findViewById(R.id.etSearch);
            editSearch = search.getText().toString();

            //check search is not empty
            if (editSearch != null) {
                //Replace space in search with add sign require by API fromate
                editSearch = editSearch.replaceAll("\\s", "+");

                setObject();
                useAdapter();
            }
        });
    }

    /**
     * method is to add info into object
     */
    public void setObject() {

        contactsList.add(new Contact(editSearch, id));
    }


    public void useAdapter(){
        RVAdapter adapter = new RVAdapter(this, contactsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        search.setText("");
    }

    private void loadDataFromDatabase()
    {
        //get a database connection:
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase(); //This calls onCreate() if you've never built the table before, or onUpgrade if the version here is newer


        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        java.lang.String[] columns = {MyOpener.COL_ID,  MyOpener.COL_NAME};
        //query all the results from the database:
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:

        int nameColIndex = results.getColumnIndex(MyOpener.COL_NAME);
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            String name = results.getString(nameColIndex);
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            contactsList.add(new Contact(name, id));
        }
    }
}

