package com.cst2335.cocktail_database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.ArrayList;

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


    //Android Class
    RecyclerView recyclerView;

    //hold search input
    EditText search;

    //hold search input with plus sign
    String editSearch;

    SQLiteDatabase db;

    //list of drink from HTTP
    ArrayList <DrinkInfo> arrayDrinkInfo = new ArrayList<>();

    //Globel variable for AsyncTask
    TextView ins;
    TextView in1;
    TextView in2;
    TextView in3;
    ImageView photo;
    ProgressBar progressBar;
    ArrayList <String> drinkName = new ArrayList<>();

    RVAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //rvDrink is in activity_main.xml
        recyclerView = findViewById(R.id.rvDrink);


        //btnSearch(search button) is in activity_main
        Button clickBtnSearch = findViewById(R.id.btnSearch);

       // loadDataFromDatabase();

        //Causing Crash
        // progressBar = findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.VISIBLE);

        RVAdapter adapter;

        //When search is click text in the EditText is pass to the list with plus sign in space
        clickBtnSearch.setOnClickListener(click -> {


            //etSearch(EditText for search) is in activity_main
            search = findViewById(R.id.etSearch);
            editSearch = search.getText().toString();

            //check search is not empty
            if (editSearch != null) {
                //Replace space in search with add sign require by API fromate
                editSearch = editSearch.replaceAll("\\s", "+");

                MyHTTPRequest req = new MyHTTPRequest();
                req.execute("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + editSearch);  //Type 1
                setData();

            }


        });
    }
    public void setData () {
        for (int i = 0; i < drinkName.size(); i++) {
            arrayDrinkInfo.add(new DrinkInfo(drinkName.get(i)));
            System.out.println(drinkName.get(i));
            String temp = drinkName.get(i);
            System.out.println(arrayDrinkInfo.get(i).getDrinkName());
            useAdapter();
        }
        }

/*
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
*/
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

        public void useAdapter () {
            adapter = new RVAdapter(this, arrayDrinkInfo);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            search.setText("");
        }

        private class MyHTTPRequest extends AsyncTask<String, Integer, String> {
            String pic;
            String inst;
            String ing1;
            String ing2;
            String ing3;
            Bitmap bmp;
            String name;

            @Override
            protected String doInBackground(String... args) {
                try {

                    //create a URL object of what server to contact:
                    URL url = new URL(args[0]);

                    //open the connection
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    //wait for data:
                    InputStream response = urlConnection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    String result = sb.toString(); //result is the whole string


                    // convert string to JSON: Look at slide 27:
                    JSONObject drinksReport = new JSONObject(result);
                    JSONArray drinksArray = drinksReport.getJSONArray("drinks");

                    for (int i = 0; i < drinksArray.length(); i++) {
                        JSONObject obj = drinksArray.getJSONObject(i);
                        pic = obj.getString("strDrinkThumb");

                        publishProgress(50);
                        drinkName.add(obj.getString("strDrink"));
                        name = obj.getString("strDrink");
                        inst = obj.getString("strInstructions");
                        ing1 = obj.getString("strIngredient1");
                        ing2 = obj.getString("strIngredient2");
                        ing3 = obj.getString("strIngredient3");
                        publishProgress(100);


                        // bmp = BitmapFactory.decodeStream(response);

                        int j = 0;
                        j++;
                    }


                } catch (Exception e) {

                }

                return "Done";
            }

        /*
        public void onProgressUpdate(Integer ... args)
        {

            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(args[0]);

        }
        //causing crash
        public void onPostExecute(String fromDoInBackground)
        {
            ins = findViewById(R.id.howToMake);
            in1 = findViewById(R.id.ing1);
            in2 = findViewById(R.id.ing2);
            in3 = findViewById(R.id.ing3);
            photo = findViewById(R.id.imageView);
            ins.setText(inst);
            in1.setText(ing1);
            in2.setText(ing2);
            in3.setText(ing3);
            //    photo.setImageURI(Uri.parse(pic));
            //photo.setImageURI(Uri.parse(pic));
        }
*/
        }


}
