package com.example.healthapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView searchList;
    private EditText editSearchBox;
    cityAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_search);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        searchList = findViewById(R.id.searchList);
        searchList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        editSearchBox = findViewById(R.id.editSearchbox);


        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("cityNames"), Model.class)
                        .build();

        adapter = new cityAdapter(options);
        searchList.setAdapter(adapter);




        editSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String search = editSearchBox.getText().toString();
                FirebaseRecyclerOptions<Model> options =
                        new FirebaseRecyclerOptions.Builder<Model>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("cityNames").orderByChild("cityName").startAt(search).endAt(search + "\uf8ff"), Model.class)
                                .build();

                adapter = new cityAdapter(options);
                adapter.startListening();
                searchList.setAdapter(adapter);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String search = editSearchBox.getText().toString();
                FirebaseRecyclerOptions<Model> options =
                        new FirebaseRecyclerOptions.Builder<Model>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("cityNames").orderByChild("cityName").startAt(search).endAt(search + "\uf8ff"), Model.class)
                                .build();

                adapter = new cityAdapter(options);
                adapter.startListening();
                searchList.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String charSequence = editable.toString();
                FirebaseRecyclerOptions<Model> options =
                        new FirebaseRecyclerOptions.Builder<Model>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("cityNames").orderByChild("cityName").startAt(charSequence).endAt(charSequence + "\uf8ff"), Model.class)
                                .build();

                adapter = new cityAdapter(options);
                adapter.startListening();
                searchList.setAdapter(adapter);

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

class cityAdapter extends FirebaseRecyclerAdapter<Model, cityAdapter.cityViewholder> {

    public cityAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull cityViewholder holder, int position, @NonNull Model model) {

        String cityName=model.getCityName().toString();

        holder.mTextview.setText(cityName);

        holder.mTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context=view.getContext();
                Intent intent=new Intent(context,CityDetails.class);
                intent.putExtra("cityName",cityName);
                context.startActivity(intent);

            }
        });

    }


    @NonNull
    @Override
    public cityViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_name, parent, false);
        return new cityViewholder(view);
    }

    class cityViewholder extends RecyclerView.ViewHolder {

        TextView mTextview;

        public cityViewholder(@NonNull View itemView) {
            super(itemView);

            mTextview =  itemView.findViewById(R.id.txtCityName);
        }
    }

}
