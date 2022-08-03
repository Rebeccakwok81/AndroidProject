package com.cst2335.cocktail_database;

import androidx.appcompat.app.AppCompatActivity;

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

    ArrayList<String> pic = new ArrayList<>();
    ArrayAdapter <String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list =  (ListView) findViewById(R.id.listDrink);
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, pic);

        Button clickBtnSearch = findViewById(R.id.btnSearch);
         clickBtnSearch.setOnClickListener ( click-> {

             EditText search = findViewById(R.id.etSearch);
             String editSearch=search.getText().toString() ;

            if(editSearch != null)
                 editSearch = editSearch.replaceAll("\\s","+");

             pic.add(editSearch);
             list.setAdapter(arrayAdapter);
             arrayAdapter.notifyDataSetChanged();
         });
    }


    /*
    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return pic.size();
        }

        @Override
        public Object getItem(int i) {
            return pic.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater inflater = getLayoutInflater();
            View newView;
            TextView tView;

            newView = inflater.inflate(R.layout.listDrink, viewGroup, false);
            tView = newView.findViewById(R.id.etSearch);
            tView.setText(getItem(i).toString());

            return newView;
        }
    }

     */
}