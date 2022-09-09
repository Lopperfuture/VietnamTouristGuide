package com.example.healthapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
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

public class Hotel extends AppCompatActivity {
    private RecyclerView hotel;
    private hotelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_hotel);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //To Get City name for refference
        Bundle extras = getIntent().getExtras();
        String cityName = extras.getString("cityName");


        hotel = findViewById(R.id.hotel);
        hotel.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("hotel").child(cityName), Model.class)
                        .build();

        adapter = new hotelAdapter(options);
        hotel.setAdapter(adapter);
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


    class hotelAdapter extends FirebaseRecyclerAdapter<Model, hotelAdapter.hotelViewholder> {

        public hotelAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {

            super(options);

        }

        @Override
        protected void onBindViewHolder(@NonNull hotelViewholder holder, int position, @NonNull Model model) {


            //For Loading image into imageview using url
            Picasso.get().load(model.getImage()).into(holder.hotelImg);

            //for loading hotel name into recycler view
            holder.hotelTitle.setText(model.getName());

            //for loading hotel discription into recyclerview
            holder.hotelData.setText(model.getDescription());


        }

        @NonNull
        @Override
        public hotelViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_details, parent, false);
            return new hotelViewholder(view);
        }

        class hotelViewholder extends RecyclerView.ViewHolder {


            private ImageView hotelImg;
            private TextView hotelTitle;
            private TextView hotelData;


            public hotelViewholder(@NonNull View itemView) {
                super(itemView);

                hotelImg = (ImageView) itemView.findViewById(R.id.ImgView);
                hotelTitle = (TextView) itemView.findViewById(R.id.titleTxtView);
                hotelData = (TextView) itemView.findViewById(R.id.dataTxtView);




            }
        }
    }





