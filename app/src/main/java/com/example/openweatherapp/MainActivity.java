package com.example.openweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Changing the action bar colour
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opt_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.opt_temp_format){
            Toast.makeText(this, "Temp_selected", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId() == R.id.opt_calendar){
//            Toast.makeText(this, "Cal_selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,Activity2.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.opt_location){
            Toast.makeText(this, "Locaiton_selected", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "No such selection", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}