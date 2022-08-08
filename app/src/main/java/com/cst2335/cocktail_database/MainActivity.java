package com.cst2335.cocktail_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    ArrayList <DrinkInfo> arrayDrinkInfo = new ArrayList<>();

    TextView ins;
    TextView in1;
    TextView in2;
    TextView in3;
    ImageView photo;
    ProgressBar progressBar;


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

                MyHTTPRequest req = new MyHTTPRequest();
                req.execute("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + editSearch);  //Type 1

                useAdapter();
            }


        });
    }

    /**
     * method is to add info into object
     */
    public void setSearchData(String drinkName) {

        arrayDrinkInfo.add(new DrinkInfo(drinkName));

    }


    public void useAdapter() {
        RVAdapter adapter = new RVAdapter(this, arrayDrinkInfo);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        search.setText("");
    }

    private class MyHTTPRequest extends AsyncTask<String, Integer, String> {
        String pic ;
        String inst;
        String ing1;
        String ing2;
        String ing3;
        Bitmap bmp;
        String drinkName;

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
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString(); //result is the whole string


                // convert string to JSON: Look at slide 27:
                JSONObject drinksReport = new JSONObject(result);
                JSONArray drinksArray = drinksReport.getJSONArray("drinks");

              //do{
                   // JSONObject obj = drinksArray.getJSONObject(id);

              // }while (drinksReport.getBoolean("drinks"));



                for(int i =0; i < drinksArray.length(); i++) {
                    JSONObject obj = drinksArray.getJSONObject(i);
                    drinkName = obj.getString("strDrink");
                    setSearchData(drinkName);
                    pic = obj.getString("strDrinkThumb");
                    publishProgress(100);
                    inst = obj.getString("strInstructions");
                    ing1 = obj.getString("strIngredient1");
                    ing2 = obj.getString("strIngredient2");
                    ing3 = obj.getString("strIngredient3");
                    publishProgress(150);

                    // bmp = BitmapFactory.decodeStream(response);

                    int j = 0; j++;
                }

            }
            catch (Exception e)
            {

            }
            return "Done";
        }

        public void onPostExecute(String fromDoInBackground)
        {
            arrayDrinkInfo.add(new DrinkInfo("drinkName"));
            ins.setText(inst);
            in1.setText(ing1);
            in2.setText(ing2);
            in3.setText(ing3);
            //    photo.setImageURI(Uri.parse(pic));
            //photo.setImageURI(Uri.parse(pic));


        }
        }


    }



