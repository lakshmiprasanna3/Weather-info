package com.example.myweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText city;
    TextView result;
    TextView temperature;
    //https://api.openweathermap.org/data/2.5/weather?q=Jaipur&appid=17fdb77a3aff08add476987521034308


    String baseURL = "https://api.openweathermap.org/data/2.5/weather?q=";

    String API = "&appid=17fdb77a3aff08add476987521034308";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        city = findViewById(R.id.getCity);
        result = findViewById(R.id.result);
        temperature = findViewById(R.id.temperature);



        if(city.getText().toString()== null){
           city.setError("Please enter city name!!!");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myURL = baseURL + city.getText().toString() + API;
               // Log.i("URL","URL: "+ myURL);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject mainobj = response.getJSONObject("main");
                            String temp = String.valueOf(mainobj.getDouble("temp"));
                            String info = response.getString("weather");

                            Log.i("INFO", "INFO: " + info);
                            JSONArray ar = response.getJSONArray("weather");
                            for(int i=0 ; i < ar.length() ; i++){
                                JSONObject parObj = ar.getJSONObject(i);
                                String myWeather = parObj.getString("main");
                                result.setText(myWeather);


                                Log.i("ID", "ID: " + parObj.getString("id"));
                                Log.i("MAIN", "MAIN: " + parObj.getString("main"));
                            }
//                                double temp_int = Double.parseDouble(temp);
//                                double centi = (temp_int-32)/1.800;
//                                centi = Math.round(centi);
//                                int j = (int)centi;
                                  double cons = 274.15;
                                double temp_cel = Double.parseDouble(temp);
                                double centi = (temp_cel - cons);
                            centi = Math.round(centi);
                             int j = (int)centi;

                                temperature.setText(String.valueOf(j)+"°C");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });

                MySingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);

            }
        });





    }


}