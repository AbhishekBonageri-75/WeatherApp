package com.example.openweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TextView CurCityState , CurCityTime , CurSunrise , CurSunset;
    TextView CurHumidity , CurTemp , CurFeelsLike , CurWind , CurUvi , CurVisibility;
    TextView CurWeatherDescription , DailyMorn , DailyDay , DailyEve , DailyNight;
    ImageView icon , icon1 ,icon2,icon3,icon4,icon5,icon6;
    JSONObject current_data,weatherObject,dailyTempObject,t_response;
    ConstraintLayout cl;
//    RecyclerView recyclerView;
    private static final String TAG = "MainActivity";
    private RequestQueue queue;
    private static final String weatherUrl="https://api.openweathermap.org/data/2.5/onecall?lat=41.8675766&lon=-87.616232&exclude=minutely&appid=25974a74eff77d6afde92ab471d7f886";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cl = findViewById(R.id.constraint_layout);
        CurCityState = findViewById(R.id.city_state);
        CurCityTime = findViewById(R.id.city_time);
//        recyclerView = findViewById(R.id.recyclerview);
        CurSunrise = findViewById(R.id.cur_sunrise);
        CurSunset = findViewById(R.id.cur_sunset);
        CurHumidity = findViewById(R.id.cur_humidity);
        CurTemp = findViewById(R.id.cur_temp);
        CurFeelsLike = findViewById(R.id.cur_feels_like);
        CurWind= findViewById(R.id.cur_winddpeed_winddeg_windgust);
        CurUvi= findViewById(R.id.cur_uvi);
        CurVisibility= findViewById(R.id.cur_visibility);
        CurWeatherDescription=findViewById(R.id.cur_weather_description);
        DailyMorn = findViewById(R.id.d0_temp_morn);
        DailyDay = findViewById(R.id.d0_temp_day);
        DailyEve = findViewById(R.id.d0_temp_eve);
        DailyNight = findViewById(R.id.d0_temp_night);
        icon = findViewById(R.id.weather_icon);
        icon2 = findViewById(R.id.temperature_icon_2);
        icon3 = findViewById(R.id.temperature_icon_3);
        icon4 = findViewById(R.id.temperature_icon_4);
        icon5 = findViewById(R.id.temperature_icon_5);
        icon6 = findViewById(R.id.temperature_icon_6);
        icon1 = findViewById(R.id.temperature_icon_1);
//        list = new ArrayList<>();
        if(hasNetworkConnection() == false){
            ConstraintLayout layout = null;
            ViewGroup rootView=findViewById(R.id.constraint_layout);
            for(int i=0;i<rootView.getChildCount();i++){

                View view=rootView.getChildAt(i);

                if(view.getId()==R.id.city_state){

                    //Do something
                    Toast.makeText(this, "No internet Connection", Toast.LENGTH_LONG).show();
                    Toast.makeText(this, "id:"+view.getId(), Toast.LENGTH_LONG).show();
                    CurCityState.setText("No internet Connection");

                }else{

//                    Do something
//                    LinearLayout layout = findViewById("root");
//                    for(int e=0;e<layout.getChildCount();e++)
//                    {
//                        View v =  (View)layout.getChildAt(e);
//                        // hide `v`
//                        v.setVisibility(View.INVISIBLE);
//                    }
                }
            }
            return;
        }



        //Changing the action bar colour
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));

        //Loading json data using volley
        queue = Volley.newRequestQueue(this);
        queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, weatherUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    t_response=response;
                    //Current data
                    current_data = response.getJSONObject("current");

                    //Current.Weather data
                    JSONArray weatherArray = current_data.getJSONArray("weather");
                    weatherObject = weatherArray.getJSONObject(0);

                    //Daily data.
                    JSONArray dailyArray = response.getJSONArray("daily");
                    JSONObject dailyObject = dailyArray.getJSONObject(0);
                    dailyTempObject = dailyObject.getJSONObject("temp");

                    CurCityState.setText(GetCity());//GetCity();
                    setdata(current_data,weatherObject,dailyTempObject);
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

    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }


    public String GetCity(){
        StringBuilder sb = new StringBuilder();
        String citystate="";
        List<Address> addresses = null;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            double lat = Double.parseDouble(t_response.getString("lat"));
            double lon = Double.parseDouble(t_response.getString("lon"));
            addresses = geocoder.getFromLocation(lat, lon,10);

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        for (Address ad : addresses) {

            String a = String.format("%s %s ",

                    (ad.getLocality() == null ? "" : ad.getLocality()),
                    (ad.getCountryName() == null ? "" : ad.getAdminArea()));


            if (!a.trim().isEmpty())
                sb.append("").append(a.trim());
            break;
//            sb.append("\n");
        }
        citystate = sb.toString();
        return citystate;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opt_menu, menu);
        return true;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)  {

        if(item.getItemId() == R.id.opt_temp_format){
//            Toast.makeText(this, "Temp_selected", Toast.LENGTH_SHORT).show();
//            int[] images = {R.drawable.units_c,R.drawable.units_f};
            if(item.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.units_c).getConstantState())){
                item.setIcon(R.drawable.units_f);
                setTempIcons();

                try {
                    setdata(current_data,weatherObject,dailyTempObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else if(item.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.units_f).getConstantState())){
                item.setIcon(R.drawable.units_c);
                setTempIconsC();
                try {
                    setCelData(current_data,weatherObject,dailyTempObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else{
                item.setIcon(R.drawable.units_f);
            }

        }
        else if(item.getItemId() == R.id.opt_calendar){

//            Intent intent = new Intent(MainActivity.this,Activity2.class);
            Intent intent = new Intent(MainActivity.this,WeekWeather.class);
            intent.putExtra("title",GetCity());
            intent.putExtra("api",t_response.toString());
//            intent.putExtra()
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

    private void setTempIcons() {
        icon1.setImageResource(R.drawable.units_f);
        icon2.setImageResource(R.drawable.units_f);
        icon3.setImageResource(R.drawable.units_f);
        icon4.setImageResource(R.drawable.units_f);
        icon5.setImageResource(R.drawable.units_f);
        icon6.setImageResource(R.drawable.units_f);
    }
    private void setTempIconsC() {
        icon1.setImageResource(R.drawable.units_c);
        icon2.setImageResource(R.drawable.units_c);
        icon3.setImageResource(R.drawable.units_c);
        icon4.setImageResource(R.drawable.units_c);
        icon5.setImageResource(R.drawable.units_c);
        icon6.setImageResource(R.drawable.units_c);
    }


    public void generate_dialogue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText et = new EditText(this);
        et.setInputType(InputType.TYPE_CLASS_TEXT);
        et.setGravity(Gravity.CENTER_HORIZONTAL);
        builder.setView(et);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

//                Toast.makeText(MainActivity.this, "Location_selected", Toast.LENGTH_SHORT).show();
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
    @SuppressLint("SetTextI18n")
    public void setdata(JSONObject current_data,JSONObject weatherObject,JSONObject dailyTempObject ) throws JSONException {
        int id ;
        String w_icon="";

//        CurCityState.setText(response.getString("lat")+"--"+response.getString("lon"));
        CurCityTime.setText(convertTime(Long.parseLong(current_data.getString("dt")),1));
        CurSunrise.setText(convertTime(Long.parseLong(current_data.getString("sunrise")),2));
        CurSunset.setText(convertTime(Long.parseLong(current_data.getString("sunset")),3));
        CurHumidity.setText(current_data.getString("humidity")+"%");
        CurFeelsLike.setText(""+convertTemp(current_data.getString("feels_like")));
        CurTemp.setText(""+convertTemp(current_data.getString("temp")));
//        CurTemp.setText(current_data.getString("temp"));
        CurUvi.setText(current_data.getString("uvi"));
        CurVisibility.setText(""+Integer.parseInt(current_data.getString("visibility"))/1609+"_Mi");
        CurWeatherDescription.setText(weatherObject.getString("description"));

        //Wind gust data might not be there in the API response some times .
        try {
            CurWind.setText(current_data.getString("wind_speed")+"/"+ current_data.getString("wind_deg")+"/"+ current_data.getString("wind_gust"));
        }catch(Exception e){
            Toast.makeText(this, "No Wind Gust_data "+e, Toast.LENGTH_SHORT).show();
        }


//        Toast.makeText(MainActivity.this, "Weather.Icon:"+weatherObject.getString("icon"), Toast.LENGTH_LONG).show();

        w_icon = "_"+weatherObject.getString("icon");
        id = fetchIconId(w_icon);

        icon.setImageResource(id);
        //setting daily[0].temp data
        DailyMorn.setText(""+convertTemp(dailyTempObject.getString("morn")));
        DailyDay.setText(""+convertTemp(dailyTempObject.getString("day")));
        DailyEve.setText(""+convertTemp(dailyTempObject.getString("eve")));
        DailyNight.setText(""+convertTemp(dailyTempObject.getString("night")));

    }


    @SuppressLint("SetTextI18n")
    private void setCelData(JSONObject current_data, JSONObject weatherObject, JSONObject dailyTempObject) throws JSONException {
        CurTemp.setText(""+convertToC(current_data.getString("temp")));
        CurFeelsLike.setText(""+convertToC(current_data.getString("feels_like")));
        //setting daily[0].temp data
        DailyMorn.setText(""+convertToC(dailyTempObject.getString("morn")));
        DailyDay.setText(""+convertToC(dailyTempObject.getString("day")));
        DailyEve.setText(""+convertToC(dailyTempObject.getString("eve")));
        DailyNight.setText(""+convertToC(dailyTempObject.getString("night")));
        CurVisibility.setText(""+Integer.parseInt(current_data.getString("visibility"))/1000+"_KM");
    }

    private int fetchIconId(String w_icon) {

//        Toast.makeText(MainActivity.this, "Weather.Icon:"+w_icon, Toast.LENGTH_LONG).show();
        Context context = icon.getContext();
        int id = context.getResources().getIdentifier(w_icon , "drawable" , context.getOpPackageName());
//        Toast.makeText(MainActivity.this, "ID:"+id, Toast.LENGTH_LONG).show();
        return id;
    }

    private int convertTemp(String Skelvin ){
        float kelvin=0;
        MenuItem i=null;
//        i.getItemId();
//        Toast.makeText(this, "Type= "+Skelvin.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
        kelvin = Float.parseFloat(Skelvin);
        int f=0;
        f= (int) ((((kelvin-273.15)*9)/5)+32);
        return f;
    }
    private int convertToC(String Skelvin){
        float kelvin=0;
        kelvin = Float.parseFloat(Skelvin);
        int f=0;
        f = (int) (kelvin-273.15);
        return f;
    }
    private String convertTime(long dt , int i){
        LocalDateTime ldt = LocalDateTime.ofEpochSecond(dt + timezone_offset(), 0, ZoneOffset.UTC);
        String formattedTimeString="";
        DateTimeFormatter dtf;
        switch (i){
            case 1: dtf = DateTimeFormatter.ofPattern("EEE MMM dd h:mm a, yyyy", Locale.getDefault());
                    formattedTimeString = ldt.format(dtf);
                    break;
            case 2:
            case 3:
                dtf = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault());
                formattedTimeString = ldt.format(dtf);
                    break;
            default:
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
        }
        return formattedTimeString;
    }

    private long timezone_offset() {
        int l = 0;
        try {
            l = Integer.parseInt(t_response.getString("timezone_offset"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return l;
    }
}