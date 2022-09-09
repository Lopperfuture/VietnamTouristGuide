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

public class FamousPlaces extends AppCompatActivity {
    private RecyclerView famousPlaces;
    private PlacesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_famousplaces);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //To Get City name
        Bundle extras = getIntent().getExtras();
        String cityName = extras.getString("cityName");


        famousPlaces = findViewById(R.id.famousPlaces);
        famousPlaces.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("places").child(cityName), Model.class)
                        .build();

        adapter = new PlacesAdapter(options);
        famousPlaces.setAdapter(adapter);
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
        adapter.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        adapter.stopListening();
        super.onStop();

    }
    }

class PlacesAdapter extends FirebaseRecyclerAdapter<Model, PlacesAdapter.PlacesViewholder> {

    public PlacesAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull PlacesViewholder holder, int position, @NonNull Model model) {


        //Loading image into imageview using url
        Picasso.get().load(model.getImage()).into(holder.famousPlacesImg);

        //loading restaurant name into recycler view
        holder.famousPlacesTitle.setText(model.getName());

        //loading restaurant description into recyclerview
        holder.famousPlacesData.setText(model.getDescription());


    }



    @NonNull
    @Override
    public PlacesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //the data objects are inflated into the xml file
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_details, parent, false);
        return new PlacesViewholder(view);
    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    class PlacesViewholder extends RecyclerView.ViewHolder {


       private ImageView famousPlacesImg;
        private TextView famousPlacesTitle;
       private TextView famousPlacesData;

        public PlacesViewholder(@NonNull View itemView) {
            super(itemView);

            //assigning the address of the materials to show the data in appropriate location
            famousPlacesImg =  itemView.findViewById(R.id.ImgView);
            famousPlacesTitle =  itemView.findViewById(R.id.titleTxtView);
            famousPlacesData =  itemView.findViewById(R.id.dataTxtView);
        }
    }

}


