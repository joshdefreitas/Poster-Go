package com.example.postergo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Recommendations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);

    }
    public void returnMain(View view){
        Intent r = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(r);
    }
}
