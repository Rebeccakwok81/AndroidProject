package com.cst2335.cocktail_database;

import static com.cst2335.cocktail_database.MainActivity.ITEM_SELECTED;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DrinkOnClickActivity extends AppCompatActivity {

    TextView ins;
    TextView in1;
    TextView in2;
    TextView in3;
    ImageView photo;
    ProgressBar progressBar;
    String imgURL;
    Bitmap bmp;

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
        // getting drink name from the row
        String keywd = getIntent().getStringExtra("keywd");
        MyHTTPRequest req = new MyHTTPRequest();
        req.execute("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + keywd);  //Type 1

    }


    //Type1     Type2   Type3
    private class MyHTTPRequest extends AsyncTask<String, Integer, String> {

        String pic;
        String inst;
        String ing1;
        String ing2;
        String ing3;


        //Type3                Type1
        public String doInBackground(String... args) {
            try {

                //create a URL object of what server to contact:
                URL url = new URL(args[0]);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();


                //JSON reading:   Look at slide 26
                //Build the entire string response:
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
                    publishProgress(50);
                    inst = obj.getString("strInstructions");
                    ing1 = obj.getString("strIngredient1");
                    ing2 = obj.getString("strIngredient2");
                    ing3 = obj.getString("strIngredient3");
                    imgURL = obj.getString("strDrinkThumb");
                    publishProgress(100);


                    int j = 0;
                    j++;
                }

            } catch (Exception e) {

            }

            return "Done";
        }

        //Type 2
        public void onProgressUpdate(Integer... args) {

            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(args[0]);

        }

        //Type3
        public void onPostExecute(String fromDoInBackground) {


            ins.setText(inst);
            in1.setText(ing1);
            in2.setText(ing2);
            in3.setText(ing3);
            Picasso.get().load(imgURL).into(photo);

        }

    }

}




