package com.cst2335.cocktail_database;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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

public class DrinkOnClickActivity extends AppCompatActivity {

    TextView ins;
    TextView in1;
    TextView in2;
    TextView in3;
    ImageView photo;
    ProgressBar progressBar;
    ArrayList <DrinkInfo> drinkName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_drink_info);

        ins = findViewById(R.id.howToMake);
        in1 = findViewById(R.id.ing1);
        in2 = findViewById(R.id.ing2);
        in3 = findViewById(R.id.ing3);
        photo = findViewById(R.id.imageView);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        MyHTTPRequest req = new MyHTTPRequest();
        req.execute("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=gin");  //Type 1
    }



    //
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

        public void onProgressUpdate(Integer... args) {

            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(args[0]);

        }
/*
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

