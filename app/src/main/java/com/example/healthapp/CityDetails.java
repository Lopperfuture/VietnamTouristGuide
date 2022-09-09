package com.example.healthapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CityDetails extends AppCompatActivity {
   private Button btnHotel,btnRestaurant,btnFamousPlaces, btnShopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_city);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent=getIntent();
        String cityName=intent.getStringExtra("cityName");
        btnHotel = findViewById(R.id.btnHotel);
        btnRestaurant = findViewById(R.id.btnRestaurant);
        btnFamousPlaces = findViewById(R.id.btnFamousPlaces);
        btnShopping = findViewById(R.id.btnShopping);



        btnHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Hotel.class);
                intent.putExtra("cityName",cityName);
                startActivity(intent);

            }
        });

        btnRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Restaurant.class);
                intent.putExtra("cityName",cityName);
                startActivity(intent);
            }
        });


        btnFamousPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),FamousPlaces.class);
                intent.putExtra("cityName",cityName);
                startActivity(intent);
            }
        });

        btnShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Shopping.class);
                intent.putExtra("cityName",cityName);
                startActivity(intent);
            }
        });


    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }
}
