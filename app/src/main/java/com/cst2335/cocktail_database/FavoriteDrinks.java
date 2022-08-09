package com.cst2335.cocktail_database;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class FavoriteDrinks extends AppCompatActivity {

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
        loadDataFromDatabase();
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
            arrayDrinkInfo.add(new DrinkInfo(name));
        }
    }

    /*
    protected void showContact(int position)
    {

        DrinkInfo selectedDrink = arrayDrinkInfo.get(position);
        View contact_view = getLayoutInflater().inflate(R.layout.contact_edit, null);
        //get the TextViews
        EditText rowName = contact_view.findViewById(R.id.row_name);
        TextView rowId = contact_view.findViewById(R.id.row_id);

        //set the fields for the alert dialog
        rowName.setText(selectedDrink.getDrinkName());

//Need to change with RecycleView still trying to get data from HTTP
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You clicked on item #" + position)
                .setMessage("You can update the fields and then click update to save in the database")
                .setView(contact_view) //add the 3 edit texts showing the contact information
                .setPositiveButton("Update", (click, b) -> {
                    selectedDrink.update(rowName.getText().toString());
/                 updateContact(selectedContact);
                    adapter.notifyDataSetChanged(); //the email and name have changed so rebuild the list
                })
                .setNegativeButton("Delete", (click, b) -> {
                    deleteDrink(selectedDrink); //remove the contact from database
                    arrayDrinkInfo.remove(position); //remove the contact from contact list
                    adapter.notifyDataSetChanged(); //there is one less item so update the list
                })
                .setNeutralButton("dismiss", (click, b) -> {
                })
                .create().show();
    }

*/
}