package com.example.openweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    List<Weather> list;
    private RequestQueue queue;
    private static final String weatherUrl="https://api.openweathermap.org/data/2.5/onecall?lat=41.8675766&lon=-87.616232&exclude=minutely&appid=25974a74eff77d6afde92ab471d7f886";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        list = new ArrayList<>();


        //Changing the action bar colour
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));


        //Loading json data using volley
        queue = Volley.newRequestQueue(this);
        queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, weatherUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                for(int i=0 ; i < response.length();i++){
                    try {
                        JSONObject weatherObject  = response.getJSONObject(String.valueOf(i));
                        Weather weather = new Weather();
                        weather.setDay(weatherObject.getString("hourly[i].dt"));
                        weather.setDescription(weatherObject.getString("hourly[i].weather.description"));
                        weather.setTemperature(weatherObject.getString("hourly[i].temp"));
                        weather.setTime(weatherObject.getString("hourly[i].dt"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new Adapter(getApplicationContext(),list);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error in data loading", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opt_menu, menu);
        return true;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.opt_temp_format){
            Toast.makeText(this, "Temp_selected", Toast.LENGTH_SHORT).show();
//            int[] images = {R.drawable.units_c,R.drawable.units_f};
            if(item.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.units_c).getConstantState())){
                item.setIcon(R.drawable.units_f);
            }
            else if(item.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.units_f).getConstantState())){
                item.setIcon(R.drawable.units_c);
            }
            else{
                item.setIcon(R.drawable.units_f);
            }

        }
        else if(item.getItemId() == R.id.opt_calendar){

            Intent intent = new Intent(MainActivity.this,Activity2.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.opt_location){
            generate_dialogue();

        }
        else{
            Toast.makeText(this, "No such selection", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }



    public void generate_dialogue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText et = new EditText(this);
        et.setInputType(InputType.TYPE_CLASS_TEXT);
        et.setGravity(Gravity.CENTER_HORIZONTAL);
        builder.setView(et);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Toast.makeText(MainActivity.this, "Location_selected", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(MainActivity.this, "You changed your mind!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setMessage("For US : 'City' or 'City,State'\n\nInternational Locations: 'City,Country' ");
        builder.setTitle("Enter a Location");

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}