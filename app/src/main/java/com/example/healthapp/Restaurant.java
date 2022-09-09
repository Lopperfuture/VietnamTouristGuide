package com.example.healthapp;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Restaurant extends AppCompatActivity {
    private RecyclerView restaurant;
    restaurantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_restaurant);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //To Get City name for refference
        Bundle extras = getIntent().getExtras();
        //Accessing the values form intent using key
        String cityName=extras.getString("cityName");

        restaurant = findViewById(R.id.restaurant);
        restaurant.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //Firebase Recyclerview to assign the data path and for accessing elements
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("restaurants").child(cityName), Model.class)
                        .build();

        adapter = new restaurantAdapter(options);
        restaurant.setAdapter(adapter);



    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void onStart() {
        //starts the listening for values when activity starts
        adapter.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        //stops looking for value when activity stops
        adapter.stopListening();
        super.onStop();

    }
}

class restaurantAdapter extends FirebaseRecyclerAdapter<Model, restaurantAdapter.restaurantViewholder> {

    public restaurantAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull restaurantViewholder holder, int position, @NonNull Model model) {


        //For Loading image into imageview using url
        Picasso.get().load(model.getImage()).into(holder.restaurantImg);

        //for loading restaurant name into recycler view
        holder.restaurantTitle.setText(model.getName());

        //for loading restaurant discription into recyclerview
        holder.restaurantData.setText(model.getDescription());


    }


    @NonNull
    @Override
    public restaurantViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //the data ojects are inflated into the xml file single_data_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_details, parent, false);
        return new restaurantViewholder(view);
    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    class restaurantViewholder extends RecyclerView.ViewHolder {


        private ImageView restaurantImg;
        private TextView restaurantTitle;
        private TextView restaurantData;

        public restaurantViewholder(@NonNull View itemView) {
            super(itemView);

            //asssiginig the address of the materials to show the data in appropriate location
            restaurantImg =  itemView.findViewById(R.id.ImgView);
            restaurantTitle =  itemView.findViewById(R.id.titleTxtView);
            restaurantData =  itemView.findViewById(R.id.dataTxtView);
        }
    }

}
