package com.example.openweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;

public class Activity2 extends AppCompatActivity {

    TextView tv ;
    private RequestQueue queue;
    private static final String weatherurl="https://api.openweathermap.org/data/2.5/onecall?lat=41.8675766&lon=87.616232&exclude=minutely&appid=25974a74eff77d6afde92ab471d7f886";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        tv=findViewById(R.id.weather_data);
        queue = Volley.newRequestQueue(this);

    }

    public void fetchdata(View view){

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    tv.setText(MessageFormat.format("Response: {0}", response.toString()));
                    JSONArray weather = response.getJSONArray("weather");
                    String icon = ((JSONObject) weather.get(0)).getString("icon");
//                    runOnUiThread(() -> getIcon(icon));
//                    setTitle("Duration: " + (System.currentTimeMillis() - start));
                } catch (Exception e) {
                    tv.setText(MessageFormat.format("Response: {0}", e.getMessage()));
                }
            }
        };
        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data));
                    tv.setText(MessageFormat.format("Error: {0}", jsonObject.toString()));
//                    setTitle("Duration: " + (System.currentTimeMillis() - start));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, weatherurl,
                        null, listener, error);
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
}