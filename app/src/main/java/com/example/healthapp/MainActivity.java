package com.example.healthapp;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private Button buttonTourist, buttonRefer, buttonTips, buttonExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonTourist = findViewById(R.id.buttonTourist);
        buttonRefer = findViewById(R.id.buttonRefer);
        buttonTips = findViewById(R.id.buttonTips);
        buttonExit = findViewById(R.id.buttonExit);


        buttonTourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });

        buttonRefer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://theculturetrip.com/asia/vietnam/articles/the-15-best-destinations-in-vietnam/"));
                startActivity(browserIntent);
            }
        });

        buttonTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(MainActivity.this, Tips.class);
                startActivity(i);
            }
        });

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });

    }
}