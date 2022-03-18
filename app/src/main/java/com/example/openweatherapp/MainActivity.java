package com.example.openweatherapp;

import static android.content.ContentValues.TAG;

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
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

    TextView CurCityState , CurCityTime , CurSunrise , CurSunset;
    TextView CurHumidity , CurTemp , CurFeelsLike , CurWind , CurUvi , CurVisibility;
    RecyclerView recyclerView;
    Adapter adapter;
    List<Weather> list;
    public  JSONObject current_data = new JSONObject();
    private RequestQueue queue;
    private static final String weatherUrl="https://api.openweathermap.org/data/2.5/onecall?lat=41.8675766&lon=-87.616232&exclude=minutely&appid=25974a74eff77d6afde92ab471d7f886";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CurCityState = findViewById(R.id.city_state);
        CurCityTime = findViewById(R.id.city_time);
        recyclerView = findViewById(R.id.recyclerview);
        CurSunrise = findViewById(R.id.cur_sunrise);
        CurSunset = findViewById(R.id.cur_sunset);
        CurHumidity = findViewById(R.id.cur_humidity);
        CurTemp = findViewById(R.id.cur_temp);
        CurFeelsLike = findViewById(R.id.cur_feels_like);
        CurWind= findViewById(R.id.cur_winddpeed_winddeg_windgust);
        CurUvi= findViewById(R.id.cur_uvi);
        CurVisibility= findViewById(R.id.cur_visibility);
        list = new ArrayList<>();


        //Changing the action bar colour
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));


        //Loading json data using volley
        queue = Volley.newRequestQueue(this);
        queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, weatherUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    current_data = response.getJSONObject("current");
                    CurCityState.setText(response.getString("lat")+"--"+response.getString("lon"));
                    setdata();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    public void setdata() throws JSONException {
//        CurCityState.setText(response.getString("lon"));//+"--"+current_data.getString("lon"));
        CurCityTime.setText(current_data.getString("dt"));
        CurSunrise.setText(current_data.getString("sunrise"));
        CurSunset.setText(current_data.getString("sunset"));
        CurHumidity.setText(current_data.getString("humidity")+"%");
        CurFeelsLike.setText(current_data.getString("feels_like"));
        CurTemp.setText(current_data.getString("temp"));
        CurUvi.setText(current_data.getString("uvi"));
        CurVisibility.setText(current_data.getString("visibility"));
        CurWind.setText(current_data.getString("wind_speed")+"/"+current_data.getString("wind_deg")+"/"+current_data.getString("wind_gust"));

    }
}