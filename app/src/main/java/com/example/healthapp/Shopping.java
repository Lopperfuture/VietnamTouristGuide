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

public class Shopping extends AppCompatActivity {
    private RecyclerView shopping;
    private shoppingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_shopping);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //To Get City name for refference
        Bundle extras = getIntent().getExtras();
        //Accessing the values form intent using key
        String cityName=extras.getString("cityName");

        shopping = findViewById(R.id.btnShopping);
        shopping.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //Firebase Recyclerview to assign the data path and for accessing elements
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("shopping").child(cityName), Model.class)
                        .build();

        adapter = new shoppingAdapter(options);
        shopping.setAdapter(adapter);



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

class shoppingAdapter extends FirebaseRecyclerAdapter<Model, shoppingAdapter.shoppingViewholder> {

    public shoppingAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull shoppingViewholder holder, int position, @NonNull Model model) {


        //For Loading image into imageview using url
        Picasso.get().load(model.getImage()).into(holder.shoppingImg);

        //for loading restaurant name into recycler view
        holder.shoppingTitle.setText(model.getName());

        //for loading restaurant discription into recyclerview
        holder.shoppingData.setText(model.getDescription());


    }


    @NonNull
    @Override
    public shoppingViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //the data ojects are inflated into the xml file single_data_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_details, parent, false);
        return new shoppingViewholder(view);
    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    class shoppingViewholder extends RecyclerView.ViewHolder {


        private ImageView shoppingImg;
        private TextView shoppingTitle;
        private TextView shoppingData;

        public shoppingViewholder(@NonNull View itemView) {
            super(itemView);

            //asssiginig the address of the materials to show the data in appropriate location
            shoppingImg =  itemView.findViewById(R.id.ImgView);
            shoppingTitle =  itemView.findViewById(R.id.titleTxtView);
            shoppingData =  itemView.findViewById(R.id.dataTxtView);
        }
    }

}
